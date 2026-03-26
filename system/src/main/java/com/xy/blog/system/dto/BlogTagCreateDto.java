package com.xy.blog.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 后台新增标签参数。
 */
@Data
@Schema(name = "BlogTagCreateDto", description = "后台新增标签参数")
public class BlogTagCreateDto {

    @NotBlank(message = "标签名称不能为空")
    @Size(max = 50, message = "标签名称长度不能超过50个字符")
    @Schema(description = "标签名称")
    private String tagName;

    @Schema(description = "排序值")
    private Integer sortNum;

    @Schema(description = "标签状态，0停用1启用")
    private String status;

    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Schema(description = "备注")
    private String remark;
}