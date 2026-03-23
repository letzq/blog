package com.xy.blog.system.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 博客用户角色关联实体。
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("blog_user_role")
@Schema(name = "BlogUserRole", description = "博客用户角色关联表")
public class BlogUserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "角色ID")
    @TableField("role_id")
    private Long roleId;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;
}
