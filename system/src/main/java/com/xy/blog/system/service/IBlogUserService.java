package com.xy.blog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
import com.xy.blog.system.entity.po.BlogUser;
import com.xy.blog.system.vo.BlogUserAuthRoleVo;
import com.xy.blog.system.vo.BlogUserDetailVo;
import com.xy.blog.system.vo.BlogUserListVo;
import com.xy.blog.system.vo.BlogUserVo;
import com.xy.blog.system.vo.CurrentUserInfoVo;
import java.util.List;

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
     * 获取当前登录用户个人资料。
     */
    CurrentUserInfoVo getCurrentUserProfile();

    /**
     * 修改当前登录用户个人资料。
     */
    void updateCurrentUserProfile(BlogUserProfileUpdateDto dto);

    /**
     * 修改当前登录用户密码。
     */
    void updateCurrentUserPassword(BlogUserProfilePasswordDto dto);

    /**
     * 修改当前登录用户头像。
     */
    void updateCurrentUserAvatar(BlogUserProfileAvatarDto dto);

    /**
     * 创建用户。
     */
    BlogUserVo createUser(BlogUserCreateDto dto);

    /**
     * 分页查询用户列表。
     */
    TableDataInfo<BlogUserListVo> selectUserPage(BlogUserQueryDto dto);

    /**
     * 查询用户详情。
     */
    BlogUserDetailVo selectUserDetail(Long userId);

    /**
     * 更新用户。
     */
    void updateUser(BlogUserUpdateDto dto);

    /**
     * 查询用户角色分配信息。
     */
    BlogUserAuthRoleVo selectUserAuthRoleInfo(Long userId);

    /**
     * 更新用户角色。
     */
    void updateUserRoles(BlogUserAuthRoleDto dto);

    /**
     * 修改用户状态。
     */
    void changeUserStatus(BlogUserChangeStatusDto dto);

    /**
     * 重置用户密码。
     */
    void resetUserPassword(BlogUserResetPasswordDto dto);

    /**
     * 删除用户。
     */
    void deleteUsers(List<Long> userIds);

    /**
     * 根据用户ID更新密码。
     */
    void updatePasswordByUserId(Long userId, String encodedPassword);
}
