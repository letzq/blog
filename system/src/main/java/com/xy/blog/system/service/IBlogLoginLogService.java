package com.xy.blog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.blog.system.entity.po.BlogLoginLog;

/**
 * 登录日志服务接口。
 */
public interface IBlogLoginLogService extends IService<BlogLoginLog> {

    /**
     * 保存登录日志。
     *
     * @param log 登录日志
     */
    void saveLoginLog(BlogLoginLog log);
}
