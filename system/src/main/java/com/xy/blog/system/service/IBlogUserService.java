package com.xy.blog.system.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.blog.system.dto.BlogUserCreateDto;
import com.xy.blog.system.entity.po.BlogUser;
import com.xy.blog.system.vo.BlogUserVo;
/**
 * ?????????
 */
public interface IBlogUserService extends IService<BlogUser> {
    /**
     * ??????????
     */
    BlogUser getByUserName(String userName);
    /**
     * ????????
     */
    BlogUser getByEmail(String email);
    /**
     * ???????
     */
    BlogUserVo createUser(BlogUserCreateDto dto);
}