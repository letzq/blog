package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 后台文章分类详情返回对象。
 */
@Data
@Schema(name = "BlogArticleCategoryDetailVo", description = "后台文章分类详情返回对象")
public class BlogArticleCategoryDetailVo {

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "父分类ID")
    private Long parentId;

    @Schema(description = "祖级列表")
    private String ancestors;

    @Schema(description = "排序值")
    private Integer sortNum;

    @Schema(description = "分类状态，0停用1启用")
    private String status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "文章数量")
    private Long articleCount;
}