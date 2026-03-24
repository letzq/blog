package com.xy.blog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.blog.system.entity.po.BlogOperLog;

/**
 * 操作日志服务接口。
 */
public interface IBlogOperLogService extends IService<BlogOperLog> {

    /**
     * 保存操作日志。
     *
     * @param log 操作日志
     */
    void saveOperLog(BlogOperLog log);
}
