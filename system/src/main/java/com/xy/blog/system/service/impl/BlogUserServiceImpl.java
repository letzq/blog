package com.xy.blog.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.blog.framework.exception.BusinessException;
import com.xy.blog.framework.security.context.UserContext;
import com.xy.blog.framework.security.service.TokenService;
import com.xy.blog.framework.web.page.TableDataInfo;
import com.xy.blog.system.dto.BlogUserAuthRoleDto;
import com.xy.blog.system.dto.BlogUserChangeStatusDto;
import com.xy.blog.system.dto.BlogUserCreateDto;
import com.xy.blog.system.dto.BlogUserProfileAvatarDto;
import com.xy.blog.system.dto.BlogUserProfilePasswordDto;
import com.xy.blog.system.dto.BlogUserProfileUpdateDto;
import com.xy.blog.system.dto.BlogUserQueryDto;
import com.xy.blog.system.dto.BlogUserResetPasswordDto;
import com.xy.blog.system.dto.BlogUserUpdateDto;
import com.xy.blog.system.entity.po.BlogRole;
import com.xy.blog.system.entity.po.BlogUser;
import com.xy.blog.system.mapper.BlogUserMapper;
import com.xy.blog.system.service.IBlogRoleService;
import com.xy.blog.system.service.IBlogUserRoleService;
import com.xy.blog.system.service.IBlogUserService;
import com.xy.blog.system.vo.BlogRoleOptionVo;
import com.xy.blog.system.vo.BlogUserAuthRoleVo;
import com.xy.blog.system.vo.BlogUserDetailVo;
import com.xy.blog.system.vo.BlogUserListVo;
import com.xy.blog.system.vo.BlogUserVo;
import com.xy.blog.system.vo.CurrentUserInfoVo;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 博客用户服务实现。
 */
@RequiredArgsConstructor
@Service
public class BlogUserServiceImpl extends ServiceImpl<BlogUserMapper, BlogUser> implements IBlogUserService {

    private final PasswordEncoder passwordEncoder;
    private final IBlogUserRoleService blogUserRoleService;
    private final IBlogRoleService blogRoleService;
    private final TokenService tokenService;

    @Override
    public BlogUser getByUserName(String userName) {
        return this.getOne(Wrappers.<BlogUser>lambdaQuery().eq(BlogUser::getUserName, userName));
    }

    @Override
    public BlogUser getByEmail(String email) {
        return this.getOne(Wrappers.<BlogUser>lambdaQuery().eq(BlogUser::getEmail, email));
    }

    @Override
    public CurrentUserInfoVo getCurrentUserProfile() {
        BlogUser user = getCurrentLoginUser();
        List<String> roleKeys = blogUserRoleService.listRoleKeysByUserId(user.getUserId());
        return buildCurrentUserInfo(user, roleKeys);
    }

    @Override
    public void updateCurrentUserProfile(BlogUserProfileUpdateDto dto) {
        BlogUser user = getCurrentLoginUser();
        validateCurrentUserProfile(dto, user);
        user.setNickName(dto.getNickName());
        user.setEmail(dto.getEmail());
        user.setPhonenumber(dto.getPhonenumber());
        user.setSex(dto.getSex());
        this.updateById(user);
    }

