package com.xy.blog.system.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 操作日志实体。
 */
@Data
@TableName("blog_oper_log")
@Schema(name = "BlogOperLog", description = "操作日志")
public class BlogOperLog {

    @Schema(description = "操作日志ID")
    @TableId(value = "oper_log_id", type = IdType.AUTO)
    private Long operLogId;

    @Schema(description = "模块标题")
    @TableField("title")
    private String title;

    @Schema(description = "业务类型")
    @TableField("business_type")
    private String businessType;

    @Schema(description = "方法名称")
    @TableField("method")
    private String method;

    @Schema(description = "请求方式")
    @TableField("request_method")
    private String requestMethod;

    @Schema(description = "操作人")
    @TableField("oper_name")
    private String operName;

    @Schema(description = "请求URL")
    @TableField("oper_url")
    private String operUrl;

    @Schema(description = "操作IP")
    @TableField("oper_ip")
    private String operIp;

    @Schema(description = "操作地点")
    @TableField("oper_location")
    private String operLocation;

    @Schema(description = "请求参数")
    @TableField("oper_param")
    private String operParam;

    @Schema(description = "返回结果")
    @TableField("json_result")
    private String jsonResult;

    @Schema(description = "操作状态")
    @TableField("status")
    private String status;

    @Schema(description = "错误消息")
    @TableField("error_msg")
    private String errorMsg;

    @Schema(description = "耗时毫秒")
    @TableField("cost_time")
    private Long costTime;

    @Schema(description = "操作时间")
    @TableField("oper_time")
    private LocalDateTime operTime;
}
