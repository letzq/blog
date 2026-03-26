package com.xy.blog.system.dto;

import com.xy.blog.framework.web.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 当前登录用户文章分页查询参数。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "BlogUserArticleQueryDto", description = "当前登录用户文章分页查询参数")
public class BlogUserArticleQueryDto extends PageQuery {

    @Schema(description = "文章标题关键字")
    private String title;

    @Schema(description = "文章状态，0草稿1已发布2下线")
    private String status;

    @Schema(description = "分类ID")
    private Long categoryId;
}
