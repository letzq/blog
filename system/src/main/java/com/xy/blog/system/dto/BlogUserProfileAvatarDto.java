package com.xy.blog.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 当前登录用户修改头像参数。
 */
@Data
@Schema(name = "BlogUserProfileAvatarDto", description = "当前登录用户修改头像参数")
public class BlogUserProfileAvatarDto {

    @Schema(description = "头像地址")
    @NotBlank(message = "头像地址不能为空")
    @Size(max = 255, message = "头像地址长度不能超过255个字符")
    private String avatar;
}
