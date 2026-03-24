package com.xy.blog.framework.security.service;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

/**
 * 当前用户权限加载接口。
 * 由业务模块提供角色或权限标识，供安全框架恢复认证上下文时使用。
 */
public interface UserAuthorityService {

    /**
     * 根据用户ID加载当前用户的权限集合。
     */
    Collection<? extends GrantedAuthority> loadAuthorities(Long userId);
}
