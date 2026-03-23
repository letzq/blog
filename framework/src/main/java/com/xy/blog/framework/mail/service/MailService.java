package com.xy.blog.framework.mail.service;

import com.xy.blog.framework.exception.BusinessException;
import com.xy.blog.framework.mail.config.MailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 邮件发送服务。
 */
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;

    /**
     * 发送纯文本邮件。
     *
     * @param to 收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendTextMail(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailProperties.getFrom());
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            javaMailSender.send(message);
        } catch (Exception exception) {
            throw new BusinessException("邮件发送失败");
        }
    }
}