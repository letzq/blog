package com.xy.blog.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xy.blog.framework.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long userId;

    private String userName;

    private String nickName;

    private String email;

    private String phonenumber;

    private String password;

    private String status;

    private Long roleId;
}
