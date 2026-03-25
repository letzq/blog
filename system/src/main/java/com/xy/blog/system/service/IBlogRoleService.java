package com.xy.blog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.blog.system.entity.po.BlogRole;
import java.util.List;

/**
 * 博客角色服务接口。
 */
public interface IBlogRoleService extends IService<BlogRole> {

    /**
     * 查询所有可分配角色。
     */
    List<BlogRole> listAssignableRoles();

    /**
     * 根据角色标识查询角色。
     */
    BlogRole getByRoleKey(String roleKey);
}
