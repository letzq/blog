package com.xy.blog.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 后台文章分类状态修改参数。
 */
@Data
@Schema(name = "BlogArticleCategoryChangeStatusDto", description = "后台文章分类状态修改参数")
public class BlogArticleCategoryChangeStatusDto {

    @NotNull(message = "分类ID不能为空")
    @Schema(description = "分类ID")
    private Long categoryId;

    @NotBlank(message = "分类状态不能为空")
    @Schema(description = "分类状态，0停用1启用")
    private String status;
}