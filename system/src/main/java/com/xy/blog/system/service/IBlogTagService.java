package com.xy.blog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.blog.framework.web.page.TableDataInfo;
import com.xy.blog.system.dto.BlogTagChangeStatusDto;
import com.xy.blog.system.dto.BlogTagCreateDto;
import com.xy.blog.system.dto.BlogTagQueryDto;
import com.xy.blog.system.dto.BlogTagUpdateDto;
import com.xy.blog.system.entity.po.BlogTag;
import com.xy.blog.system.vo.AppTagListVo;
import com.xy.blog.system.vo.BlogTagDetailVo;
import com.xy.blog.system.vo.BlogTagListVo;
import java.util.List;

/**
 * 博客标签服务接口。
 */
public interface IBlogTagService extends IService<BlogTag> {

    /**
     * 查询前台启用标签列表。
     */
    List<AppTagListVo> listPublicTags();

    /**
     * 分页查询后台标签列表。
     */
    TableDataInfo<BlogTagListVo> selectAdminTagPage(BlogTagQueryDto dto);

    /**
     * 查询后台标签详情。
     */
    BlogTagDetailVo selectAdminTagDetail(Long tagId);

    /**
     * 新增后台标签。
     */
    Long createTag(BlogTagCreateDto dto);

    /**
     * 修改后台标签。
     */
    void updateTag(BlogTagUpdateDto dto);

    /**
     * 修改后台标签状态。
     */
    void changeTagStatus(BlogTagChangeStatusDto dto);

    /**
     * 删除后台标签。
     */
    void deleteTags(List<Long> tagIds);
}