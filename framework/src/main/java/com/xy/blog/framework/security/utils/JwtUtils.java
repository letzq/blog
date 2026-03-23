package com.xy.blog.framework.security.utils;

import com.xy.blog.framework.security.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * JWT 工具类。
 */
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;

    /**
     * 生成 JWT 令牌。
     */
    public String createToken(Long userId, String userName) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + jwtProperties.getExpireTime() * 60 * 1000);
        return Jwts.builder()
            .subject(userName)
            .claim("userId", userId)
            .issuedAt(now)
            .expiration(expireDate)
            .signWith(getSecretKey())
            .compact();
    }

    /**
     * 解析 JWT 载荷。
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
            .verifyWith(getSecretKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    /**
     * 获取用户名。
     */
    public String getUserName(String token) {
        return parseToken(token).getSubject();
    }

    /**
     * 获取用户ID。
     */
    public Long getUserId(String token) {
        Object value = parseToken(token).get("userId");
        if (value instanceof Integer integer) {
            return integer.longValue();
        }
        if (value instanceof Long longValue) {
            return longValue;
        }
        return value == null ? null : Long.parseLong(value.toString());
    }

    /**
     * 校验令牌是否有效。
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception exception) {
            return false;
        }
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }
}
