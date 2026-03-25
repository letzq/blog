package com.xy.blog.controller.system;

import com.xy.blog.framework.aspectj.annotation.Log;
import com.xy.blog.framework.aspectj.enums.BusinessType;
import com.xy.blog.framework.web.domain.Result;
import com.xy.blog.framework.web.page.TableDataInfo;
import com.xy.blog.system.dto.BlogUserAuthRoleDto;
import com.xy.blog.system.dto.BlogUserChangeStatusDto;
import com.xy.blog.system.dto.BlogUserCreateDto;
import com.xy.blog.system.dto.BlogUserQueryDto;
import com.xy.blog.system.dto.BlogUserResetPasswordDto;
import com.xy.blog.system.dto.BlogUserUpdateDto;
import com.xy.blog.system.service.IBlogUserService;
import com.xy.blog.system.vo.BlogUserAuthRoleVo;
import com.xy.blog.system.vo.BlogUserDetailVo;
import com.xy.blog.system.vo.BlogUserListVo;
import com.xy.blog.system.vo.BlogUserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台用户管理控制器。
 */
@Tag(name = "后台用户管理")
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class SysUserController {

    private final IBlogUserService blogUserService;

    /**
     * 分页查询用户列表。
     */
    @Log(title = "用户管理", businessType = BusinessType.OTHER, saveRequestData = true, saveResponseData = false)
    @Operation(summary = "分页查询用户列表")
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/list")
    public Result<TableDataInfo<BlogUserListVo>> list(BlogUserQueryDto dto) {
        return Result.success(blogUserService.selectUserPage(dto));
    }

    /**
     * 查询用户详情。
     */
    @Log(title = "用户管理", businessType = BusinessType.OTHER, saveRequestData = true, saveResponseData = false)
    @Operation(summary = "查询用户详情")
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/{userId}")
    public Result<BlogUserDetailVo> getInfo(@PathVariable Long userId) {
        return Result.success(blogUserService.selectUserDetail(userId));
    }

    /**
     * 新增用户。
     */
    @Log(title = "用户管理", businessType = BusinessType.INSERT, saveRequestData = true, saveResponseData = false)
    @Operation(summary = "新增用户")
    @PreAuthorize("hasRole('admin')")
    @PostMapping
    public Result<BlogUserVo> add(@RequestBody @Valid BlogUserCreateDto dto) {
        return Result.success(blogUserService.createUser(dto));
    }

    /**
     * 修改用户。
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE, saveRequestData = true, saveResponseData = false)
    @Operation(summary = "修改用户")
    @PreAuthorize("hasRole('admin')")
    @PutMapping
    public Result<String> edit(@RequestBody @Valid BlogUserUpdateDto dto) {
        blogUserService.updateUser(dto);
        return Result.success("修改成功");
    }

    /**
     * 查询用户角色分配信息。
     */
    @Log(title = "用户管理", businessType = BusinessType.OTHER, saveRequestData = true, saveResponseData = false)
    @Operation(summary = "查询用户角色分配信息")
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/authRole/{userId}")
    public Result<BlogUserAuthRoleVo> authRole(@PathVariable Long userId) {
        return Result.success(blogUserService.selectUserAuthRoleInfo(userId));
    }

    /**
     * 更新用户角色。
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE, saveRequestData = true, saveResponseData = false)
    @Operation(summary = "更新用户角色")
    @PreAuthorize("hasRole('admin')")
    @PutMapping("/authRole")
    public Result<String> updateAuthRole(@RequestBody @Valid BlogUserAuthRoleDto dto) {
        blogUserService.updateUserRoles(dto);
        return Result.success("角色分配成功");
    }

    /**
     * 修改用户状态。
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE, saveRequestData = true, saveResponseData = false)
    @Operation(summary = "修改用户状态")
    @PreAuthorize("hasRole('admin')")
    @PutMapping("/changeStatus")
    public Result<String> changeStatus(@RequestBody @Valid BlogUserChangeStatusDto dto) {
        blogUserService.changeUserStatus(dto);
        return Result.success("状态修改成功");
    }

    /**
     * 重置用户密码。
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE, saveRequestData = true, saveResponseData = false)
    @Operation(summary = "重置用户密码")
    @PreAuthorize("hasRole('admin')")
    @PutMapping("/resetPwd")
    public Result<String> resetPwd(@RequestBody @Valid BlogUserResetPasswordDto dto) {
        blogUserService.resetUserPassword(dto);
        return Result.success("密码重置成功");
    }

    /**
     * 删除用户。
     */
    @Log(title = "用户管理", businessType = BusinessType.DELETE, saveRequestData = true, saveResponseData = false)
    @Operation(summary = "删除用户")
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/{userIds}")
    public Result<String> remove(@PathVariable List<Long> userIds) {
        blogUserService.deleteUsers(userIds);
        return Result.success("删除成功");
    }
}
