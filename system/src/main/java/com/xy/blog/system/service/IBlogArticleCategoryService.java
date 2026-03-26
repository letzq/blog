package com.xy.blog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.blog.framework.web.page.TableDataInfo;
import com.xy.blog.system.dto.BlogArticleCategoryChangeStatusDto;
import com.xy.blog.system.dto.BlogArticleCategoryCreateDto;
import com.xy.blog.system.dto.BlogArticleCategoryQueryDto;
import com.xy.blog.system.dto.BlogArticleCategoryUpdateDto;
import com.xy.blog.system.entity.po.BlogArticleCategory;
import com.xy.blog.system.vo.AppCategoryListVo;
import com.xy.blog.system.vo.BlogArticleCategoryDetailVo;
import com.xy.blog.system.vo.BlogArticleCategoryListVo;
import java.util.List;

/**
 * 博客文章分类服务接口。
 */
public interface IBlogArticleCategoryService extends IService<BlogArticleCategory> {

    /**
     * 查询前台启用分类列表。
     */
    List<AppCategoryListVo> listPublicCategories();

    /**
     * 校验分类是否存在且可用。
     */
    void validateAvailableCategory(Long categoryId);

    /**
     * 分页查询后台分类列表。
     */
    TableDataInfo<BlogArticleCategoryListVo> selectAdminCategoryPage(BlogArticleCategoryQueryDto dto);

    /**
     * 查询后台分类详情。
     */
    BlogArticleCategoryDetailVo selectAdminCategoryDetail(Long categoryId);

    /**
     * 新增后台分类。
     */
    Long createCategory(BlogArticleCategoryCreateDto dto);

    /**
     * 修改后台分类。
     */
    void updateCategory(BlogArticleCategoryUpdateDto dto);

    /**
     * 修改后台分类状态。
     */
    void changeCategoryStatus(BlogArticleCategoryChangeStatusDto dto);

    /**
     * 删除后台分类。
     */
    void deleteCategories(List<Long> categoryIds);
}