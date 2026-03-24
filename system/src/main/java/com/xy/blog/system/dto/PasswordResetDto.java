package com.xy.blog.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 找回密码重置请求对象。
 */
@Data
@Schema(name = "PasswordResetDto", description = "找回密码重置请求对象")
public class PasswordResetDto {

    @Schema(description = "用户邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    @Schema(description = "邮箱验证码")
    @NotBlank(message = "邮箱验证码不能为空")
    private String code;

    @Schema(description = "新密码")
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 20, message = "新密码长度必须在6到20个字符之间")
    private String newPassword;

    @Schema(description = "确认新密码")
    @NotBlank(message = "确认密码不能为空")
    @Size(min = 6, max = 20, message = "确认密码长度必须在6到20个字符之间")
    private String confirmPassword;
}
