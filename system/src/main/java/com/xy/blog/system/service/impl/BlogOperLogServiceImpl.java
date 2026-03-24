package com.xy.blog.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.blog.system.entity.po.BlogOperLog;
import com.xy.blog.system.mapper.BlogOperLogMapper;
import com.xy.blog.system.service.IBlogOperLogService;
import org.springframework.stereotype.Service;

/**
 * 操作日志服务实现。
 */
@Service
public class BlogOperLogServiceImpl extends ServiceImpl<BlogOperLogMapper, BlogOperLog> implements IBlogOperLogService {

    @Override
    public void saveOperLog(BlogOperLog log) {
        this.save(log);
    }
}
