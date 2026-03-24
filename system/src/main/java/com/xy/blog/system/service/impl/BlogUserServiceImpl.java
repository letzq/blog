package com.xy.blog.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.blog.framework.exception.BusinessException;
import com.xy.blog.system.dto.BlogUserCreateDto;
import com.xy.blog.system.entity.po.BlogUser;
import com.xy.blog.system.mapper.BlogUserMapper;
import com.xy.blog.system.service.IBlogUserService;
import com.xy.blog.system.vo.BlogUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 博客用户服务实现。
 */
@RequiredArgsConstructor
@Service
public class BlogUserServiceImpl extends ServiceImpl<BlogUserMapper, BlogUser> implements IBlogUserService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public BlogUser getByUserName(String userName) {
        return this.getOne(Wrappers.<BlogUser>lambdaQuery().eq(BlogUser::getUserName, userName));
    }

    @Override
    public BlogUser getByEmail(String email) {
        return this.getOne(Wrappers.<BlogUser>lambdaQuery().eq(BlogUser::getEmail, email));
    }

    @Override
    public BlogUserVo createUser(BlogUserCreateDto dto) {
        if (this.getByUserName(dto.getUserName()) != null) {
            throw new BusinessException("用户名已存在");
        }
        if (dto.getEmail() != null && this.getByEmail(dto.getEmail()) != null) {
            throw new BusinessException("邮箱已被注册");
        }

        BlogUser user = new BlogUser();
        user.setUserName(dto.getUserName());
        user.setNickName(dto.getNickName());
        user.setEmail(dto.getEmail());
        user.setPhonenumber(dto.getPhonenumber());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setStatus("1");
        user.setDelFlag("0");
        this.save(user);

        BlogUserVo vo = new BlogUserVo();
        vo.setUserId(user.getUserId());
        vo.setUserName(user.getUserName());
        vo.setNickName(user.getNickName());
        vo.setEmail(user.getEmail());
        vo.setPhonenumber(user.getPhonenumber());
        vo.setAvatar(user.getAvatar());
        vo.setStatus(user.getStatus());
        return vo;
    }

    @Override
    public void updatePasswordByUserId(Long userId, String encodedPassword) {
        BlogUser user = new BlogUser();
        user.setUserId(userId);
        user.setPassword(encodedPassword);
        this.updateById(user);
    }
}