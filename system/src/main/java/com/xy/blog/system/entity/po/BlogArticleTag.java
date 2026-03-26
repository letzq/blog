package com.xy.blog.system.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 博客文章标签关联实体。
 */
@Data
@TableName("blog_article_tag")
@Schema(name = "BlogArticleTag", description = "博客文章标签关联实体")
public class BlogArticleTag {

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "文章ID")
    @TableField("article_id")
    private Long articleId;

    @Schema(description = "标签ID")
    @TableField("tag_id")
    private Long tagId;
}
