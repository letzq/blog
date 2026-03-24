package com.xy.blog.system.service.impl;

import com.xy.blog.common.constant.CacheConstants;
import com.xy.blog.framework.exception.BusinessException;
import com.xy.blog.framework.mail.service.MailService;
import com.xy.blog.framework.redis.RedisCache;
import com.xy.blog.framework.security.config.JwtProperties;
import com.xy.blog.framework.security.service.TokenService;
import com.xy.blog.system.dto.BlogLoginDto;
import com.xy.blog.system.dto.BlogUserCreateDto;
import com.xy.blog.system.dto.EmailCodeSendDto;
import com.xy.blog.system.dto.EmailLoginDto;
import com.xy.blog.system.dto.RegisterDto;
import com.xy.blog.system.entity.po.BlogUser;
import com.xy.blog.system.service.IBlogAuthService;
import com.xy.blog.system.service.IBlogUserService;
import com.xy.blog.system.vo.BlogLoginVo;
import com.xy.blog.system.vo.BlogUserVo;
import com.xy.blog.system.vo.CaptchaVo;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 博客认证服务实现。
 */
@Service
@RequiredArgsConstructor
public class BlogAuthServiceImpl implements IBlogAuthService {

    private static final long LOGIN_CAPTCHA_EXPIRE_MINUTES = 5L;
    private static final long EMAIL_CODE_EXPIRE_MINUTES = 5L;
    private static final long EMAIL_SEND_LIMIT_SECONDS = 60L;
    private static final int LOGIN_CAPTCHA_LENGTH = 4;
    private static final char[] CAPTCHA_CHARS = "23456789qwertyuiopasdfghjklzxcvbnmABCDEFGHJKLMNPQRSTUVWXYZ".toCharArray();

    private final IBlogUserService blogUserService;
    private final RedisCache redisCache;
    private final MailService mailService;
    private final TokenService tokenService;
    private final JwtProperties jwtProperties;
    private final PasswordEncoder passwordEncoder;

    /**
     * 生成登录验证码。
     */
    @Override
    public CaptchaVo generateLoginCaptcha() {
        String captchaCode = generateCaptchaCode(LOGIN_CAPTCHA_LENGTH);
        String captchaKey = UUID.randomUUID().toString().replace("-", "");
        String cacheKey = CacheConstants.LOGIN_CAPTCHA_CODE_KEY_PREFIX + captchaKey;
        redisCache.setCacheObject(cacheKey, captchaCode, LOGIN_CAPTCHA_EXPIRE_MINUTES, TimeUnit.MINUTES);
        return CaptchaVo.builder()
            .captchaKey(captchaKey)
            .captchaImage(buildCaptchaImage(captchaCode))
            .build();
    }

    @Override
    public BlogLoginVo loginByPassword(BlogLoginDto dto) {
        validateLoginCaptcha(dto.getCaptchaKey(), dto.getCaptchaCode());
        BlogUser user = blogUserService.getByUserName(dto.getUserName().trim());
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        validateUserAvailable(user);
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        return buildLoginVo(user);
    }

    @Override
    public void sendEmailCode(EmailCodeSendDto dto) {
        String email = dto.getEmail().trim();
        String scene = dto.getScene().trim().toUpperCase(Locale.ROOT);
        validateEmailScene(scene, email);

        String sendLimitKey = buildSendLimitKey(scene, email);
        if (redisCache.hasKey(sendLimitKey)) {
            throw new BusinessException("验证码发送过于频繁，请60秒后再试");
        }

        String code = generateNumericCode();
        redisCache.setCacheObject(buildCodeKey(scene, email), code, EMAIL_CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);
        redisCache.setCacheObject(sendLimitKey, "1", EMAIL_SEND_LIMIT_SECONDS, TimeUnit.SECONDS);
        mailService.sendTextMail(email, resolveSubject(scene), buildContent(scene, code));
    }

    @Override
    public BlogLoginVo loginByEmail(EmailLoginDto dto) {
        String email = dto.getEmail().trim();
        BlogUser user = blogUserService.getByEmail(email);
        if (user == null) {
            throw new BusinessException("该邮箱未注册");
        }
        validateUserAvailable(user);
        validateEmailCode("LOGIN", email, dto.getCode(), true);
        return buildLoginVo(user);
    }
    /**
     * 注册前先校验一次邮箱验证码，校验成功后立即删除缓存避免重复使用。
     */

    @Override
    public BlogUserVo register(RegisterDto dto) {
        String email = dto.getEmail().trim();
        validateEmailCode("REGISTER", email, dto.getCode(), true);

        BlogUserCreateDto createDto = new BlogUserCreateDto();
        createDto.setUserName(dto.getUserName());
        createDto.setNickName(dto.getNickName());
        createDto.setEmail(email);
        createDto.setPhonenumber(dto.getPhonenumber());
        createDto.setPassword(dto.getPassword());
        return blogUserService.createUser(createDto);
    }

