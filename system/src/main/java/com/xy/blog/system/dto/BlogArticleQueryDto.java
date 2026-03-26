package com.xy.blog.system.dto;

import com.xy.blog.framework.web.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 后台文章分页查询参数。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "BlogArticleQueryDto", description = "后台文章分页查询参数")
public class BlogArticleQueryDto extends PageQuery {

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "作者账号")
    private String userName;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "文章状态，0草稿1已发布2下线")
    private String status;

    @Schema(description = "是否置顶，0否1是")
    private String isTop;
}