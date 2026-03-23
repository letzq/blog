package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 登录验证码返回对象。
 */
@Data
@Builder
@Schema(name = "CaptchaVo", description = "登录验证码返回对象")
public class CaptchaVo {

    @Schema(description = "验证码 Key")
    private String captchaKey;

    @Schema(description = "Base64 图片数据，带 data:image/png;base64, 前缀")
    private String captchaImage;
}