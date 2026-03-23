package com.xy.blog.system.service;

import com.xy.blog.system.dto.BlogLoginDto;
import com.xy.blog.system.dto.EmailCodeSendDto;
import com.xy.blog.system.dto.EmailLoginDto;
import com.xy.blog.system.dto.RegisterDto;
import com.xy.blog.system.vo.BlogLoginVo;
import com.xy.blog.system.vo.BlogUserVo;
import com.xy.blog.system.vo.CaptchaVo;

/**
 * 博客认证服务接口。
 */
public interface IBlogAuthService {

    /**
     * 生成账号密码登录验证码。
     *
     * @return 验证码键值和图片内容
     */
    CaptchaVo generateLoginCaptcha();

    /**
     * 账号密码登录。
     *
     * @param dto 登录参数
     * @return 登录结果
     */
    BlogLoginVo loginByPassword(BlogLoginDto dto);

    /**
     * 发送邮箱验证码。
     *
     * @param dto 邮箱验证码发送参数
     */
    void sendEmailCode(EmailCodeSendDto dto);

    /**
     * 邮箱验证码登录。
     *
     * @param dto 邮箱登录参数
     * @return 登录结果
     */
    BlogLoginVo loginByEmail(EmailLoginDto dto);

    /**
     * 用户注册。
     *
     * @param dto 注册参数
     * @return 注册后的用户信息
     */
    BlogUserVo register(RegisterDto dto);
}
