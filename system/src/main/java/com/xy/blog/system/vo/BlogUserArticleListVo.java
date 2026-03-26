package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 当前登录用户文章列表返回对象。
 */
@Data
@Schema(name = "BlogUserArticleListVo", description = "当前登录用户文章列表返回对象")
public class BlogUserArticleListVo {

    @Schema(description = "文章ID")
    private Long articleId;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章摘要")
    private String summary;

    @Schema(description = "封面图片")
    private String coverImage;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "文章状态，0草稿1已发布2下线")
    private String status;

    @Schema(description = "是否置顶，0否1是")
    private String isTop;

    @Schema(description = "发布时间")
    private LocalDateTime publishTime;

    @Schema(description = "浏览量")
    private Long viewCount;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
