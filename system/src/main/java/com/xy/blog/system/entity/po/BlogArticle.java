package com.xy.blog.system.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xy.blog.framework.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 博客文章实体。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_article")
@Schema(name = "BlogArticle", description = "博客文章实体")
public class BlogArticle extends BaseEntity {

    @Schema(description = "文章ID")
    @TableId(value = "article_id", type = IdType.AUTO)
    private Long articleId;

    @Schema(description = "分类ID")
    @TableField("category_id")
    private Long categoryId;

    @Schema(description = "作者用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "文章标题")
    @TableField("title")
    private String title;

    @Schema(description = "文章摘要")
    @TableField("summary")
    private String summary;

    @Schema(description = "封面图片")
    @TableField("cover_image")
    private String coverImage;

    @Schema(description = "Markdown 正文内容")
    @TableField("content_md")
    private String contentMd;

    @Schema(description = "是否置顶，0否1是")
    @TableField("is_top")
    private String isTop;

    @Schema(description = "是否原创，0否1是")
    @TableField("is_original")
    private String isOriginal;

    @Schema(description = "原文地址")
    @TableField("original_url")
    private String originalUrl;

    @Schema(description = "是否允许评论，0否1是")
    @TableField("allow_comment")
    private String allowComment;

    @Schema(description = "状态，0草稿1已发布2下线")
    @TableField("status")
    private String status;

    @Schema(description = "浏览量")
    @TableField("view_count")
    private Long viewCount;

    @Schema(description = "点赞数")
    @TableField("like_count")
    private Long likeCount;

    @Schema(description = "发布时间")
    @TableField("publish_time")
    private LocalDateTime publishTime;
}
