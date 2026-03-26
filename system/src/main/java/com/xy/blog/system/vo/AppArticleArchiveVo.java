package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

/**
 * 前台文章归档返回对象。
 */
@Data
@Schema(name = "AppArticleArchiveVo", description = "前台文章归档返回对象")
public class AppArticleArchiveVo {

    @Schema(description = "归档年月")
    private String yearMonth;

    @Schema(description = "该月份下的文章列表")
    private List<AppArticleArchiveItemVo> articles;
}