    /**
     * 登录前先校验一次图形验证码，校验成功后立即删除缓存避免重复使用。
     */
    private void validateLoginCaptcha(String captchaKey, String captchaCode) {
        String cacheKey = CacheConstants.LOGIN_CAPTCHA_CODE_KEY_PREFIX + captchaKey.trim();
        String cacheCode = redisCache.getCacheObject(cacheKey);
        redisCache.deleteObject(cacheKey);
        if (cacheCode == null) {
            throw new BusinessException("验证码不存在或已过期");
        }
        if (!cacheCode.equalsIgnoreCase(captchaCode.trim())) {
            throw new BusinessException("验证码错误");
        }
    }

    /**
     * 根据验证码使用场景校验邮箱是否允许发送验证码。
     */
    private void validateEmailScene(String scene, String email) {
        BlogUser user = blogUserService.getByEmail(email);
        switch (scene) {
            case "LOGIN" -> {
                if (user == null) {
                    throw new BusinessException("该邮箱未注册");
                }
                validateUserAvailable(user);
            }
            case "REGISTER" -> {
                if (user != null) {
                    throw new BusinessException("该邮箱已被注册");
                }
            }
            default -> throw new BusinessException("不支持的验证码场景");
        }
    }

    /**
     * 校验邮箱验证码，并按需要在校验通过后删除缓存。
     */
    private void validateEmailCode(String scene, String email, String code, boolean removeAfterValidate) {
        String cacheKey = buildCodeKey(scene, email);
        String cacheCode = redisCache.getCacheObject(cacheKey);
        if (cacheCode == null) {
            throw new BusinessException("邮箱验证码不存在或已过期");
        }
        if (!cacheCode.equalsIgnoreCase(code.trim())) {
            throw new BusinessException("邮箱验证码错误");
        }
        if (removeAfterValidate) {
            redisCache.deleteObject(cacheKey);
        }
    }

    /**
     * 校验用户当前是否可登录。
     */
    private void validateUserAvailable(BlogUser user) {
        if (!"1".equals(user.getStatus()) || "1".equals(user.getDelFlag())) {
            throw new BusinessException("当前用户已被禁用");
        }
    }

    private String buildCodeKey(String scene, String email) {
        return switch (scene) {
            case "LOGIN" -> CacheConstants.EMAIL_LOGIN_CODE_KEY_PREFIX + email;
            case "REGISTER" -> CacheConstants.EMAIL_REGISTER_CODE_KEY_PREFIX + email;
            default -> throw new BusinessException("不支持的验证码场景");
        };
    }

    private String buildSendLimitKey(String scene, String email) {
        return switch (scene) {
            case "LOGIN" -> CacheConstants.EMAIL_LOGIN_SEND_LIMIT_KEY_PREFIX + email;
            case "REGISTER" -> CacheConstants.EMAIL_REGISTER_SEND_LIMIT_KEY_PREFIX + email;
            default -> throw new BusinessException("不支持的验证码场景");
        };
    }

    private String generateNumericCode() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
    }

    private String generateCaptchaCode(int length) {
        StringBuilder builder = new StringBuilder(length);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int index = 0; index < length; index++) {
            builder.append(CAPTCHA_CHARS[random.nextInt(CAPTCHA_CHARS.length)]);
        }
        return builder.toString();
    }

    private String resolveSubject(String scene) {
        return switch (scene) {
            case "LOGIN" -> "博客系统登录验证码";
            case "REGISTER" -> "博客系统注册验证码";
            default -> "博客系统验证码";
        };
    }

    private String buildContent(String scene, String code) {
        String action = "LOGIN".equals(scene) ? "登录" : "注册";
        return "您本次" + action + "的验证码为：" + code + "，5分钟内有效，请勿泄露给他人。";
    }

    private BlogLoginVo buildLoginVo(BlogUser user) {
        String token = tokenService.createToken(user.getUserId(), user.getUserName());
        return BlogLoginVo.builder()
            .token(token)
            .header(jwtProperties.getHeader())
            .prefix(jwtProperties.getPrefix())
            .expireTime(jwtProperties.getExpireTime())
            .userName(user.getUserName())
            .build();
    }

    /**
     * 生成基础验证码图片，当前实现够开发联调用，后面可再增强噪点和字体随机化。
     */
    private String buildCaptchaImage(String captchaCode) {
        BufferedImage image = new BufferedImage(120, 40, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        try {
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, 120, 40);
            graphics.setFont(new Font("Arial", Font.BOLD, 24));
            graphics.setColor(new Color(52, 73, 94));
            graphics.drawString(captchaCode, 18, 28);
            graphics.setColor(new Color(189, 195, 199));
            for (int index = 0; index < 6; index++) {
                int x1 = ThreadLocalRandom.current().nextInt(120);
                int y1 = ThreadLocalRandom.current().nextInt(40);
                int x2 = ThreadLocalRandom.current().nextInt(120);
                int y2 = ThreadLocalRandom.current().nextInt(40);
                graphics.drawLine(x1, y1, x2, y2);
            }
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ImageIO.write(image, "png", outputStream);
                return "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
            }
        } catch (IOException exception) {
            throw new BusinessException("生成验证码图片失败");
        } finally {
            graphics.dispose();
        }
    }
}