package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * 用户详情返回对象。
 */
@Data
@Builder
@Schema(name = "BlogUserDetailVo", description = "用户详情返回对象")
public class BlogUserDetailVo {

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

    @Schema(description = "性别")
    private String sex;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "角色ID列表")
    private List<Long> roleIds;

    @Schema(description = "角色标识列表")
    private List<String> roles;
}
