# 用户管理模块开发清单

日期：2026-03-24

## 1. 目标

为博客后台提供第一版用户管理能力，面向后台管理员使用，支持：

- 用户分页查询
- 用户详情查询
- 新增用户
- 修改用户
- 删除用户
- 修改用户状态
- 重置用户密码
- 查询用户已分配角色
- 更新用户角色

当前阶段统一按角色控制，用户管理模块仅允许 `admin` 访问。

---

## 2. 接口设计

### 2.1 用户分页列表

- 方法：`GET`
- 路径：`/system/user/list`
- 权限：`admin`
- 说明：分页查询后台用户列表

查询参数：

- `pageNum`：页码
- `pageSize`：每页条数
- `userName`：登录账号
- `nickName`：用户昵称
- `email`：邮箱
- `phonenumber`：手机号
- `status`：状态
- `beginTime`：创建开始时间
- `endTime`：创建结束时间

返回字段建议：

- `userId`
- `userName`
- `nickName`
- `email`
- `phonenumber`
- `avatar`
- `status`
- `loginIp`
- `loginDate`
- `createTime`
- `roles`

返回示例：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "total": 2,
    "rows": [
      {
        "userId": 1,
        "userName": "admin",
        "nickName": "超级管理员",
        "email": "admin@example.com",
        "phonenumber": "13800000000",
        "avatar": null,
        "status": "1",
        "loginIp": "127.0.0.1",
        "loginDate": "2026-03-24T22:30:00",
        "createTime": "2026-03-23T10:00:00",
        "roles": [
          "admin"
        ]
      }
    ]
  }
}
```

### 2.2 用户详情

- 方法：`GET`
- 路径：`/system/user/{userId}`
- 权限：`admin`
- 说明：查询单个用户详情

返回字段建议：

- 用户基础信息
- `roleIds`
- `roles`

返回示例：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "userId": 1,
    "userName": "admin",
    "nickName": "超级管理员",
    "email": "admin@example.com",
    "phonenumber": "13800000000",
    "avatar": null,
    "sex": "0",
    "status": "1",
    "roleIds": [1],
    "roles": ["admin"]
  }
}
```

### 2.3 新增用户

- 方法：`POST`
- 路径：`/system/user`
- 权限：`admin`
- 说明：管理员创建后台用户并分配角色

请求字段建议：

- `userName`
- `nickName`
- `email`
- `phonenumber`
- `password`
- `status`
- `roleIds`

返回示例：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

### 2.4 修改用户

- 方法：`PUT`
- 路径：`/system/user`
- 权限：`admin`
- 说明：管理员修改用户信息

请求字段建议：

- `userId`
- `nickName`
- `email`
- `phonenumber`
- `avatar`
- `sex`
- `status`
- `roleIds`

返回示例：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

### 2.5 删除用户

- 方法：`DELETE`
- 路径：`/system/user/{userIds}`
- 权限：`admin`
- 说明：删除单个或多个用户

说明：

- 建议第一版使用逻辑删除
- 删除用户时同步清理 `blog_user_role`
- 禁止删除当前登录用户自己

返回示例：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

### 2.6 修改用户状态

- 方法：`PUT`
- 路径：`/system/user/changeStatus`
- 权限：`admin`
- 说明：单独修改用户状态，便于前端列表开关控制

请求字段建议：

- `userId`
- `status`

返回示例：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

### 2.7 重置用户密码

- 方法：`PUT`
- 路径：`/system/user/resetPwd`
- 权限：`admin`
- 说明：管理员重置指定用户密码

请求字段建议：

- `userId`
- `password`

说明：

- 密码必须使用 BCrypt 加密后入库
- 重置成功后建议清理该用户现有登录 token

返回示例：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

### 2.8 查询用户已分配角色

- 方法：`GET`
- 路径：`/system/user/authRole/{userId}`
- 权限：`admin`
- 说明：查询用户可分配角色和已分配角色

返回字段建议：

- 用户基础信息
- 可分配角色列表
- 已选 `roleIds`

