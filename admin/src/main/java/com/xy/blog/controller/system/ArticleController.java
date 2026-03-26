package com.xy.blog.controller.system;

import com.xy.blog.framework.aspectj.annotation.Log;
import com.xy.blog.framework.aspectj.enums.BusinessType;
import com.xy.blog.framework.web.domain.Result;
import com.xy.blog.framework.web.page.TableDataInfo;
import com.xy.blog.system.dto.BlogArticleChangeStatusDto;
import com.xy.blog.system.dto.BlogArticleChangeTopDto;
import com.xy.blog.system.dto.BlogArticleCreateDto;
import com.xy.blog.system.dto.BlogArticleQueryDto;
import com.xy.blog.system.dto.BlogArticleUpdateDto;
import com.xy.blog.system.service.IBlogArticleService;
import com.xy.blog.system.vo.BlogArticleDetailVo;
import com.xy.blog.system.vo.BlogArticleListVo;
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
 * 后台文章管理控制器。
 */
@Tag(name = "后台文章管理")
@RestController
@RequestMapping("/system/article")
@RequiredArgsConstructor
public class ArticleController {

    private final IBlogArticleService blogArticleService;

    @Operation(summary = "分页查询后台文章列表", description = "需要 admin 或 editor 角色，返回后台文章分页数据。")
    @Log(title = "文章管理", businessType = BusinessType.OTHER, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @GetMapping("/list")
    public Result<TableDataInfo<BlogArticleListVo>> list(BlogArticleQueryDto dto) {
        return Result.success(blogArticleService.selectAdminArticlePage(dto));
    }

    @Operation(summary = "查询后台文章详情", description = "需要 admin 或 editor 角色，返回后台文章详情。")
    @Log(title = "文章管理", businessType = BusinessType.OTHER, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @GetMapping("/{articleId}")
    public Result<BlogArticleDetailVo> getInfo(@PathVariable Long articleId) {
        return Result.success(blogArticleService.selectAdminArticleDetail(articleId));
    }

    @Operation(summary = "新增后台文章", description = "需要 admin 或 editor 角色，新增一篇后台文章。")
    @Log(title = "文章管理", businessType = BusinessType.INSERT, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @PostMapping
    public Result<Long> add(@RequestBody @Valid BlogArticleCreateDto dto) {
        return Result.success(blogArticleService.createAdminArticle(dto));
    }

    @Operation(summary = "修改后台文章", description = "需要 admin 或 editor 角色，修改一篇后台文章。")
    @Log(title = "文章管理", businessType = BusinessType.UPDATE, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @PutMapping
    public Result<String> edit(@RequestBody @Valid BlogArticleUpdateDto dto) {
        blogArticleService.updateAdminArticle(dto);
        return Result.success("修改成功");
    }

    @Operation(summary = "修改后台文章状态", description = "需要 admin 或 editor 角色，切换文章草稿、发布、下线状态。")
    @Log(title = "文章管理", businessType = BusinessType.UPDATE, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @PutMapping("/changeStatus")
    public Result<String> changeStatus(@RequestBody @Valid BlogArticleChangeStatusDto dto) {
        blogArticleService.changeAdminArticleStatus(dto);
        return Result.success("状态修改成功");
    }

    @Operation(summary = "修改后台文章置顶状态", description = "需要 admin 或 editor 角色，切换文章置顶状态。")
    @Log(title = "文章管理", businessType = BusinessType.UPDATE, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @PutMapping("/changeTop")
    public Result<String> changeTop(@RequestBody @Valid BlogArticleChangeTopDto dto) {
        blogArticleService.changeAdminArticleTop(dto);
        return Result.success("置顶状态修改成功");
    }

    @Operation(summary = "删除后台文章", description = "需要 admin 或 editor 角色，删除一篇或多篇后台文章。")
    @Log(title = "文章管理", businessType = BusinessType.DELETE, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @DeleteMapping("/{articleIds}")
    public Result<String> remove(@PathVariable List<Long> articleIds) {
        blogArticleService.deleteAdminArticles(articleIds);
        return Result.success("删除成功");
    }
}