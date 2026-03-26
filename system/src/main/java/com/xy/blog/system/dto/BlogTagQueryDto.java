package com.xy.blog.system.dto;

import com.xy.blog.framework.web.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 后台标签分页查询参数。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "BlogTagQueryDto", description = "后台标签分页查询参数")
public class BlogTagQueryDto extends PageQuery {

    @Schema(description = "标签名称")
    private String tagName;

    @Schema(description = "标签状态，0停用1启用")
    private String status;
}