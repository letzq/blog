package com.xy.blog.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 后台文章置顶状态修改参数。
 */
@Data
@Schema(name = "BlogArticleChangeTopDto", description = "后台文章置顶状态修改参数")
public class BlogArticleChangeTopDto {

    @NotNull(message = "文章ID不能为空")
    @Schema(description = "文章ID")
    private Long articleId;

    @NotBlank(message = "置顶状态不能为空")
    @Schema(description = "是否置顶，0否1是")
    private String isTop;
}