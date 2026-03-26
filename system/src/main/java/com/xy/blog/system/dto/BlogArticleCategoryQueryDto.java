package com.xy.blog.system.dto;

import com.xy.blog.framework.web.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 后台文章分类分页查询参数。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "BlogArticleCategoryQueryDto", description = "后台文章分类分页查询参数")
public class BlogArticleCategoryQueryDto extends PageQuery {

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "分类状态，0停用1启用")
    private String status;
}