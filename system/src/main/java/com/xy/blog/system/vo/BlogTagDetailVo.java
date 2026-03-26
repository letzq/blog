package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 后台标签详情返回对象。
 */
@Data
@Schema(name = "BlogTagDetailVo", description = "后台标签详情返回对象")
public class BlogTagDetailVo {

    @Schema(description = "标签ID")
    private Long tagId;

    @Schema(description = "标签名称")
    private String tagName;

    @Schema(description = "排序值")
    private Integer sortNum;

    @Schema(description = "标签状态，0停用1启用")
    private String status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "文章数量")
    private Long articleCount;
}