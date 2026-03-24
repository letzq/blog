package com.xy.blog.controller;

import com.xy.blog.framework.web.domain.Result;
import com.xy.blog.system.dto.BlogUserCreateDto;
import com.xy.blog.system.entity.po.BlogUser;
import com.xy.blog.system.service.IBlogUserService;
import com.xy.blog.system.vo.BlogUserVo;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器。
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final IBlogUserService blogUserService;

    /**
     * 连通性测试接口。
     */
    @GetMapping("/ping")
    public Result<Map<String, Object>> ping() {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "pong");
        data.put("module", "admin");
        data.put("status", "ok");
        return Result.success(data);
    }

    /**
     * 用户查询示例接口，仅 admin 可以访问。
     */
    @GetMapping("/user/demo")
    @PreAuthorize("hasRole('admin')")
    public Result<BlogUser> demoUser() {
        BlogUser user = blogUserService.getByUserName("admin");
        return Result.success(user);
    }

    /**
     * 角色权限示例接口，admin 或 editor 可以访问。
     */
    @GetMapping("/role/demo")
    @PreAuthorize("hasAnyRole('admin','editor')")
    public Result<Map<String, Object>> roleDemo() {
        Map<String, Object> data = new HashMap<>();
        data.put("scope", "admin/editor");
        data.put("status", "granted");
        return Result.success(data);
    }

    /**
     * 参数校验示例接口。
     */
    @PostMapping("/user/validate")
    public Result<BlogUserCreateDto> validateUser(@RequestBody @Valid BlogUserCreateDto dto) {
        return Result.success(dto);
    }

    /**
     * 新增用户示例接口。
     */
    @PostMapping("/user/create")
    public Result<BlogUserVo> createUser(@RequestBody @Valid BlogUserCreateDto dto) {
        return Result.success(blogUserService.createUser(dto));
    }
}