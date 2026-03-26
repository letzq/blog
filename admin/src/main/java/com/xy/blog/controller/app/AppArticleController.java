package com.xy.blog.controller.app;

import com.xy.blog.framework.web.domain.Result;
import com.xy.blog.framework.web.page.TableDataInfo;
import com.xy.blog.system.dto.BlogArticleAppQueryDto;
import com.xy.blog.system.service.IBlogArticleService;
import com.xy.blog.system.vo.AppArticleArchiveVo;
import com.xy.blog.system.vo.AppArticleDetailVo;
import com.xy.blog.system.vo.AppArticleListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前台文章控制器。
 */
@Tag(name = "前台文章")
@RestController
@RequestMapping("/app/article")
@RequiredArgsConstructor
public class AppArticleController {

    private final IBlogArticleService blogArticleService;

    /**
     * 分页查询前台文章列表。
     */
    @Operation(summary = "分页查询前台文章列表", description = "未登录也可访问，只返回已发布文章，支持按分类和标题关键字筛选。")
    @GetMapping("/list")
    public Result<TableDataInfo<AppArticleListVo>> list(BlogArticleAppQueryDto dto) {
        return Result.success(blogArticleService.selectPublicArticlePage(dto));
    }

    /**
     * 查询前台文章详情。
     */
    @Operation(summary = "查询前台文章详情", description = "未登录也可访问，只允许查看已发布文章，查询详情时会增加浏览量。")
    @GetMapping("/{articleId}")
    public Result<AppArticleDetailVo> getInfo(@PathVariable Long articleId) {
        return Result.success(blogArticleService.selectPublicArticleDetail(articleId));
    }

    /**
     * 查询热门文章列表。
     */
    @Operation(summary = "查询热门文章列表", description = "未登录也可访问，只返回已发布文章，按浏览量倒序取前若干条数据。")
    @GetMapping("/hot/list")
    public Result<List<AppArticleListVo>> hotList(Integer limit) {
        return Result.success(blogArticleService.listPublicHotArticles(limit));
    }

    /**
     * 查询最新文章列表。
     */
    @Operation(summary = "查询最新文章列表", description = "未登录也可访问，只返回已发布文章，按发布时间倒序取前若干条数据。")
    @GetMapping("/latest/list")
    public Result<List<AppArticleListVo>> latestList(Integer limit) {
        return Result.success(blogArticleService.listPublicLatestArticles(limit));
    }

    /**
     * 查询文章归档列表。
     */
    @Operation(summary = "查询文章归档列表", description = "未登录也可访问，只返回已发布文章，并按年月分组展示归档数据。")
    @GetMapping("/archive/list")
    public Result<List<AppArticleArchiveVo>> archiveList() {
        return Result.success(blogArticleService.listPublicArchives());
    }
}