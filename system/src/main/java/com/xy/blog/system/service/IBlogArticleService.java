package com.xy.blog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.blog.framework.web.page.TableDataInfo;
import com.xy.blog.system.dto.BlogArticleAppQueryDto;
import com.xy.blog.system.dto.BlogArticleChangeStatusDto;
import com.xy.blog.system.dto.BlogArticleChangeTopDto;
import com.xy.blog.system.dto.BlogArticleCreateDto;
import com.xy.blog.system.dto.BlogArticleQueryDto;
import com.xy.blog.system.dto.BlogArticleUpdateDto;
import com.xy.blog.system.dto.BlogUserArticleCreateDto;
import com.xy.blog.system.dto.BlogUserArticlePublishDto;
import com.xy.blog.system.dto.BlogUserArticleQueryDto;
import com.xy.blog.system.dto.BlogUserArticleUpdateDto;
import com.xy.blog.system.entity.po.BlogArticle;
import com.xy.blog.system.vo.AppArticleArchiveVo;
import com.xy.blog.system.vo.AppArticleDetailVo;
import com.xy.blog.system.vo.AppArticleListVo;
import com.xy.blog.system.vo.BlogArticleDetailVo;
import com.xy.blog.system.vo.BlogArticleListVo;
import com.xy.blog.system.vo.BlogUserArticleDetailVo;
import com.xy.blog.system.vo.BlogUserArticleListVo;
import java.util.List;

/**
 * 博客文章服务接口。
 */
public interface IBlogArticleService extends IService<BlogArticle> {

    TableDataInfo<BlogArticleListVo> selectAdminArticlePage(BlogArticleQueryDto dto);

    BlogArticleDetailVo selectAdminArticleDetail(Long articleId);

    Long createAdminArticle(BlogArticleCreateDto dto);

    void updateAdminArticle(BlogArticleUpdateDto dto);

    void changeAdminArticleStatus(BlogArticleChangeStatusDto dto);

    void changeAdminArticleTop(BlogArticleChangeTopDto dto);

    void deleteAdminArticles(List<Long> articleIds);

    TableDataInfo<AppArticleListVo> selectPublicArticlePage(BlogArticleAppQueryDto dto);

    AppArticleDetailVo selectPublicArticleDetail(Long articleId);

    List<AppArticleListVo> listPublicHotArticles(Integer limit);

    List<AppArticleListVo> listPublicLatestArticles(Integer limit);

    List<AppArticleArchiveVo> listPublicArchives();

    TableDataInfo<BlogUserArticleListVo> selectCurrentUserArticlePage(BlogUserArticleQueryDto dto);

    BlogUserArticleDetailVo selectCurrentUserArticleDetail(Long articleId);

    Long createCurrentUserArticle(BlogUserArticleCreateDto dto);

    void updateCurrentUserArticle(BlogUserArticleUpdateDto dto);

    void deleteCurrentUserArticles(List<Long> articleIds);

    void changeCurrentUserArticleStatus(BlogUserArticlePublishDto dto);
}