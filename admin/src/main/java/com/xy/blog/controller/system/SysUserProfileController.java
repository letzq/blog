package com.xy.blog.controller.system;

import com.xy.blog.framework.aspectj.annotation.Log;
import com.xy.blog.framework.aspectj.enums.BusinessType;
import com.xy.blog.framework.web.domain.Result;
import com.xy.blog.system.dto.BlogUserProfileAvatarDto;
import com.xy.blog.system.dto.BlogUserProfilePasswordDto;
import com.xy.blog.system.dto.BlogUserProfileUpdateDto;
import com.xy.blog.system.service.IBlogUserService;
import com.xy.blog.system.vo.CurrentUserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 当前登录用户个人中心控制器。
 */
@Tag(name = "个人中心")
@RestController
@RequestMapping("/system/user/profile")
@RequiredArgsConstructor
public class SysUserProfileController {

    private final IBlogUserService blogUserService;

    /**
     * 查询当前登录用户个人资料。
     */
    @Operation(summary = "查询当前登录用户个人资料")
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public Result<CurrentUserInfoVo> profile() {
        return Result.success(blogUserService.getCurrentUserProfile());
    }

    /**
     * 修改当前登录用户个人资料。
     */
    @Log(title = "个人中心", businessType = BusinessType.UPDATE, saveRequestData = true, saveResponseData = false)
    @Operation(summary = "修改当前登录用户个人资料")
    @PreAuthorize("isAuthenticated()")
    @PutMapping
    public Result<String> updateProfile(@RequestBody @Valid BlogUserProfileUpdateDto dto) {
        blogUserService.updateCurrentUserProfile(dto);
        return Result.success("个人资料修改成功");
    }

    /**
     * 修改当前登录用户密码。
     */
    @Log(title = "个人中心", businessType = BusinessType.UPDATE, saveRequestData = true, saveResponseData = false)
    @Operation(summary = "修改当前登录用户密码")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/updatePwd")
    public Result<String> updatePassword(@RequestBody @Valid BlogUserProfilePasswordDto dto) {
        blogUserService.updateCurrentUserPassword(dto);
        return Result.success("密码修改成功，请重新登录");
    }

    /**
     * 修改当前登录用户头像。
     */
    @Log(title = "个人中心", businessType = BusinessType.UPDATE, saveRequestData = true, saveResponseData = false)
    @Operation(summary = "修改当前登录用户头像")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/avatar")
    public Result<String> updateAvatar(@RequestBody @Valid BlogUserProfileAvatarDto dto) {
        blogUserService.updateCurrentUserAvatar(dto);
        return Result.success("头像修改成功");
    }
}
