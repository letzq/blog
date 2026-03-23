package com.xy.blog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.blog.system.domain.entity.SysUser;

public interface ISysUserService extends IService<SysUser> {

    SysUser getByUserName(String userName);
}
