package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 前台文章标签返回对象。
 */
@Data
@Schema(name = "AppArticleTagVo", description = "前台文章标签返回对象")
public class AppArticleTagVo {

    @Schema(description = "标签ID")
    private Long tagId;

    @Schema(description = "标签名称")
    private String tagName;
}
