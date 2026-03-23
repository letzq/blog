package com.xy.blog.controller;

import com.xy.blog.framework.web.domain.Result;
import com.xy.blog.system.domain.entity.SysUser;
import com.xy.blog.system.service.ISysUserService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final ISysUserService sysUserService;

    @GetMapping("/ping")
    public Result<Map<String, Object>> ping() {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "pong");
        data.put("module", "admin");
        data.put("status", "ok");
        return Result.success(data);
    }

    @GetMapping("/user/demo")
    public Result<SysUser> demoUser() {
        SysUser user = sysUserService.getByUserName("admin");
        return Result.success(user);
    }
}
