package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 前台标签列表返回对象。
 */
@Data
@Schema(name = "AppTagListVo", description = "前台标签列表返回对象")
public class AppTagListVo {

    @Schema(description = "标签ID")
    private Long tagId;

    @Schema(description = "标签名称")
    private String tagName;

    @Schema(description = "排序值")
    private Integer sortNum;

    @Schema(description = "已发布文章数量")
    private Long articleCount;
}