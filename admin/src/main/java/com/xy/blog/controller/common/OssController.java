package com.xy.blog.controller.common;

import com.xy.blog.framework.aspectj.annotation.Log;
import com.xy.blog.framework.aspectj.enums.BusinessType;
import com.xy.blog.framework.oss.model.OssUploadResult;
import com.xy.blog.framework.oss.service.OssService;
import com.xy.blog.framework.web.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * OSS 文件上传控制器。
 */
@Tag(name = "OSS文件上传")
@RestController
@RequestMapping("/common/oss")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "blog.oss", name = "enabled", havingValue = "true")
public class OssController {

    private final OssService ossService;

    /**
     * 上传用户头像。
     */
    @Log(title = "文件上传", businessType = BusinessType.OTHER, saveRequestData = false, saveResponseData = true)
    @Operation(summary = "上传用户头像")
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/upload/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<OssUploadResult> uploadAvatar(@RequestPart("file") MultipartFile file) {
        return Result.success(ossService.upload(file, "avatar"));
    }
}
