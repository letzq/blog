package com.xy.blog.framework.oss.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云 OSS 配置属性。
 */
@Data
@Component
@ConfigurationProperties(prefix = "blog.oss")
public class AliyunOssProperties {

    /**
     * 是否启用 OSS。
     */
    private boolean enabled;

    /**
     * OSS 节点地址。
     */
    private String endpoint;

    /**
     * AccessKeyId。
     */
    private String accessKeyId;

    /**
     * AccessKeySecret。
     */
    private String accessKeySecret;

    /**
     * Bucket 名称。
     */
    private String bucketName;

    /**
     * 对外访问域名。
     */
    private String domain;
}
