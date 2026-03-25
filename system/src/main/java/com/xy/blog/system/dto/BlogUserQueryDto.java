package com.xy.blog.system.dto;

import com.xy.blog.framework.web.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户分页查询参数。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "BlogUserQueryDto", description = "用户分页查询参数")
public class BlogUserQueryDto extends PageQuery {

    @Schema(description = "登录账号")
    private String userName;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phonenumber;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "创建开始时间，格式：yyyy-MM-dd HH:mm:ss")
    private String beginTime;

    @Schema(description = "创建结束时间，格式：yyyy-MM-dd HH:mm:ss")
    private String endTime;
}
