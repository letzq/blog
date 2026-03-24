package com.xy.blog.framework.mail.service;

import com.xy.blog.framework.mail.config.MailProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 邮件发送服务。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;

    /**
     * 异步发送纯文本邮件，避免阻塞当前接口线程。
     *
     * @param to 收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    @Async
    public void sendTextMail(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailProperties.getFrom());
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            javaMailSender.send(message);
        } catch (Exception exception) {
            log.error("Send mail failed, to={}", to, exception);
        }
    }
}