package com.xy.blog.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.blog.system.domain.entity.SysUser;
import com.xy.blog.system.mapper.SysUserMapper;
import com.xy.blog.system.service.ISysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser getByUserName(String userName) {
        return this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, userName));
    }
}
