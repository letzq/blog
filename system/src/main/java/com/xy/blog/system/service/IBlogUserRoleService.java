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

    /**
     * 根据用户ID查询角色ID列表。
     */
    List<Long> listRoleIdsByUserId(Long userId);

    /**
     * 重新绑定用户角色。
     */
    void replaceUserRoles(Long userId, List<Long> roleIds);

    /**
     * 删除指定用户的角色绑定。
     */
    void deleteByUserIds(List<Long> userIds);
}
