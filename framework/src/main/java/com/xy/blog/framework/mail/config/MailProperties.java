package com.xy.blog.framework.mail.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 邮件发送配置属性。
 */
@Data
@Component
@ConfigurationProperties(prefix = "blog.mail")
public class MailProperties {

    /**
     * SMTP 主机地址。
     */
    private String host;

    /**
     * SMTP 端口。
     */
    private Integer port = 25;

    /**
     * 发件人邮箱。
     */
    private String from;

    /**
     * 发件人账号。
     */
    private String user;

    /**
     * 发件人密码或授权码。
     */
    private String pass;
}