package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * 用户角色分配信息返回对象。
 */
@Data
@Builder
@Schema(name = "BlogUserAuthRoleVo", description = "用户角色分配信息返回对象")
public class BlogUserAuthRoleVo {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "登录账号")
    private String userName;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "角色ID列表")
    private List<Long> roleIds;

    @Schema(description = "可分配角色选项")
    private List<BlogRoleOptionVo> roleOptions;
}
