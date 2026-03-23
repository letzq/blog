package com.xy.blog.framework.security.service;

import com.xy.blog.framework.redis.RedisCache;
import com.xy.blog.framework.security.config.JwtProperties;
import com.xy.blog.framework.security.utils.JwtUtils;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 令牌服务。
 */
@Service
@RequiredArgsConstructor
public class TokenService {

    private static final String TOKEN_CACHE_PREFIX = "blog:login:token:";

    private final JwtUtils jwtUtils;
    private final JwtProperties jwtProperties;
    private final RedisCache redisCache;

    /**
     * 为用户创建令牌并写入 Redis。
     */
    public String createToken(Long userId, String userName) {
        String token = jwtUtils.createToken(userId, userName);
        String cacheKey = TOKEN_CACHE_PREFIX + userId;
        redisCache.setCacheObject(cacheKey, token, jwtProperties.getExpireTime(), TimeUnit.MINUTES);
        return token;
    }

    /**
     * 获取令牌在请求头中的完整表现形式。
     */
    public String buildAuthorizationToken(String token) {
        return jwtProperties.getPrefix() + token;
    }
}
