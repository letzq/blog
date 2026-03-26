package com.xy.blog.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 当前登录用户文章发布状态修改参数。
 */
@Data
@Schema(name = "BlogUserArticlePublishDto", description = "当前登录用户文章发布状态修改参数")
public class BlogUserArticlePublishDto {

    @NotNull(message = "文章ID不能为空")
    @Schema(description = "文章ID")
    private Long articleId;

    @NotBlank(message = "文章状态不能为空")
    @Schema(description = "文章状态，1已发布2下线")
    private String status;
}