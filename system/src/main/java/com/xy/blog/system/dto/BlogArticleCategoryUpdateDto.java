package com.xy.blog.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 后台修改文章分类参数。
 */
@Data
@Schema(name = "BlogArticleCategoryUpdateDto", description = "后台修改文章分类参数")
public class BlogArticleCategoryUpdateDto {

    @NotNull(message = "分类ID不能为空")
    @Schema(description = "分类ID")
    private Long categoryId;

    @NotBlank(message = "分类名称不能为空")
    @Size(max = 50, message = "分类名称长度不能超过50个字符")
    @Schema(description = "分类名称")
    private String categoryName;

    @NotNull(message = "父分类不能为空")
    @Schema(description = "父分类ID，顶级分类传0")
    private Long parentId;

    @Schema(description = "排序值")
    private Integer sortNum;

    @Schema(description = "分类状态，0停用1启用")
    private String status;

    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Schema(description = "备注")
    private String remark;
}