package com.xy.blog.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.blog.framework.exception.BusinessException;
import com.xy.blog.framework.security.context.UserContext;
import com.xy.blog.framework.web.page.TableDataInfo;
import com.xy.blog.system.dto.BlogArticleAppQueryDto;
import com.xy.blog.system.dto.BlogUserArticleCreateDto;
import com.xy.blog.system.dto.BlogUserArticlePublishDto;
import com.xy.blog.system.dto.BlogUserArticleQueryDto;
import com.xy.blog.system.dto.BlogUserArticleUpdateDto;
import com.xy.blog.system.entity.po.BlogArticle;
import com.xy.blog.system.mapper.BlogArticleMapper;
import com.xy.blog.system.mapper.BlogArticleTagMapper;
import com.xy.blog.system.mapper.BlogTagMapper;
import com.xy.blog.system.service.IBlogArticleCategoryService;
import com.xy.blog.system.service.IBlogArticleService;
import com.xy.blog.system.vo.AppArticleArchiveItemVo;
import com.xy.blog.system.vo.AppArticleArchiveVo;
import com.xy.blog.system.vo.AppArticleDetailVo;
import com.xy.blog.system.vo.AppArticleListVo;
import com.xy.blog.system.vo.BlogUserArticleDetailVo;
import com.xy.blog.system.vo.BlogUserArticleListVo;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 博客文章服务实现。
 */
