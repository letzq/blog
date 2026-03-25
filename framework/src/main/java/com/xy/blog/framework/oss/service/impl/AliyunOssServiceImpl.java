package com.xy.blog.framework.oss.service.impl;

import com.aliyun.oss.OSS;
import com.xy.blog.framework.exception.BusinessException;
import com.xy.blog.framework.oss.model.OssUploadResult;
import com.xy.blog.framework.oss.properties.AliyunOssProperties;
import com.xy.blog.framework.oss.service.OssService;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 阿里云 OSS 服务实现。
 */
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "blog.oss", name = "enabled", havingValue = "true")
public class AliyunOssServiceImpl implements OssService {

    private final OSS ossClient;
    private final AliyunOssProperties aliyunOssProperties;

    @Override
    public OssUploadResult upload(MultipartFile file, String dir) {
        validateFile(file);
        String objectName = buildObjectName(dir, file.getOriginalFilename());
        try (InputStream inputStream = file.getInputStream()) {
            ossClient.putObject(aliyunOssProperties.getBucketName(), objectName, inputStream);
        } catch (IOException exception) {
            throw new BusinessException("文件上传到 OSS 失败");
        }
        return OssUploadResult.builder()
            .url(buildFileUrl(objectName))
            .fileName(objectName)
            .originalName(file.getOriginalFilename())
            .contentType(file.getContentType())
            .size(file.getSize())
            .build();
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }
        if (file.getOriginalFilename() == null) {
            throw new BusinessException("文件名不能为空");
        }
    }

    private String buildObjectName(String dir, String originalFilename) {
        LocalDate today = LocalDate.now();
        String extension = getExtension(originalFilename);
        return String.format(
            Locale.ROOT,
            "%s/%04d/%02d/%02d/%s%s",
            normalizeDir(dir),
            today.getYear(),
            today.getMonthValue(),
            today.getDayOfMonth(),
            UUID.randomUUID().toString().replace("-", ""),
            extension
        );
    }

    private String normalizeDir(String dir) {
        String normalized = StringUtils.hasText(dir) ? dir.trim() : "common";
        normalized = normalized.replace("\\", "/");
        while (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
        }
        while (normalized.endsWith("/")) {
            normalized = normalized.substring(0, normalized.length() - 1);
        }
        return StringUtils.hasText(normalized) ? normalized : "common";
    }

    private String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex >= 0 ? fileName.substring(dotIndex) : "";
    }

    private String buildFileUrl(String objectName) {
        String domain = aliyunOssProperties.getDomain();
        if (StringUtils.hasText(domain)) {
            return domain.endsWith("/") ? domain + objectName : domain + "/" + objectName;
        }
        return "https://" + aliyunOssProperties.getBucketName() + "." + aliyunOssProperties.getEndpoint() + "/" + objectName;
    }
}
