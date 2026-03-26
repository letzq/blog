package com.xy.blog.system.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xy.blog.framework.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 博客标签实体。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_tag")
@Schema(name = "BlogTag", description = "博客标签实体")
public class BlogTag extends BaseEntity {

    @Schema(description = "标签ID")
    @TableId(value = "tag_id", type = IdType.AUTO)
    private Long tagId;

    @Schema(description = "标签名称")
    @TableField("tag_name")
    private String tagName;

    @Schema(description = "排序值")
    @TableField("sort_num")
    private Integer sortNum;

    @Schema(description = "状态，0停用1启用")
    @TableField("status")
    private String status;
}