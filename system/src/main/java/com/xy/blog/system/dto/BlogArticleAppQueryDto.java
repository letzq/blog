package com.xy.blog.system.dto;

import com.xy.blog.framework.web.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 前台文章分页查询参数。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "BlogArticleAppQueryDto", description = "前台文章分页查询参数")
public class BlogArticleAppQueryDto extends PageQuery {

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "文章标题关键字")
    private String keyword;
}
