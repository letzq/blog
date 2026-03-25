package com.xy.blog.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.blog.system.entity.po.BlogRole;
import com.xy.blog.system.mapper.BlogRoleMapper;
import com.xy.blog.system.service.IBlogRoleService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 博客角色服务实现。
 */
@Service
public class BlogRoleServiceImpl extends ServiceImpl<BlogRoleMapper, BlogRole> implements IBlogRoleService {

    @Override
    public List<BlogRole> listAssignableRoles() {
        return this.list(Wrappers.<BlogRole>lambdaQuery()
            .eq(BlogRole::getStatus, "1")
            .eq(BlogRole::getDelFlag, "0")
            .orderByAsc(BlogRole::getRoleSort, BlogRole::getRoleId));
    }
}
