package com.xy.blog.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

/**
 * 用户角色分配参数。
 */
@Data
@Schema(name = "BlogUserAuthRoleDto", description = "用户角色分配参数")
public class BlogUserAuthRoleDto {

    @Schema(description = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "角色ID列表")
    @NotEmpty(message = "角色不能为空")
    private List<Long> roleIds;
}
