package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 角色选项返回对象。
 */
@Data
@Builder
@Schema(name = "BlogRoleOptionVo", description = "角色选项返回对象")
public class BlogRoleOptionVo {

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色权限字符")
    private String roleKey;
}
