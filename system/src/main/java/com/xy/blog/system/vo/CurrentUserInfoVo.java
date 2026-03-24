package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * 当前登录用户信息返回对象。
 */
@Data
@Builder
@Schema(name = "CurrentUserInfoVo", description = "当前登录用户信息")
public class CurrentUserInfoVo {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "登录账号")
    private String userName;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phonenumber;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "角色标识列表")
    private List<String> roles;

    @Schema(description = "权限标识列表")
    private List<String> permissions;
}
