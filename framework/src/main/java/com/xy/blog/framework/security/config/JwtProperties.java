package com.xy.blog.framework.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置属性。
 */
@Data
@Component
@ConfigurationProperties(prefix = "token")
public class JwtProperties {

    /**
     * JWT 密钥。
     */
    private String secret = "blog-default-jwt-secret-key-blog-default-jwt-secret-key";

    /**
     * 令牌有效期，单位分钟。
     */
    private long expireTime = 120L;

    /**
     * 令牌请求头名称。
     */
    private String header = "Authorization";

    /**
     * 令牌前缀。
     */
    private String prefix = "Bearer ";
}
