package com.xy.blog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.blog.system.dto.BlogUserCreateDto;
import com.xy.blog.system.entity.po.BlogUser;
import com.xy.blog.system.vo.BlogUserVo;

/**
 * 博客用户服务接口。
 */
public interface IBlogUserService extends IService<BlogUser> {

    /**
     * 根据用户名查询用户。
     */
    BlogUser getByUserName(String userName);

    /**
     * 根据邮箱查询用户。
     */
    BlogUser getByEmail(String email);

    /**
     * 创建用户。
     */
    BlogUserVo createUser(BlogUserCreateDto dto);

    /**
     * 根据用户ID更新密码。
     */
    void updatePasswordByUserId(Long userId, String encodedPassword);
}