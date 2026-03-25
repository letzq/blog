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
 * 博客角色实体。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_role")
@Schema(name = "BlogRole", description = "博客角色表")
public class BlogRole extends BaseEntity {

    @Schema(description = "角色ID")
    @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;

    @Schema(description = "角色名称")
    @TableField("role_name")
    private String roleName;

    @Schema(description = "角色权限字符")
    @TableField("role_key")
    private String roleKey;

    @Schema(description = "角色排序")
    @TableField("role_sort")
    private Integer roleSort;

    @Schema(description = "状态")
    @TableField("status")
    private String status;

    @Schema(description = "删除标志")
    @TableField("del_flag")
    private String delFlag;
}
