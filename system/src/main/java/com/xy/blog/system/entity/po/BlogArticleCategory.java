package com.xy.blog.system.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xy.blog.framework.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 博客文章分类实体。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_article_category")
@Schema(name = "BlogArticleCategory", description = "博客文章分类实体")
public class BlogArticleCategory extends BaseEntity {

    @Schema(description = "分类ID")
    @TableId(value = "category_id", type = IdType.AUTO)
    private Long categoryId;

    @Schema(description = "分类名称")
    @TableField("category_name")
    private String categoryName;

    @Schema(description = "父分类ID")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "祖级列表")
    @TableField("ancestors")
    private String ancestors;

    @Schema(description = "排序值")
    @TableField("sort_num")
    private Integer sortNum;

    @Schema(description = "分类状态，0停用1启用")
    @TableField("status")
    private String status;
}
