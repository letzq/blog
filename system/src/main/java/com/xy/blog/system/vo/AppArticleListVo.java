package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 前台文章列表返回对象。
 */
@Data
@Schema(name = "AppArticleListVo", description = "前台文章列表返回对象")
public class AppArticleListVo {

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

    @Schema(description = "作者名称")
    private String authorName;

    @Schema(description = "发布时间")
    private LocalDateTime publishTime;

    @Schema(description = "浏览量")
    private Long viewCount;

    @Schema(description = "是否置顶，0否1是")
    private String isTop;
}
