package com.xy.blog.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.blog.system.entity.po.BlogUserRole;
import com.xy.blog.system.mapper.BlogUserRoleMapper;
import com.xy.blog.system.service.IBlogUserRoleService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 博客用户角色关联服务实现。
 */
@Service
public class BlogUserRoleServiceImpl extends ServiceImpl<BlogUserRoleMapper, BlogUserRole> implements IBlogUserRoleService {

    @Override
    public List<String> listRoleKeysByUserId(Long userId) {
        return this.baseMapper.selectRoleKeysByUserId(userId);
    }
}