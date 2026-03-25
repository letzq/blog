package com.xy.blog.system.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xy.blog.framework.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 博客用户实体。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_user")
@Schema(name = "BlogUser", description = "博客用户表")
public class BlogUser extends BaseEntity {

    @Schema(description = "用户ID")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    @Schema(description = "登录账号")
    @TableField("user_name")
    private String userName;

    @Schema(description = "用户昵称")
    @TableField("nick_name")
    private String nickName;

    @Schema(description = "用户邮箱")
    @TableField("email")
    private String email;

    @Schema(description = "手机号")
    @TableField("phonenumber")
    private String phonenumber;

    @Schema(description = "登录密码")
    @TableField("password")
    private String password;

    @Schema(description = "头像地址")
    @TableField("avatar")
    private String avatar;

    @Schema(description = "性别，0未知 1男 2女")
    @TableField("sex")
    private String sex;

    @Schema(description = "账号状态，0停用 1正常")
    @TableField("status")
    private String status;

    @Schema(description = "删除标志，0存在 1删除")
    @TableField("del_flag")
    private String delFlag;

    @Schema(description = "最后登录IP")
    @TableField("login_ip")
    private String loginIp;

    @Schema(description = "最后登录时间")
    @TableField("login_date")
    private LocalDateTime loginDate;

    @Schema(description = "微信OpenID")
    @TableField("wx_openid")
    private String wxOpenid;

    @Schema(description = "微信UnionID")
    @TableField("wx_unionid")
    private String wxUnionid;

    @Schema(description = "微信昵称")
    @TableField("wx_nickname")
    private String wxNickname;

    @Schema(description = "微信头像")
    @TableField("wx_avatar")
    private String wxAvatar;
}
