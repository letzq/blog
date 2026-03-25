package com.xy.blog.framework.oss.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.xy.blog.framework.oss.properties.AliyunOssProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云 OSS 配置。
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "blog.oss", name = "enabled", havingValue = "true")
public class AliyunOssConfig {

    private final AliyunOssProperties aliyunOssProperties;

    /**
     * 注册 OSS 客户端。
     */
    @Bean(destroyMethod = "shutdown")
    public OSS ossClient() {
        return new OSSClientBuilder().build(
            aliyunOssProperties.getEndpoint(),
            aliyunOssProperties.getAccessKeyId(),
            aliyunOssProperties.getAccessKeySecret()
        );
    }
}
