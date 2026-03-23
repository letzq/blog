package com.xy.blog.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 邮箱验证码登录请求对象。
 */
@Data
@Schema(name = "EmailLoginDto", description = "邮箱验证码登录请求对象")
public class EmailLoginDto {

    @Schema(description = "登录邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "邮箱验证码")
    @NotBlank(message = "邮箱验证码不能为空")
    private String code;
}