@Service
@RequiredArgsConstructor
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements IBlogArticleService {

    private static final int DEFAULT_LIST_LIMIT = 10;
    private static final int MAX_LIST_LIMIT = 20;

    private final IBlogArticleCategoryService blogArticleCategoryService;
    private final BlogTagMapper blogTagMapper;
    private final BlogArticleTagMapper blogArticleTagMapper;

    @Override
    public TableDataInfo<AppArticleListVo> selectPublicArticlePage(BlogArticleAppQueryDto dto) {
        Page<AppArticleListVo> page = dto.buildPage();
        IPage<AppArticleListVo> result = this.baseMapper.selectPublicArticlePage(page, dto);
        return TableDataInfo.build(result);
    }

    @Override
    public AppArticleDetailVo selectPublicArticleDetail(Long articleId) {
        AppArticleDetailVo detail = this.baseMapper.selectPublicArticleDetail(articleId);
        if (detail == null) {
            throw new BusinessException("文章不存在或未发布");
        }
        this.baseMapper.increaseViewCount(articleId);
        detail.setViewCount(detail.getViewCount() == null ? 1L : detail.getViewCount() + 1);
        detail.setTags(this.baseMapper.selectTagListByArticleId(articleId));
        if (detail.getTags() == null) {
            detail.setTags(Collections.emptyList());
        }
        return detail;
    }

    @Override
    public List<AppArticleListVo> listPublicHotArticles(Integer limit) {
        return this.baseMapper.selectPublicHotArticleList(normalizeLimit(limit));
    }

    @Override
    public List<AppArticleListVo> listPublicLatestArticles(Integer limit) {
        return this.baseMapper.selectPublicLatestArticleList(normalizeLimit(limit));
    }

    @Override
    public List<AppArticleArchiveVo> listPublicArchives() {
        List<AppArticleArchiveItemVo> items = this.baseMapper.selectPublicArchiveItemList();
        Map<String, List<AppArticleArchiveItemVo>> grouped = items.stream().collect(Collectors.groupingBy(
            AppArticleArchiveItemVo::getYearMonth,
            LinkedHashMap::new,
            Collectors.toList()
        ));

        return grouped.entrySet().stream()
            .map(entry -> {
                AppArticleArchiveVo vo = new AppArticleArchiveVo();
                vo.setYearMonth(entry.getKey());
                vo.setArticles(entry.getValue());
                return vo;
            })
            .toList();
    }

    @Override
    public TableDataInfo<BlogUserArticleListVo> selectCurrentUserArticlePage(BlogUserArticleQueryDto dto) {
        Long userId = getCurrentUserId();
        Page<BlogUserArticleListVo> page = dto.buildPage();
        IPage<BlogUserArticleListVo> result = this.baseMapper.selectCurrentUserArticlePage(page, dto, userId);
        return TableDataInfo.build(result);
    }

    @Override
    public BlogUserArticleDetailVo selectCurrentUserArticleDetail(Long articleId) {
        Long userId = getCurrentUserId();
        BlogUserArticleDetailVo detail = this.baseMapper.selectCurrentUserArticleDetail(articleId, userId);
        if (detail == null) {
            throw new BusinessException("文章不存在或无权访问");
        }
        detail.setTagIds(blogArticleTagMapper.selectTagIdsByArticleId(articleId));
        if (detail.getTagIds() == null) {
            detail.setTagIds(Collections.emptyList());
        }
        return detail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCurrentUserArticle(BlogUserArticleCreateDto dto) {
        Long userId = getCurrentUserId();
        validateArticlePayload(dto.getCategoryId(), dto.getTagIds(), dto.getIsOriginal(), dto.getOriginalUrl(), dto.getStatus());

        BlogArticle article = new BlogArticle();
        article.setUserId(userId);
        fillArticle(article, dto.getTitle(), dto.getSummary(), dto.getCoverImage(), dto.getCategoryId(), dto.getContentMd(),
            dto.getAllowComment(), dto.getIsOriginal(), dto.getOriginalUrl(), dto.getStatus(), dto.getIsTop());
        this.save(article);
        replaceArticleTags(article.getArticleId(), dto.getTagIds());
        return article.getArticleId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCurrentUserArticle(BlogUserArticleUpdateDto dto) {
        BlogArticle article = getCurrentUserArticle(dto.getArticleId());
        validateArticlePayload(dto.getCategoryId(), dto.getTagIds(), dto.getIsOriginal(), dto.getOriginalUrl(), dto.getStatus());
        fillArticle(article, dto.getTitle(), dto.getSummary(), dto.getCoverImage(), dto.getCategoryId(), dto.getContentMd(),
            dto.getAllowComment(), dto.getIsOriginal(), dto.getOriginalUrl(), dto.getStatus(), dto.getIsTop());
        this.updateById(article);
        replaceArticleTags(article.getArticleId(), dto.getTagIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCurrentUserArticles(List<Long> articleIds) {
        if (articleIds == null || articleIds.isEmpty()) {
            throw new BusinessException("请选择要删除的文章");
        }
        Long userId = getCurrentUserId();
        List<BlogArticle> articles = this.list(Wrappers.<BlogArticle>lambdaQuery()
            .in(BlogArticle::getArticleId, articleIds)
            .eq(BlogArticle::getUserId, userId));
        if (articles.size() != articleIds.stream().distinct().count()) {
            throw new BusinessException("存在文章不存在或无权删除");
        }
        this.removeByIds(articleIds);
        articleIds.forEach(blogArticleTagMapper::deleteByArticleId);
    }

    @Override
    public void changeCurrentUserArticleStatus(BlogUserArticlePublishDto dto) {
        if (!List.of("1", "2").contains(dto.getStatus())) {
            throw new BusinessException("发布状态不合法");
        }
        BlogArticle article = getCurrentUserArticle(dto.getArticleId());
        article.setStatus(dto.getStatus());
        if ("1".equals(dto.getStatus()) && article.getPublishTime() == null) {
            article.setPublishTime(LocalDateTime.now());
        }
        this.updateById(article);
    }

    private void fillArticle(BlogArticle article, String title, String summary, String coverImage, Long categoryId,
                             String contentMd, String allowComment, String isOriginal, String originalUrl,
                             String status, String isTop) {
        article.setTitle(title);
        article.setSummary(summary);
        article.setCoverImage(coverImage);
        article.setCategoryId(categoryId);
        article.setContentMd(contentMd);
        article.setAllowComment(defaultFlag(allowComment, "1"));
        article.setIsOriginal(defaultFlag(isOriginal, "1"));
        article.setOriginalUrl(blankToNull(originalUrl));
        article.setStatus(defaultStatus(status));
        article.setIsTop(defaultFlag(isTop, "0"));
        if ("1".equals(article.getStatus()) && article.getPublishTime() == null) {
            article.setPublishTime(LocalDateTime.now());
        }
    }

    private void validateArticlePayload(Long categoryId, List<Long> tagIds, String isOriginal, String originalUrl, String status) {
        blogArticleCategoryService.validateAvailableCategory(categoryId);
        validateTagIds(tagIds);
        validateOriginalInfo(isOriginal, originalUrl);
        validateStatus(status);
    }

    private void validateTagIds(List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return;
        }
        long distinctCount = tagIds.stream().distinct().count();
        if (distinctCount != tagIds.size()) {
            throw new BusinessException("标签不能重复选择");
        }
        long enabledCount = blogTagMapper.countEnabledTagsByIds(tagIds);
        if (enabledCount != distinctCount) {
            throw new BusinessException("存在无效或已停用的标签");
        }
    }

    private void validateOriginalInfo(String isOriginal, String originalUrl) {
        if ("0".equals(defaultFlag(isOriginal, "1")) && blankToNull(originalUrl) == null) {
            throw new BusinessException("转载文章必须填写原文地址");
        }
    }

    private void validateStatus(String status) {
        String safeStatus = defaultStatus(status);
        if (!List.of("0", "1", "2").contains(safeStatus)) {
            throw new BusinessException("文章状态不合法");
        }
    }

    private void replaceArticleTags(Long articleId, List<Long> tagIds) {
        blogArticleTagMapper.deleteByArticleId(articleId);
        if (tagIds == null || tagIds.isEmpty()) {
            return;
        }
        blogArticleTagMapper.insertBatch(articleId, tagIds);
    }

    private BlogArticle getCurrentUserArticle(Long articleId) {
        Long userId = getCurrentUserId();
        BlogArticle article = this.getOne(Wrappers.<BlogArticle>lambdaQuery()
            .eq(BlogArticle::getArticleId, articleId)
            .eq(BlogArticle::getUserId, userId)
            .last("LIMIT 1"));
        if (article == null) {
            throw new BusinessException("文章不存在或无权操作");
        }
        return article;
    }

    private Long getCurrentUserId() {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            throw new BusinessException("当前未登录或登录状态已失效");
        }
        return userId;
    }

    private String defaultFlag(String value, String defaultValue) {
        return value == null || value.isBlank() ? defaultValue : value;
    }

    private String defaultStatus(String status) {
        return status == null || status.isBlank() ? "0" : status;
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }

    private int normalizeLimit(Integer limit) {
        if (limit == null || limit < 1) {
            return DEFAULT_LIST_LIMIT;
        }
        return Math.min(limit, MAX_LIST_LIMIT);
    }
}