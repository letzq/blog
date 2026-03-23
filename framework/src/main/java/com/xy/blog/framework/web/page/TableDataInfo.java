package com.xy.blog.framework.web.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import lombok.Data;

@Data
public class TableDataInfo<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long total;

    private List<T> rows;

    public static <T> TableDataInfo<T> build(IPage<T> page) {
        TableDataInfo<T> data = new TableDataInfo<>();
        data.setTotal(page.getTotal());
        data.setRows(page.getRecords());
        return data;
    }

    public static <T> TableDataInfo<T> empty() {
        TableDataInfo<T> data = new TableDataInfo<>();
        data.setTotal(0L);
        data.setRows(Collections.emptyList());
        return data;
    }
}