返回示例：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "userId": 2,
    "userName": "editor01",
    "nickName": "编辑A",
    "roleIds": [2],
    "roleOptions": [
      {
        "roleId": 1,
        "roleName": "超级管理员",
        "roleKey": "admin"
      },
      {
        "roleId": 2,
        "roleName": "内容编辑",
        "roleKey": "editor"
      },
      {
        "roleId": 3,
        "roleName": "普通作者",
        "roleKey": "author"
      }
    ]
  }
}
```

### 2.9 更新用户角色

- 方法：`PUT`
- 路径：`/system/user/authRole`
- 权限：`admin`
- 说明：为指定用户分配角色

请求字段建议：

- `userId`
- `roleIds`

返回示例：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

## 3. DTO 设计

建议新增以下 DTO：

- `BlogUserQueryDto`
- `BlogUserCreateDto`
- `BlogUserUpdateDto`
- `BlogUserChangeStatusDto`
- `BlogUserResetPasswordDto`
- `BlogUserAuthRoleDto`

说明：

- 查询条件、创建、编辑、状态修改、密码重置、角色分配分开建模
- 不要用一个 DTO 承担全部场景
- 参数校验统一写在 DTO 上，不写在实体类上

校验建议：

- `userName`：`@NotBlank`、`@Size(max = 30)`
- `nickName`：`@NotBlank`、`@Size(max = 30)`
- `email`：`@Email`
- `phonenumber`：`@Pattern`
- `password`：`@NotBlank`、`@Size(min = 6, max = 20)`
- `status`：`@NotBlank`
- `roleIds`：`@NotEmpty`

---

## 4. VO 设计

建议新增以下 VO：

- `BlogUserListVo`
- `BlogUserDetailVo`
- `BlogRoleOptionVo`
- `BlogUserAuthRoleVo`

说明：

- 列表 VO 和详情 VO 分开
- 角色选择框数据单独用 `BlogRoleOptionVo`
- 用户角色分配页用独立 VO，避免复用混乱

---

## 5. Service 设计

建议在用户服务中补这些方法：

- `selectUserPage(BlogUserQueryDto dto)`
- `selectUserById(Long userId)`
- `createUser(BlogUserCreateDto dto)`
- `updateUser(BlogUserUpdateDto dto)`
- `deleteUsers(List<Long> userIds)`
- `changeUserStatus(BlogUserChangeStatusDto dto)`
- `resetPassword(BlogUserResetPasswordDto dto)`
- `selectUserAuthRoleInfo(Long userId)`
- `updateUserRoles(BlogUserAuthRoleDto dto)`

角色关联相关逻辑建议：

- 查询用户角色：走 `blog_user_role -> blog_role`
- 更新角色时：先删旧绑定，再批量插入新绑定
- 删除用户时：同步清理用户角色绑定

---

## 6. Controller 设计

建议新增控制器：

- 包路径：`admin/src/main/java/com/xy/blog/controller/system`
- 类名：`SysUserController`

接口建议统一使用：

- `@Tag(name = "后台用户管理")`
- `@Operation(summary = "...")`
- `@PreAuthorize("hasRole('admin')")`
- `@Log(title = "用户管理", businessType = ...)`

说明：

- 当前阶段用户管理模块全部只开放给 `admin`
- 后面如果要放开某些查询给 `editor`，再单独放行

---

## 7. OpenAPI 注释规范

Controller 要求：

- 类上必须加 `@Tag`
- 方法上必须加 `@Operation`

DTO / VO 要求：

- 类上加 `@Schema`
- 关键字段加 `@Schema(description = "...")`

注释要求：

- 全部使用中文
- 不允许乱码
- 说明要面向接口使用者，不写数据库术语堆砌

示例：

```java
@Tag(name = "后台用户管理")
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Operation(summary = "分页查询用户列表")
    @GetMapping("/list")
    @PreAuthorize("hasRole('admin')")
    public Result<TableDataInfo<BlogUserListVo>> list(BlogUserQueryDto dto) {
        ...
    }
}
```

---

## 8. 操作日志建议

建议这些接口全部记录操作日志：

- 新增用户
- 修改用户
- 删除用户
- 修改状态
- 重置密码
- 分配角色

日志建议：

- `title = "用户管理"`
- `businessType` 根据动作区分

注意：

- 密码字段要脱敏
- 角色分配请求可记录 `userId` 和 `roleIds`

---

## 9. 权限控制建议

当前阶段统一按角色控制：

- 用户管理模块全部使用 `@PreAuthorize("hasRole('admin')")`

原因：

- 当前数据库只有角色表和用户角色绑定表
- 还没有权限字符表
- 后台第一版先用角色控制更稳

后续升级方向：

- 新增权限字符表
- 改成 `hasAuthority(...)`
- 再把按钮权限和接口权限细化

---

## 10. 开发顺序

建议按下面顺序落地：

1. 用户分页列表
2. 用户详情
3. 新增用户
4. 修改用户
5. 查询用户已分配角色
6. 更新用户角色
7. 修改用户状态
8. 重置用户密码
9. 删除用户

---

## 11. 需要特别注意的规则

- 不能删除当前登录用户自己
- 不能重置或禁用最后一个超级管理员，后续可以加这条保护
- 密码必须 BCrypt 加密
- 返回对象不能暴露密码字段
- 删除用户后要清理角色关联
- 修改角色后建议清理该用户登录 token，避免旧登录态权限不一致

---

## 12. 本阶段交付目标

第一阶段交付完成后，至少应具备：

- 管理员查看用户列表
- 管理员新增用户并分配角色
- 管理员修改用户资料和角色
- 管理员重置密码
- 管理员禁用用户
- 管理员删除用户

并满足：

- 接口有中文 OpenAPI 注释
- 返回结构统一
- 方法级角色鉴权生效
- 关键接口有操作日志
- 代码改动后完成编译校验
