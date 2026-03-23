package com.xy.blog.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 博客用户新增请求对象。
 */
@Data
@Schema(name = "BlogUserCreateDto", description = "博客用户新增请求对象")
public class BlogUserCreateDto {

    @Schema(description = "登录账号")
    @NotBlank(message = "登录账号不能为空")
    @Size(max = 30, message = "登录账号长度不能超过30个字符")
    private String userName;

    @Schema(description = "用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickName;

    @Schema(description = "用户邮箱")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    @Schema(description = "手机号")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phonenumber;

    @Schema(description = "登录密码")
    @NotBlank(message = "登录密码不能为空")
    @Size(min = 6, max = 20, message = "登录密码长度必须在6到20个字符之间")
    private String password;
}