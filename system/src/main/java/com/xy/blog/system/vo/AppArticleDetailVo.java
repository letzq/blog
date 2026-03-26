package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * 前台文章详情返回对象。
 */
@Data
@Schema(name = "AppArticleDetailVo", description = "前台文章详情返回对象")
public class AppArticleDetailVo {

    @Schema(description = "文章ID")
    private Long articleId;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章摘要")
    private String summary;

    @Schema(description = "封面图片")
    private String coverImage;

    @Schema(description = "Markdown 正文内容")
    private String contentMd;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "作者ID")
    private Long authorId;

    @Schema(description = "作者名称")
    private String authorName;

    @Schema(description = "发布时间")
    private LocalDateTime publishTime;

    @Schema(description = "浏览量")
    private Long viewCount;

    @Schema(description = "点赞数")
    private Long likeCount;

    @Schema(description = "是否原创，0否1是")
    private String isOriginal;

    @Schema(description = "原文地址")
    private String originalUrl;

    @Schema(description = "是否允许评论，0否1是")
    private String allowComment;

    @Schema(description = "文章标签列表")
    private List<AppArticleTagVo> tags;
}
