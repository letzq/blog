package com.xy.blog.controller.app;

import com.xy.blog.framework.web.domain.Result;
import com.xy.blog.framework.web.page.TableDataInfo;
import com.xy.blog.system.dto.BlogUserArticleCreateDto;
import com.xy.blog.system.dto.BlogUserArticlePublishDto;
import com.xy.blog.system.dto.BlogUserArticleQueryDto;
import com.xy.blog.system.dto.BlogUserArticleUpdateDto;
import com.xy.blog.system.service.IBlogArticleService;
import com.xy.blog.system.vo.BlogUserArticleDetailVo;
import com.xy.blog.system.vo.BlogUserArticleListVo;
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
 * 前台当前登录用户文章控制器。
 */
@Tag(name = "前台用户文章")
@RestController
@RequestMapping("/app/user/article")
@RequiredArgsConstructor
public class AppUserArticleController {

    private final IBlogArticleService blogArticleService;

    @Operation(summary = "分页查询我的文章列表", description = "需要登录，只返回当前登录用户自己创建的文章。")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public Result<TableDataInfo<BlogUserArticleListVo>> list(BlogUserArticleQueryDto dto) {
        return Result.success(blogArticleService.selectCurrentUserArticlePage(dto));
    }

    @Operation(summary = "查询我的文章详情", description = "需要登录，只允许查看当前登录用户自己的文章。")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{articleId}")
    public Result<BlogUserArticleDetailVo> getInfo(@PathVariable Long articleId) {
        return Result.success(blogArticleService.selectCurrentUserArticleDetail(articleId));
    }

    @Operation(summary = "新增我的文章", description = "需要登录，新增文章后默认归属当前登录用户。")
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public Result<Long> add(@RequestBody @Valid BlogUserArticleCreateDto dto) {
        return Result.success(blogArticleService.createCurrentUserArticle(dto));
    }

    @Operation(summary = "修改我的文章", description = "需要登录，只允许修改当前登录用户自己的文章。")
    @PreAuthorize("isAuthenticated()")
    @PutMapping
    public Result<String> edit(@RequestBody @Valid BlogUserArticleUpdateDto dto) {
        blogArticleService.updateCurrentUserArticle(dto);
        return Result.success("修改成功");
    }

    @Operation(summary = "发布或下线我的文章", description = "需要登录，只允许修改当前登录用户自己的文章发布状态。")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/publish")
    public Result<String> publish(@RequestBody @Valid BlogUserArticlePublishDto dto) {
        blogArticleService.changeCurrentUserArticleStatus(dto);
        return Result.success("状态修改成功");
    }

    @Operation(summary = "删除我的文章", description = "需要登录，只允许删除当前登录用户自己的文章。")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{articleIds}")
    public Result<String> remove(@PathVariable List<Long> articleIds) {
        blogArticleService.deleteCurrentUserArticles(articleIds);
        return Result.success("删除成功");
    }
}