    @Override
    public void updateCurrentUserPassword(BlogUserProfilePasswordDto dto) {
        BlogUser user = getCurrentLoginUser();
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new BusinessException("旧密码错误");
        }
        if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())) {
            throw new BusinessException("新密码不能与旧密码相同");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new BusinessException("两次输入的新密码不一致");
        }
        updatePasswordByUserId(user.getUserId(), passwordEncoder.encode(dto.getNewPassword()));
        tokenService.removeTokenByUserId(user.getUserId());
    }

    @Override
    public void updateCurrentUserAvatar(BlogUserProfileAvatarDto dto) {
        BlogUser user = getCurrentLoginUser();
        user.setAvatar(dto.getAvatar());
        this.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BlogUserVo createUser(BlogUserCreateDto dto) {
        validateCreateUser(dto);

        BlogUser user = new BlogUser();
        user.setUserName(dto.getUserName());
        user.setNickName(dto.getNickName());
        user.setEmail(dto.getEmail());
        user.setPhonenumber(dto.getPhonenumber());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setStatus(dto.getStatus());
        user.setDelFlag("0");
        this.save(user);
        blogUserRoleService.replaceUserRoles(user.getUserId(), dto.getRoleIds());
        return buildUserVo(user);
    }

    @Override
    public TableDataInfo<BlogUserListVo> selectUserPage(BlogUserQueryDto dto) {
        LocalDateTime beginTime = parseDateTime(dto.getBeginTime());
        LocalDateTime endTime = parseDateTime(dto.getEndTime());

        Page<BlogUser> page = this.page(dto.buildPage(), Wrappers.<BlogUser>lambdaQuery()
            .like(isNotBlank(dto.getUserName()), BlogUser::getUserName, dto.getUserName())
            .like(isNotBlank(dto.getNickName()), BlogUser::getNickName, dto.getNickName())
            .like(isNotBlank(dto.getEmail()), BlogUser::getEmail, dto.getEmail())
            .like(isNotBlank(dto.getPhonenumber()), BlogUser::getPhonenumber, dto.getPhonenumber())
            .eq(isNotBlank(dto.getStatus()), BlogUser::getStatus, dto.getStatus())
            .ge(beginTime != null, BlogUser::getCreateTime, beginTime)
            .le(endTime != null, BlogUser::getCreateTime, endTime)
            .orderByDesc(BlogUser::getCreateTime));

        List<BlogUserListVo> rows = page.getRecords().stream()
            .map(this::buildUserListVo)
            .toList();

        TableDataInfo<BlogUserListVo> data = new TableDataInfo<>();
        data.setTotal(page.getTotal());
        data.setRows(rows);
        return data;
    }

    @Override
    public BlogUserDetailVo selectUserDetail(Long userId) {
        BlogUser user = getExistingUser(userId);
        List<String> roleKeys = blogUserRoleService.listRoleKeysByUserId(userId);
        List<Long> roleIds = blogUserRoleService.listRoleIdsByUserId(userId);
        return BlogUserDetailVo.builder()
            .userId(user.getUserId())
            .userName(user.getUserName())
            .nickName(user.getNickName())
            .email(user.getEmail())
            .phonenumber(user.getPhonenumber())
            .avatar(user.getAvatar())
            .sex(user.getSex())
            .status(user.getStatus())
            .roleIds(roleIds == null ? Collections.emptyList() : roleIds)
            .roles(roleKeys == null ? Collections.emptyList() : roleKeys)
            .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(BlogUserUpdateDto dto) {
        BlogUser user = getExistingUser(dto.getUserId());
        validateUpdateUser(dto, user);

        user.setNickName(dto.getNickName());
        user.setEmail(dto.getEmail());
        user.setPhonenumber(dto.getPhonenumber());
        user.setAvatar(dto.getAvatar());
        user.setSex(dto.getSex());
        user.setStatus(dto.getStatus());
        this.updateById(user);
        blogUserRoleService.replaceUserRoles(user.getUserId(), dto.getRoleIds());
        tokenService.removeTokenByUserId(user.getUserId());
    }

    @Override
    public BlogUserAuthRoleVo selectUserAuthRoleInfo(Long userId) {
        BlogUser user = getExistingUser(userId);
        List<Long> roleIds = blogUserRoleService.listRoleIdsByUserId(userId);
        List<BlogRoleOptionVo> roleOptions = blogRoleService.listAssignableRoles().stream()
            .map(this::buildRoleOptionVo)
            .toList();
        return BlogUserAuthRoleVo.builder()
            .userId(user.getUserId())
            .userName(user.getUserName())
            .nickName(user.getNickName())
            .roleIds(roleIds == null ? Collections.emptyList() : roleIds)
            .roleOptions(roleOptions)
            .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserRoles(BlogUserAuthRoleDto dto) {
        BlogUser user = getExistingUser(dto.getUserId());
        ensureRoleIds(dto.getRoleIds());
        blogUserRoleService.replaceUserRoles(user.getUserId(), dto.getRoleIds());
        tokenService.removeTokenByUserId(user.getUserId());
    }

    @Override
    public void changeUserStatus(BlogUserChangeStatusDto dto) {
        BlogUser user = getExistingUser(dto.getUserId());
        user.setStatus(dto.getStatus());
        this.updateById(user);
        if (!"1".equals(dto.getStatus())) {
            tokenService.removeTokenByUserId(user.getUserId());
        }
    }

    @Override
    public void resetUserPassword(BlogUserResetPasswordDto dto) {
        BlogUser user = getExistingUser(dto.getUserId());
        updatePasswordByUserId(user.getUserId(), passwordEncoder.encode(dto.getPassword()));
        tokenService.removeTokenByUserId(user.getUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUsers(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            throw new BusinessException("请选择要删除的用户");
        }
        Long currentUserId = UserContext.getUserId();
        if (currentUserId != null && userIds.contains(currentUserId)) {
            throw new BusinessException("不能删除当前登录用户");
        }
        List<BlogUser> users = this.listByIds(userIds);
        if (users.isEmpty()) {
            throw new BusinessException("用户不存在");
        }
        users.forEach(user -> {
            user.setDelFlag("1");
            user.setStatus("0");
        });
        this.updateBatchById(users);
        blogUserRoleService.deleteByUserIds(userIds);
        userIds.forEach(tokenService::removeTokenByUserId);
    }

    @Override
    public void updatePasswordByUserId(Long userId, String encodedPassword) {
        BlogUser user = new BlogUser();
        user.setUserId(userId);
        user.setPassword(encodedPassword);
        this.updateById(user);
    }

    private void validateCreateUser(BlogUserCreateDto dto) {
        if (this.getByUserName(dto.getUserName()) != null) {
            throw new BusinessException("用户名已存在");
        }
        if (isNotBlank(dto.getEmail()) && this.getByEmail(dto.getEmail()) != null) {
            throw new BusinessException("邮箱已被注册");
        }
        ensureRoleIds(dto.getRoleIds());
    }

    private void validateUpdateUser(BlogUserUpdateDto dto, BlogUser currentUser) {
        if (isNotBlank(dto.getEmail())) {
            BlogUser emailUser = this.getByEmail(dto.getEmail());
            if (emailUser != null && !emailUser.getUserId().equals(currentUser.getUserId())) {
                throw new BusinessException("邮箱已被注册");
            }
        }
        ensureRoleIds(dto.getRoleIds());
    }

    private void validateCurrentUserProfile(BlogUserProfileUpdateDto dto, BlogUser currentUser) {
        if (isNotBlank(dto.getEmail())) {
            BlogUser emailUser = this.getByEmail(dto.getEmail());
            if (emailUser != null && !emailUser.getUserId().equals(currentUser.getUserId())) {
                throw new BusinessException("邮箱已被注册");
            }
        }
        if (isNotBlank(dto.getPhonenumber())) {
            BlogUser phoneUser = this.getOne(Wrappers.<BlogUser>lambdaQuery().eq(BlogUser::getPhonenumber, dto.getPhonenumber()));
            if (phoneUser != null && !phoneUser.getUserId().equals(currentUser.getUserId())) {
                throw new BusinessException("手机号已被使用");
            }
        }
    }

    private void ensureRoleIds(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            throw new BusinessException("角色不能为空");
        }
        List<BlogRole> roles = blogRoleService.listByIds(roleIds);
        if (roles.size() != roleIds.stream().distinct().count()) {
            throw new BusinessException("存在无效角色，请重新选择");
        }
        boolean invalidRole = roles.stream().anyMatch(role -> !"1".equals(role.getStatus()) || !"0".equals(role.getDelFlag()));
        if (invalidRole) {
            throw new BusinessException("存在不可用角色，请重新选择");
        }
    }

    private BlogUser getExistingUser(Long userId) {
        BlogUser user = this.getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return user;
    }

    private BlogUser getCurrentLoginUser() {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            throw new BusinessException("当前未登录或登录状态已失效");
        }
        return getExistingUser(userId);
    }

    private CurrentUserInfoVo buildCurrentUserInfo(BlogUser user, List<String> roleKeys) {
        List<String> safeRoles = roleKeys == null ? Collections.emptyList() : roleKeys;
        return CurrentUserInfoVo.builder()
            .userId(user.getUserId())
            .userName(user.getUserName())
            .nickName(user.getNickName())
            .email(user.getEmail())
            .phonenumber(user.getPhonenumber())
            .avatar(user.getAvatar())
            .sex(user.getSex())
            .status(user.getStatus())
            .roles(safeRoles)
            .permissions(Collections.emptyList())
            .build();
    }

    private BlogUserVo buildUserVo(BlogUser user) {
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

    private BlogUserListVo buildUserListVo(BlogUser user) {
        List<String> roleKeys = blogUserRoleService.listRoleKeysByUserId(user.getUserId());
        return BlogUserListVo.builder()
            .userId(user.getUserId())
            .userName(user.getUserName())
            .nickName(user.getNickName())
            .email(user.getEmail())
            .phonenumber(user.getPhonenumber())
            .avatar(user.getAvatar())
            .status(user.getStatus())
            .loginIp(user.getLoginIp())
            .loginDate(user.getLoginDate())
            .createTime(user.getCreateTime())
            .roles(roleKeys == null ? Collections.emptyList() : roleKeys)
            .build();
    }

    private BlogRoleOptionVo buildRoleOptionVo(BlogRole role) {
        return BlogRoleOptionVo.builder()
            .roleId(role.getRoleId())
            .roleName(role.getRoleName())
            .roleKey(role.getRoleKey())
            .build();
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }

    private LocalDateTime parseDateTime(String value) {
        if (!isNotBlank(value)) {
            return null;
        }
        try {
            return LocalDateTime.parse(value.replace(" ", "T"));
        } catch (DateTimeParseException exception) {
            throw new BusinessException("时间格式错误，请使用 yyyy-MM-dd HH:mm:ss");
        }
    }
}
