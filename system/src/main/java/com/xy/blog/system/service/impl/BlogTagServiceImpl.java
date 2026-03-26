package com.xy.blog.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.blog.framework.exception.BusinessException;
import com.xy.blog.framework.web.page.TableDataInfo;
import com.xy.blog.system.dto.BlogTagChangeStatusDto;
import com.xy.blog.system.dto.BlogTagCreateDto;
import com.xy.blog.system.dto.BlogTagQueryDto;
import com.xy.blog.system.dto.BlogTagUpdateDto;
import com.xy.blog.system.entity.po.BlogTag;
import com.xy.blog.system.mapper.BlogTagMapper;
import com.xy.blog.system.service.IBlogTagService;
import com.xy.blog.system.vo.AppTagListVo;
import com.xy.blog.system.vo.BlogTagDetailVo;
import com.xy.blog.system.vo.BlogTagListVo;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 博客标签服务实现。
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements IBlogTagService {

    @Override
    public List<AppTagListVo> listPublicTags() {
        return this.baseMapper.selectPublicTagList();
    }

    @Override
    public TableDataInfo<BlogTagListVo> selectAdminTagPage(BlogTagQueryDto dto) {
        Page<BlogTagListVo> page = dto.buildPage();
        IPage<BlogTagListVo> result = this.baseMapper.selectAdminTagPage(page, dto);
        return TableDataInfo.build(result);
    }

    @Override
    public BlogTagDetailVo selectAdminTagDetail(Long tagId) {
        BlogTag tag = getExistingTag(tagId);
        BlogTagDetailVo vo = new BlogTagDetailVo();
        vo.setTagId(tag.getTagId());
        vo.setTagName(tag.getTagName());
        vo.setSortNum(tag.getSortNum());
        vo.setStatus(tag.getStatus());
        vo.setRemark(tag.getRemark());
        vo.setArticleCount(this.baseMapper.selectArticleReferenceCount(tagId));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTag(BlogTagCreateDto dto) {
        validateTagNameUnique(dto.getTagName(), null);
        BlogTag tag = new BlogTag();
        tag.setTagName(dto.getTagName());
        tag.setSortNum(defaultSort(dto.getSortNum()));
        tag.setStatus(defaultStatus(dto.getStatus()));
        tag.setRemark(dto.getRemark());
        this.save(tag);
        return tag.getTagId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTag(BlogTagUpdateDto dto) {
        BlogTag tag = getExistingTag(dto.getTagId());
        validateTagNameUnique(dto.getTagName(), tag.getTagId());
        tag.setTagName(dto.getTagName());
        tag.setSortNum(defaultSort(dto.getSortNum()));
        tag.setStatus(defaultStatus(dto.getStatus()));
        tag.setRemark(dto.getRemark());
        this.updateById(tag);
    }

    @Override
    public void changeTagStatus(BlogTagChangeStatusDto dto) {
        BlogTag tag = getExistingTag(dto.getTagId());
        if (!List.of("0", "1").contains(dto.getStatus())) {
            throw new BusinessException("标签状态不合法");
        }
        tag.setStatus(dto.getStatus());
        this.updateById(tag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTags(List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            throw new BusinessException("请选择要删除的标签");
        }
        List<BlogTag> tags = this.listByIds(tagIds);
        if (tags.size() != tagIds.stream().distinct().count()) {
            throw new BusinessException("存在标签不存在");
        }
        for (Long tagId : tagIds) {
            if (this.baseMapper.selectArticleReferenceCount(tagId) > 0) {
                throw new BusinessException("标签已被文章使用，不能删除");
            }
        }
        this.removeByIds(tagIds);
    }

    private BlogTag getExistingTag(Long tagId) {
        BlogTag tag = this.getById(tagId);
        if (tag == null) {
            throw new BusinessException("标签不存在");
        }
        return tag;
    }

    private void validateTagNameUnique(String tagName, Long excludeId) {
        BlogTag existing = this.getOne(Wrappers.<BlogTag>lambdaQuery()
            .eq(BlogTag::getTagName, tagName)
            .ne(excludeId != null, BlogTag::getTagId, excludeId)
            .last("LIMIT 1"));
        if (existing != null) {
            throw new BusinessException("标签名称已存在");
        }
    }

    private Integer defaultSort(Integer sortNum) {
        return sortNum == null ? 0 : sortNum;
    }

    private String defaultStatus(String status) {
        String safeStatus = (status == null || status.isBlank()) ? "1" : status;
        if (!List.of("0", "1").contains(safeStatus)) {
            throw new BusinessException("标签状态不合法");
        }
        return safeStatus;
    }
}