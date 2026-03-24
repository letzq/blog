package com.xy.blog.system.service;

import com.xy.blog.system.dto.BlogLoginDto;
import com.xy.blog.system.dto.EmailCodeSendDto;
import com.xy.blog.system.dto.EmailLoginDto;
import com.xy.blog.system.dto.PasswordCodeSendDto;
import com.xy.blog.system.dto.PasswordResetDto;
import com.xy.blog.system.dto.RegisterDto;
import com.xy.blog.system.vo.BlogLoginVo;
import com.xy.blog.system.vo.BlogUserVo;
import com.xy.blog.system.vo.CaptchaVo;
import com.xy.blog.system.vo.CurrentUserInfoVo;

/**
 * 博客认证服务接口。
 */
public interface IBlogAuthService {

    /**
     * 生成账号密码登录验证码。
     */
    CaptchaVo generateLoginCaptcha();

    /**
     * 账号密码登录。
     */
    BlogLoginVo loginByPassword(BlogLoginDto dto);

    /**
     * 发送邮箱验证码。
     */
    void sendEmailCode(EmailCodeSendDto dto);

    /**
     * 邮箱验证码登录。
     */
    BlogLoginVo loginByEmail(EmailLoginDto dto);

    /**
     * 用户注册。
     */
    BlogUserVo register(RegisterDto dto);

    /**
     * 发送找回密码验证码。
     */
    void sendResetPasswordCode(PasswordCodeSendDto dto);

    /**
     * 重置密码。
     */
    void resetPassword(PasswordResetDto dto);

    /**
     * 获取当前登录用户信息。
     */
    CurrentUserInfoVo getCurrentUserInfo();

    /**
     * 退出登录。
     */
    void logout();
}