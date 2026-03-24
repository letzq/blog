package com.xy.blog.security.service;

import com.xy.blog.framework.security.service.UserAuthorityService;
import com.xy.blog.system.service.IBlogUserRoleService;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * 用户权限加载实现。
 * 当前阶段按角色控制，将角色标识转换为 Spring Security 可识别的 ROLE_ 权限。
 */
@Service
@RequiredArgsConstructor
public class UserAuthorityServiceImpl implements UserAuthorityService {

    private final IBlogUserRoleService blogUserRoleService;

    @Override
    public Collection<? extends GrantedAuthority> loadAuthorities(Long userId) {
        if (userId == null) {
            return Collections.emptyList();
        }
        List<String> roleKeys = blogUserRoleService.listRoleKeysByUserId(userId);
        if (roleKeys == null || roleKeys.isEmpty()) {
            return Collections.emptyList();
        }
        return roleKeys.stream()
            .filter(roleKey -> roleKey != null && !roleKey.isBlank())
            .map(roleKey -> new SimpleGrantedAuthority("ROLE_" + roleKey))
            .toList();
    }
}
