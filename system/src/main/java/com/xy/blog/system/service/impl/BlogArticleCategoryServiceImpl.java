package com.xy.blog.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.blog.framework.exception.BusinessException;
import com.xy.blog.system.entity.po.BlogArticleCategory;
import com.xy.blog.system.mapper.BlogArticleCategoryMapper;
import com.xy.blog.system.service.IBlogArticleCategoryService;
import com.xy.blog.system.vo.AppCategoryListVo;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 博客文章分类服务实现。
 */
@Service
public class BlogArticleCategoryServiceImpl extends ServiceImpl<BlogArticleCategoryMapper, BlogArticleCategory>
    implements IBlogArticleCategoryService {

    @Override
    public List<AppCategoryListVo> listPublicCategories() {
        return this.baseMapper.selectPublicCategoryList();
    }

    @Override
    public void validateAvailableCategory(Long categoryId) {
        BlogArticleCategory category = this.getById(categoryId);
        if (category == null || !"1".equals(category.getStatus())) {
            throw new BusinessException("文章分类不存在或不可用");
        }
    }
}