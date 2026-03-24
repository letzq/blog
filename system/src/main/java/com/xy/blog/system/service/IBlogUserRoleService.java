package com.xy.blog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.blog.system.entity.po.BlogUserRole;
import java.util.List;

/**
 * 博客用户角色关联服务接口。
 */
public interface IBlogUserRoleService extends IService<BlogUserRole> {

    /**
     * 根据用户ID查询角色权限字符列表。
     */
    List<String> listRoleKeysByUserId(Long userId);
}