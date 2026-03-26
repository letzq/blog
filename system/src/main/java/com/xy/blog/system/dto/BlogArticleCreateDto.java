package com.xy.blog.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

/**
 * 后台新增文章参数。
 */
@Data
@Schema(name = "BlogArticleCreateDto", description = "后台新增文章参数")
public class BlogArticleCreateDto {

    @NotBlank(message = "文章标题不能为空")
    @Size(max = 200, message = "文章标题长度不能超过200个字符")
    @Schema(description = "文章标题")
    private String title;

    @Size(max = 500, message = "文章摘要长度不能超过500个字符")
    @Schema(description = "文章摘要")
    private String summary;

    @Schema(description = "封面图片地址")
    private String coverImage;

    @NotNull(message = "文章分类不能为空")
    @Schema(description = "分类ID")
    private Long categoryId;

    @NotBlank(message = "文章内容不能为空")
    @Schema(description = "Markdown 正文内容")
    private String contentMd;

    @Schema(description = "是否允许评论，0否1是")
    private String allowComment;

    @Schema(description = "是否原创，0否1是")
    private String isOriginal;

    @Schema(description = "原文地址")
    private String originalUrl;

    @Schema(description = "文章状态，0草稿1已发布2下线")
    private String status;

    @Schema(description = "是否置顶，0否1是")
    private String isTop;

    @Schema(description = "标签ID列表")
    private List<Long> tagIds;
}