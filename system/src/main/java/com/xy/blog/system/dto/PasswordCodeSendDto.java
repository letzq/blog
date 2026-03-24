package com.xy.blog.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 发送找回密码验证码请求对象。
 */
@Data
@Schema(name = "PasswordCodeSendDto", description = "发送找回密码验证码请求对象")
public class PasswordCodeSendDto {

    @Schema(description = "用户邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;
}
