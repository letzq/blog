package com.xy.blog.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.blog.system.entity.po.BlogUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 博客用户 Mapper 接口。
 */
public interface BlogUserMapper extends BaseMapper<BlogUser> {

    /**
     * 按用户名严格区分大小写查询用户。
     */
    @Select("""
        SELECT *
        FROM blog_user
        WHERE BINARY user_name = #{userName}
        LIMIT 1
        """)
    BlogUser selectByUserNameCaseSensitive(@Param("userName") String userName);
}
