package com.xy.blog.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.blog.system.entity.po.BlogTag;
import com.xy.blog.system.mapper.BlogTagMapper;
import com.xy.blog.system.service.IBlogTagService;
import com.xy.blog.system.vo.AppTagListVo;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 博客标签服务实现。
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements IBlogTagService {

    @Override
    public List<AppTagListVo> listPublicTags() {
        return this.baseMapper.selectPublicTagList();
    }
}