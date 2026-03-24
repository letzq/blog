package com.xy.blog.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.blog.system.entity.po.BlogLoginLog;
import com.xy.blog.system.mapper.BlogLoginLogMapper;
import com.xy.blog.system.service.IBlogLoginLogService;
import org.springframework.stereotype.Service;

/**
 * 登录日志服务实现。
 */
@Service
public class BlogLoginLogServiceImpl extends ServiceImpl<BlogLoginLogMapper, BlogLoginLog> implements IBlogLoginLogService {

    @Override
    public void saveLoginLog(BlogLoginLog log) {
        this.save(log);
    }
}
