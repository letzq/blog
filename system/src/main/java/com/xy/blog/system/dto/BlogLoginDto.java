package com.xy.blog.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 账号密码登录请求对象。
 */
@Data
@Schema(name = "BlogLoginDto", description = "账号密码登录请求对象")
public class BlogLoginDto {

    @Schema(description = "登录账号")
    @NotBlank(message = "登录账号不能为空")
    private String userName;

    @Schema(description = "登录密码")
    @NotBlank(message = "登录密码不能为空")
    private String password;

    @Schema(description = "登录验证码 Key")
    @NotBlank(message = "登录验证码 Key 不能为空")
    private String captchaKey;

    @Schema(description = "登录验证码")
    @NotBlank(message = "登录验证码不能为空")
    private String captchaCode;
}