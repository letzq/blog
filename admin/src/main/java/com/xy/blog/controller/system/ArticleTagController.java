package com.xy.blog.controller.system;

import com.xy.blog.framework.aspectj.annotation.Log;
import com.xy.blog.framework.aspectj.enums.BusinessType;
import com.xy.blog.framework.web.domain.Result;
import com.xy.blog.framework.web.page.TableDataInfo;
import com.xy.blog.system.dto.BlogTagChangeStatusDto;
import com.xy.blog.system.dto.BlogTagCreateDto;
import com.xy.blog.system.dto.BlogTagQueryDto;
import com.xy.blog.system.dto.BlogTagUpdateDto;
import com.xy.blog.system.service.IBlogTagService;
import com.xy.blog.system.vo.BlogTagDetailVo;
import com.xy.blog.system.vo.BlogTagListVo;
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
 * 后台标签控制器。
 */
@Tag(name = "后台标签管理")
@RestController
@RequestMapping("/system/article/tag")
@RequiredArgsConstructor
public class ArticleTagController {

    private final IBlogTagService blogTagService;

    @Operation(summary = "分页查询后台标签列表", description = "需要 admin 或 editor 角色，返回后台标签分页数据。")
    @Log(title = "标签管理", businessType = BusinessType.OTHER, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @GetMapping("/list")
    public Result<TableDataInfo<BlogTagListVo>> list(BlogTagQueryDto dto) {
        return Result.success(blogTagService.selectAdminTagPage(dto));
    }

    @Operation(summary = "查询后台标签详情", description = "需要 admin 或 editor 角色，返回后台标签详情。")
    @Log(title = "标签管理", businessType = BusinessType.OTHER, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @GetMapping("/{tagId}")
    public Result<BlogTagDetailVo> getInfo(@PathVariable Long tagId) {
        return Result.success(blogTagService.selectAdminTagDetail(tagId));
    }

    @Operation(summary = "新增后台标签", description = "需要 admin 或 editor 角色，新增一条标签数据。")
    @Log(title = "标签管理", businessType = BusinessType.INSERT, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @PostMapping
    public Result<Long> add(@RequestBody @Valid BlogTagCreateDto dto) {
        return Result.success(blogTagService.createTag(dto));
    }

    @Operation(summary = "修改后台标签", description = "需要 admin 或 editor 角色，修改一条标签数据。")
    @Log(title = "标签管理", businessType = BusinessType.UPDATE, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @PutMapping
    public Result<String> edit(@RequestBody @Valid BlogTagUpdateDto dto) {
        blogTagService.updateTag(dto);
        return Result.success("修改成功");
    }

    @Operation(summary = "修改后台标签状态", description = "需要 admin 或 editor 角色，切换标签启用状态。")
    @Log(title = "标签管理", businessType = BusinessType.UPDATE, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @PutMapping("/changeStatus")
    public Result<String> changeStatus(@RequestBody @Valid BlogTagChangeStatusDto dto) {
        blogTagService.changeTagStatus(dto);
        return Result.success("状态修改成功");
    }

    @Operation(summary = "删除后台标签", description = "需要 admin 或 editor 角色，删除一条或多条标签数据。")
    @Log(title = "标签管理", businessType = BusinessType.DELETE, saveRequestData = true, saveResponseData = false)
    @PreAuthorize("hasAnyRole('admin','editor')")
    @DeleteMapping("/{tagIds}")
    public Result<String> remove(@PathVariable List<Long> tagIds) {
        blogTagService.deleteTags(tagIds);
        return Result.success("删除成功");
    }
}