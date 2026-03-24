package com.xy.blog.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.blog.system.entity.po.BlogUserRole;
import java.util.List;
import org.apache.ibatis.annotations.Select;

/**
 * 博客用户角色关联 Mapper 接口。
 */
public interface BlogUserRoleMapper extends BaseMapper<BlogUserRole> {

    /**
     * 根据用户ID查询角色权限字符列表。
     */
    @Select("""
        SELECT DISTINCT role.role_key
        FROM blog_user_role user_role
        INNER JOIN blog_role role ON role.role_id = user_role.role_id
        WHERE user_role.user_id = #{userId}
          AND role.status = '1'
          AND role.del_flag = '0'
        """)
    List<String> selectRoleKeysByUserId(Long userId);
}
