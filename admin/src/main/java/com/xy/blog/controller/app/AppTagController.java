package com.xy.blog.controller.app;

import com.xy.blog.framework.web.domain.Result;
import com.xy.blog.system.service.IBlogTagService;
import com.xy.blog.system.vo.AppTagListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前台标签控制器。
 */
@Tag(name = "前台标签")
@RestController
@RequestMapping("/app/tag")
@RequiredArgsConstructor
public class AppTagController {

    private final IBlogTagService blogTagService;

    /**
     * 查询前台启用标签列表。
     */
    @Operation(summary = "查询前台标签列表", description = "未登录也可访问，只返回启用状态的标签，并附带每个标签下的已发布文章数量。")
    @GetMapping("/list")
    public Result<List<AppTagListVo>> list() {
        return Result.success(blogTagService.listPublicTags());
    }
}