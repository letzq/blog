package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * 后台文章详情返回对象。
 */
@Data
@Schema(name = "BlogArticleDetailVo", description = "后台文章详情返回对象")
public class BlogArticleDetailVo {

    @Schema(description = "文章ID")
    private Long articleId;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章摘要")
    private String summary;

    @Schema(description = "封面图片地址")
    private String coverImage;

    @Schema(description = "Markdown 正文内容")
    private String contentMd;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "作者ID")
    private Long userId;

    @Schema(description = "作者账号")
    private String userName;

    @Schema(description = "作者昵称")
    private String nickName;

    @Schema(description = "文章状态，0草稿1已发布2下线")
    private String status;

    @Schema(description = "是否置顶，0否1是")
    private String isTop;

    @Schema(description = "是否原创，0否1是")
    private String isOriginal;

    @Schema(description = "原文地址")
    private String originalUrl;

    @Schema(description = "是否允许评论，0否1是")
    private String allowComment;

    @Schema(description = "浏览量")
    private Long viewCount;

    @Schema(description = "点赞数")
    private Long likeCount;

    @Schema(description = "发布时间")
    private LocalDateTime publishTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "标签ID列表")
    private List<Long> tagIds;
}