package com.xy.blog.framework.web.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class PageQuery {

    private static final long DEFAULT_PAGE_NUM = 1L;
    private static final long DEFAULT_PAGE_SIZE = 10L;
    private static final long MAX_PAGE_SIZE = 100L;

    private Long pageNum = DEFAULT_PAGE_NUM;

    private Long pageSize = DEFAULT_PAGE_SIZE;

    public <T> Page<T> buildPage() {
        long current = pageNum == null || pageNum < 1 ? DEFAULT_PAGE_NUM : pageNum;
        long size = pageSize == null || pageSize < 1 ? DEFAULT_PAGE_SIZE : Math.min(pageSize, MAX_PAGE_SIZE);
        return new Page<>(current, size);
    }
}
