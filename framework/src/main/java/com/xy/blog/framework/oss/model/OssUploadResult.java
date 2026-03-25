package com.xy.blog.framework.oss.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * OSS 上传结果。
 */
@Data
@Builder
@Schema(name = "OssUploadResult", description = "OSS 上传结果")
public class OssUploadResult {

    @Schema(description = "文件访问地址")
    private String url;

    @Schema(description = "对象存储中的文件名")
    private String fileName;

    @Schema(description = "原始文件名")
    private String originalName;

    @Schema(description = "文件类型")
    private String contentType;

    @Schema(description = "文件大小，单位字节")
    private Long size;
}
