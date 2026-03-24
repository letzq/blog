package com.xy.blog.framework.security.service;

import com.xy.blog.common.constant.CacheConstants;
import com.xy.blog.framework.redis.RedisCache;
import com.xy.blog.framework.security.config.JwtProperties;
import com.xy.blog.framework.security.utils.JwtUtils;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Token 服务。
 */
@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtils jwtUtils;
    private final JwtProperties jwtProperties;
    private final RedisCache redisCache;

    /**
     * 为用户创建 token 并写入 Redis。
     */
    public String createToken(Long userId, String userName) {
        String token = jwtUtils.createToken(userId, userName);
        String cacheKey = CacheConstants.LOGIN_TOKEN_KEY_PREFIX + userId;
        redisCache.setCacheObject(cacheKey, token, jwtProperties.getExpireTime(), TimeUnit.MINUTES);
        return token;
    }

    /**
     * 构建请求头中的完整 Bearer token。
     */
    public String buildAuthorizationToken(String token) {
        return jwtProperties.getPrefix() + token;
    }

    /**
     * 从请求头值中提取纯 token。
     */
    public String resolveToken(String authorizationHeader) {
        if (!StringUtils.hasText(authorizationHeader)) {
            return null;
        }
        if (!authorizationHeader.startsWith(jwtProperties.getPrefix())) {
            return null;
        }
        String token = authorizationHeader.substring(jwtProperties.getPrefix().length()).trim();
        return StringUtils.hasText(token) ? token : null;
    }

    /**
     * 判断当前 token 是否与 Redis 中缓存的登录态一致。
     */
    public boolean isTokenActive(Long userId, String token) {
        if (userId == null || !StringUtils.hasText(token)) {
            return false;
        }
        String cacheToken = redisCache.getCacheObject(CacheConstants.LOGIN_TOKEN_KEY_PREFIX + userId);
        return token.equals(cacheToken);
    }

    /**
     * 获取 JWT 请求头名称。
     */
    public String getHeaderName() {
        return jwtProperties.getHeader();
    }

    /**
     * 根据用户 ID 删除登录 token。
     */
    public void removeTokenByUserId(Long userId) {
        if (userId == null) {
            return;
        }
        redisCache.deleteObject(CacheConstants.LOGIN_TOKEN_KEY_PREFIX + userId);
    }
}