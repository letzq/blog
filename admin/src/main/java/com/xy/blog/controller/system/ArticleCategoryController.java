package com.xy.blog.controller.system;

import com.xy.blog.framework.aspectj.annotation.Log;
import com.xy.blog.framework.aspectj.enums.BusinessType;
import com.xy.blog.framework.web.domain.Result;
import com.xy.blog.framework.web.page.TableDataInfo;
import com.xy.blog.system.dto.BlogArticleCategoryChangeStatusDto;
import com.xy.blog.system.dto.BlogArticleCategoryCreateDto;
import com.xy.blog.system.dto.BlogArticleCategoryQueryDto;
import com.xy.blog.system.dto.BlogArticleCategoryUpdateDto;
import com.xy.blog.system.service.IBlogArticleCategoryService;
import com.xy.blog.system.vo.BlogArticleCategoryDetailVo;
import com.xy.blog.system.vo.BlogArticleCategoryListVo;
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
 * 后台文章分类控制器。
 */
@Tag(name = "后台文章分类管理")
@RestController
@RequestMapping("/system/article/category")
@RequiredArgsConstructor
public class ArticleCategoryController {

    private final IBlogArticleCategoryService blogArticleCategoryService;

    @Operation(summary = "分页查询后台分类列表", description = "需要 admin 或 editor 角色，返回后台文章分类分页数据。")
    @Log(title = "文章分类管理", businessType = BusinessType.OTHER, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @GetMapping("/list")
    public Result<TableDataInfo<BlogArticleCategoryListVo>> list(BlogArticleCategoryQueryDto dto) {
        return Result.success(blogArticleCategoryService.selectAdminCategoryPage(dto));
    }

    @Operation(summary = "查询后台分类详情", description = "需要 admin 或 editor 角色，返回后台文章分类详情。")
    @Log(title = "文章分类管理", businessType = BusinessType.OTHER, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @GetMapping("/{categoryId}")
    public Result<BlogArticleCategoryDetailVo> getInfo(@PathVariable Long categoryId) {
        return Result.success(blogArticleCategoryService.selectAdminCategoryDetail(categoryId));
    }

    @Operation(summary = "新增后台分类", description = "需要 admin 或 editor 角色，新增一条文章分类数据。")
    @Log(title = "文章分类管理", businessType = BusinessType.INSERT, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @PostMapping
    public Result<Long> add(@RequestBody @Valid BlogArticleCategoryCreateDto dto) {
        return Result.success(blogArticleCategoryService.createCategory(dto));
    }

    @Operation(summary = "修改后台分类", description = "需要 admin 或 editor 角色，修改一条文章分类数据。")
    @Log(title = "文章分类管理", businessType = BusinessType.UPDATE, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @PutMapping
    public Result<String> edit(@RequestBody @Valid BlogArticleCategoryUpdateDto dto) {
        blogArticleCategoryService.updateCategory(dto);
        return Result.success("修改成功");
    }

    @Operation(summary = "修改后台分类状态", description = "需要 admin 或 editor 角色，切换文章分类启用状态。")
    @Log(title = "文章分类管理", businessType = BusinessType.UPDATE, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @PutMapping("/changeStatus")
    public Result<String> changeStatus(@RequestBody @Valid BlogArticleCategoryChangeStatusDto dto) {
        blogArticleCategoryService.changeCategoryStatus(dto);
        return Result.success("状态修改成功");
    }

    @Operation(summary = "删除后台分类", description = "需要 admin 或 editor 角色，删除一条或多条文章分类数据。")
    @Log(title = "文章分类管理", businessType = BusinessType.DELETE, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @DeleteMapping("/{categoryIds}")
    public Result<String> remove(@PathVariable List<Long> categoryIds) {
        blogArticleCategoryService.deleteCategories(categoryIds);
        return Result.success("删除成功");
    }
}