package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 后台文章分类列表返回对象。
 */
@Data
@Schema(name = "BlogArticleCategoryListVo", description = "后台文章分类列表返回对象")
public class BlogArticleCategoryListVo {

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "父分类ID")
    private Long parentId;

    @Schema(description = "排序值")
    private Integer sortNum;

    @Schema(description = "分类状态，0停用1启用")
    private String status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "文章数量")
    private Long articleCount;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}