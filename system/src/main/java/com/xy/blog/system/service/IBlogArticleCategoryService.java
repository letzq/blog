package com.xy.blog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.blog.system.entity.po.BlogArticleCategory;
import com.xy.blog.system.vo.AppCategoryListVo;
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
}