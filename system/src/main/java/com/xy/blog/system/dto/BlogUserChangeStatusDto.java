package com.xy.blog.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用户状态修改参数。
 */
@Data
@Schema(name = "BlogUserChangeStatusDto", description = "用户状态修改参数")
public class BlogUserChangeStatusDto {

    @Schema(description = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "状态")
    @NotBlank(message = "状态不能为空")
    private String status;
}
