package com.xy.blog.framework.oss.service;

import com.xy.blog.framework.oss.model.OssUploadResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 对象存储服务接口。
 */
public interface OssService {

    /**
     * 上传文件到指定业务目录。
     *
     * @param file 上传文件
     * @param dir 业务目录
     * @return 上传结果
     */
    OssUploadResult upload(MultipartFile file, String dir);
}
