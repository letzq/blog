package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 博客用户返回对象。
 */
@Data
@Schema(name = "BlogUserVo", description = "博客用户返回对象")
public class BlogUserVo {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "登录账号")
    private String userName;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "用户邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phonenumber;

    @Schema(description = "头像地址")
    private String avatar;

    @Schema(description = "账号状态，0停用 1正常")
    private String status;
}