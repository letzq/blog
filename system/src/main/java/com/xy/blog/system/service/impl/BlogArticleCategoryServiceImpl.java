package com.xy.blog.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.blog.framework.exception.BusinessException;
import com.xy.blog.framework.web.page.TableDataInfo;
import com.xy.blog.system.dto.BlogArticleCategoryChangeStatusDto;
import com.xy.blog.system.dto.BlogArticleCategoryCreateDto;
import com.xy.blog.system.dto.BlogArticleCategoryQueryDto;
import com.xy.blog.system.dto.BlogArticleCategoryUpdateDto;
import com.xy.blog.system.entity.po.BlogArticle;
import com.xy.blog.system.entity.po.BlogArticleCategory;
import com.xy.blog.system.mapper.BlogArticleCategoryMapper;
import com.xy.blog.system.service.IBlogArticleCategoryService;
import com.xy.blog.system.vo.AppCategoryListVo;
import com.xy.blog.system.vo.BlogArticleCategoryDetailVo;
import com.xy.blog.system.vo.BlogArticleCategoryListVo;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public TableDataInfo<BlogArticleCategoryListVo> selectAdminCategoryPage(BlogArticleCategoryQueryDto dto) {
        Page<BlogArticleCategoryListVo> page = dto.buildPage();
        IPage<BlogArticleCategoryListVo> result = this.baseMapper.selectAdminCategoryPage(page, dto);
        return TableDataInfo.build(result);
    }

    @Override
    public BlogArticleCategoryDetailVo selectAdminCategoryDetail(Long categoryId) {
        BlogArticleCategory category = getExistingCategory(categoryId);
        BlogArticleCategoryDetailVo vo = new BlogArticleCategoryDetailVo();
        vo.setCategoryId(category.getCategoryId());
        vo.setCategoryName(category.getCategoryName());
        vo.setParentId(category.getParentId());
        vo.setAncestors(category.getAncestors());
        vo.setSortNum(category.getSortNum());
        vo.setStatus(category.getStatus());
        vo.setRemark(category.getRemark());
        vo.setArticleCount(countArticlesByCategoryId(categoryId));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCategory(BlogArticleCategoryCreateDto dto) {
        validateCategoryNameUnique(dto.getCategoryName(), null);
        validateParent(dto.getParentId(), null);

        BlogArticleCategory category = new BlogArticleCategory();
        category.setCategoryName(dto.getCategoryName());
        category.setParentId(dto.getParentId());
        category.setAncestors(buildAncestors(dto.getParentId()));
        category.setSortNum(defaultSort(dto.getSortNum()));
        category.setStatus(defaultStatus(dto.getStatus()));
        category.setRemark(dto.getRemark());
        this.save(category);
        return category.getCategoryId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(BlogArticleCategoryUpdateDto dto) {
        BlogArticleCategory category = getExistingCategory(dto.getCategoryId());
        validateCategoryNameUnique(dto.getCategoryName(), category.getCategoryId());
        validateParent(dto.getParentId(), category.getCategoryId());

        category.setCategoryName(dto.getCategoryName());
        category.setParentId(dto.getParentId());
        category.setAncestors(buildAncestors(dto.getParentId()));
        category.setSortNum(defaultSort(dto.getSortNum()));
        category.setStatus(defaultStatus(dto.getStatus()));
        category.setRemark(dto.getRemark());
        this.updateById(category);
    }

    @Override
    public void changeCategoryStatus(BlogArticleCategoryChangeStatusDto dto) {
        BlogArticleCategory category = getExistingCategory(dto.getCategoryId());
        if (!List.of("0", "1").contains(dto.getStatus())) {
            throw new BusinessException("分类状态不合法");
        }
        category.setStatus(dto.getStatus());
        this.updateById(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategories(List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new BusinessException("请选择要删除的分类");
        }
        List<BlogArticleCategory> categories = this.listByIds(categoryIds);
        if (categories.size() != categoryIds.stream().distinct().count()) {
            throw new BusinessException("存在分类不存在");
        }
        for (Long categoryId : categoryIds) {
            if (countChildrenByParentId(categoryId) > 0) {
                throw new BusinessException("分类下存在子分类，不能删除");
            }
            if (countArticlesByCategoryId(categoryId) > 0) {
                throw new BusinessException("分类下存在文章，不能删除");
            }
        }
        this.removeByIds(categoryIds);
    }

    private BlogArticleCategory getExistingCategory(Long categoryId) {
        BlogArticleCategory category = this.getById(categoryId);
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        return category;
    }

    private void validateCategoryNameUnique(String categoryName, Long excludeId) {
        BlogArticleCategory existing = this.getOne(Wrappers.<BlogArticleCategory>lambdaQuery()
            .eq(BlogArticleCategory::getCategoryName, categoryName)
            .ne(excludeId != null, BlogArticleCategory::getCategoryId, excludeId)
            .last("LIMIT 1"));
        if (existing != null) {
            throw new BusinessException("分类名称已存在");
        }
    }

    private void validateParent(Long parentId, Long currentId) {
        if (parentId == null || parentId < 0) {
            throw new BusinessException("父分类不合法");
        }
        if (currentId != null && parentId.equals(currentId)) {
            throw new BusinessException("父分类不能选择自己");
        }
        if (parentId == 0L) {
            return;
        }
        BlogArticleCategory parent = this.getById(parentId);
        if (parent == null) {
            throw new BusinessException("父分类不存在");
        }
    }

    private String buildAncestors(Long parentId) {
        if (parentId == null || parentId == 0L) {
            return "";
        }
        BlogArticleCategory parent = this.getById(parentId);
        if (parent == null) {
            throw new BusinessException("父分类不存在");
        }
        String parentAncestors = parent.getAncestors();
        return parentAncestors == null || parentAncestors.isBlank()
            ? String.valueOf(parentId)
            : parentAncestors + "," + parentId;
    }

    private Integer defaultSort(Integer sortNum) {
        return sortNum == null ? 0 : sortNum;
    }

    private String defaultStatus(String status) {
        String safeStatus = (status == null || status.isBlank()) ? "1" : status;
        if (!List.of("0", "1").contains(safeStatus)) {
            throw new BusinessException("分类状态不合法");
        }
        return safeStatus;
    }

    private long countChildrenByParentId(Long parentId) {
        return this.count(Wrappers.<BlogArticleCategory>lambdaQuery().eq(BlogArticleCategory::getParentId, parentId));
    }

    private long countArticlesByCategoryId(Long categoryId) {
        return this.getBaseMapper().selectCountArticlesByCategoryId(categoryId);
    }
}