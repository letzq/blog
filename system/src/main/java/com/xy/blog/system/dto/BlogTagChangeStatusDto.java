package com.xy.blog.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 后台标签状态修改参数。
 */
@Data
@Schema(name = "BlogTagChangeStatusDto", description = "后台标签状态修改参数")
public class BlogTagChangeStatusDto {

    @NotNull(message = "标签ID不能为空")
    @Schema(description = "标签ID")
    private Long tagId;

    @NotBlank(message = "标签状态不能为空")
    @Schema(description = "标签状态，0停用1启用")
    private String status;
}