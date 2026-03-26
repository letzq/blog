package com.xy.blog.controller.app;

import com.xy.blog.framework.web.domain.Result;
import com.xy.blog.system.service.IBlogArticleCategoryService;
import com.xy.blog.system.vo.AppCategoryListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前台文章分类控制器。
 */
@Tag(name = "前台文章分类")
@RestController
@RequestMapping("/app/category")
@RequiredArgsConstructor
public class AppCategoryController {

    private final IBlogArticleCategoryService blogArticleCategoryService;

    /**
     * 查询前台启用分类列表。
     */
    @Operation(summary = "查询前台分类列表", description = "未登录也可访问，只返回启用状态的分类，并附带每个分类下的已发布文章数量。")
    @GetMapping("/list")
    public Result<List<AppCategoryListVo>> list() {
        return Result.success(blogArticleCategoryService.listPublicCategories());
    }
}