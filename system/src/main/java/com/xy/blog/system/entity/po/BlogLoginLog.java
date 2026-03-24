package com.xy.blog.system.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 登录日志实体。
 */
@Data
@TableName("blog_login_log")
@Schema(name = "BlogLoginLog", description = "登录日志")
public class BlogLoginLog {

    @Schema(description = "登录日志ID")
    @TableId(value = "login_log_id", type = IdType.AUTO)
    private Long loginLogId;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "用户名")
    @TableField("user_name")
    private String userName;

    @Schema(description = "登录方式")
    @TableField("login_type")
    private String loginType;

    @Schema(description = "邮箱")
    @TableField("email")
    private String email;

    @Schema(description = "登录IP")
    @TableField("login_ip")
    private String loginIp;

    @Schema(description = "登录地点")
    @TableField("login_location")
    private String loginLocation;

    @Schema(description = "浏览器")
    @TableField("browser")
    private String browser;

    @Schema(description = "操作系统")
    @TableField("os")
    private String os;

    @Schema(description = "设备信息")
    @TableField("device_info")
    private String deviceInfo;

    @Schema(description = "登录状态")
    @TableField("status")
    private String status;

    @Schema(description = "结果消息")
    @TableField("message")
    private String message;

    @Schema(description = "登录时间")
    @TableField("login_time")
    private LocalDateTime loginTime;
}
