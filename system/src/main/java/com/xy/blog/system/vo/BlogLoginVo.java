package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 登录返回对象。
 */
@Data
@Builder
@Schema(name = "BlogLoginVo", description = "登录返回对象")
public class BlogLoginVo {

    @Schema(description = "JWT令牌")
    private String token;

    @Schema(description = "请求头名称")
    private String header;

    @Schema(description = "令牌前缀")
    private String prefix;

    @Schema(description = "过期时间，单位分钟")
    private long expireTime;

    @Schema(description = "登录账号")
    private String userName;
}