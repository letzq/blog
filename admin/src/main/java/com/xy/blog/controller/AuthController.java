package com.xy.blog.controller;

import com.xy.blog.framework.aspectj.annotation.Log;
import com.xy.blog.framework.aspectj.enums.BusinessType;
import com.xy.blog.framework.web.domain.Result;
import com.xy.blog.system.dto.BlogLoginDto;
import com.xy.blog.system.dto.EmailCodeSendDto;
import com.xy.blog.system.dto.EmailLoginDto;
import com.xy.blog.system.dto.PasswordCodeSendDto;
import com.xy.blog.system.dto.PasswordResetDto;
import com.xy.blog.system.dto.RegisterDto;
import com.xy.blog.system.service.IBlogAuthService;
import com.xy.blog.system.vo.BlogLoginVo;
import com.xy.blog.system.vo.BlogUserVo;
import com.xy.blog.system.vo.CaptchaVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器。
 */
@Tag(name = "认证管理")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IBlogAuthService blogAuthService;

    @Operation(summary = "获取登录验证码")
    @GetMapping("/captcha")
    public Result<CaptchaVo> captcha() {
        return Result.success(blogAuthService.generateLoginCaptcha());
    }

    @Log(title = "认证管理", businessType = BusinessType.LOGIN, saveRequestData = true, saveResponseData = true)
    @Operation(summary = "账号密码登录")
    @PostMapping("/login")
    public Result<BlogLoginVo> login(@RequestBody @Valid BlogLoginDto dto) {
        return Result.success(blogAuthService.loginByPassword(dto));
    }

    @Log(title = "认证管理", businessType = BusinessType.OTHER, saveRequestData = true, saveResponseData = true)
    @Operation(summary = "发送邮箱验证码")
    @PostMapping("/email/code")
    public Result<String> sendEmailCode(@RequestBody @Valid EmailCodeSendDto dto) {
        blogAuthService.sendEmailCode(dto);
        return Result.success("验证码发送成功");
    }

    @Log(title = "认证管理", businessType = BusinessType.LOGIN, saveRequestData = true, saveResponseData = true)
    @Operation(summary = "邮箱验证码登录")
    @PostMapping("/login/email")
    public Result<BlogLoginVo> loginByEmail(@RequestBody @Valid EmailLoginDto dto) {
        return Result.success(blogAuthService.loginByEmail(dto));
    }

    @Log(title = "认证管理", businessType = BusinessType.INSERT, saveRequestData = true, saveResponseData = true)
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<BlogUserVo> register(@RequestBody @Valid RegisterDto dto) {
        return Result.success(blogAuthService.register(dto));
    }

    @Log(title = "认证管理", businessType = BusinessType.OTHER, saveRequestData = true, saveResponseData = true)
    @Operation(summary = "发送找回密码验证码")
    @PostMapping("/password/code")
    public Result<String> sendResetPasswordCode(@RequestBody @Valid PasswordCodeSendDto dto) {
        blogAuthService.sendResetPasswordCode(dto);
        return Result.success("找回密码验证码发送成功");
    }

    @Log(title = "认证管理", businessType = BusinessType.UPDATE, saveRequestData = true, saveResponseData = true)
    @Operation(summary = "重置密码")
    @PostMapping("/password/reset")
    public Result<String> resetPassword(@RequestBody @Valid PasswordResetDto dto) {
        blogAuthService.resetPassword(dto);
        return Result.success("密码重置成功");
    }
}