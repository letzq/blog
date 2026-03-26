package com.xy.blog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.blog.system.entity.po.BlogTag;
import com.xy.blog.system.vo.AppTagListVo;
import java.util.List;

/**
 * 博客标签服务接口。
 */
public interface IBlogTagService extends IService<BlogTag> {

    /**
     * 查询前台启用标签列表。
     */
    List<AppTagListVo> listPublicTags();
}