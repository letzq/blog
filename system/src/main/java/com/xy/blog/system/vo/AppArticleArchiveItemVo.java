package com.xy.blog.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 前台文章归档明细返回对象。
 */
@Data
@Schema(name = "AppArticleArchiveItemVo", description = "前台文章归档明细返回对象")
public class AppArticleArchiveItemVo {

    @Schema(description = "归档年月")
    private String yearMonth;

    @Schema(description = "文章ID")
    private Long articleId;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "发布时间")
    private LocalDateTime publishTime;
}