package com.xy.blog.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 邮箱验证码发送请求对象。
 */
@Data
@Schema(name = "EmailCodeSendDto", description = "邮箱验证码发送请求对象")
public class EmailCodeSendDto {

    @Schema(description = "接收验证码的邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "发送场景，LOGIN 登录，REGISTER 注册")
    @NotBlank(message = "发送场景不能为空")
    @Pattern(regexp = "LOGIN|REGISTER", message = "发送场景只支持 LOGIN 或 REGISTER")
    private String scene;
}