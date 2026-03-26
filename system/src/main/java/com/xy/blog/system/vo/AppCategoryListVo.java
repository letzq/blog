package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 前台分类列表返回对象。
 */
@Data
@Schema(name = "AppCategoryListVo", description = "前台分类列表返回对象")
public class AppCategoryListVo {

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "排序值")
    private Integer sortNum;

    @Schema(description = "已发布文章数量")
    private Long articleCount;
}
