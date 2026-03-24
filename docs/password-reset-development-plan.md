# 忘记密码开发方案

## 一、目标

为当前博客后台项目增加“忘记密码”能力，支持用户通过邮箱验证码重置密码。

本方案采用简化版两接口设计，优先保证实现成本低、结构清晰、可快速接入当前认证体系。

## 二、整体流程

### 1. 发送找回密码验证码

用户输入邮箱后，调用发送验证码接口：

- 校验邮箱格式
- 判断邮箱是否已注册
- 判断发送频率是否过高
- 生成邮箱验证码
- 写入 Redis
- 发送邮箱验证码

### 2. 通过验证码重置密码

用户输入邮箱、验证码、新密码和确认密码后，调用重置密码接口：

- 校验邮箱格式
- 校验验证码是否存在且正确
- 校验两次密码是否一致
- 校验新密码长度
- 查询用户
- BCrypt 加密新密码
- 更新数据库密码
- 删除 Redis 验证码
- 可选清理旧登录 token

## 三、接口设计

### 1. 发送找回密码验证码

接口：

```http
POST /auth/password/code
```

请求参数：

- `email`

请求示例：

```json
{
  "email": "user@example.com"
}
```

返回示例：

```json
{
  "code": 200,
  "msg": "验证码发送成功",
  "data": null
}
```

### 2. 重置密码

接口：

```http
POST /auth/password/reset
```

请求参数：

- `email`
- `code`
- `newPassword`
- `confirmPassword`

请求示例：

```json
{
  "email": "user@example.com",
  "code": "123456",
  "newPassword": "newPassword123",
  "confirmPassword": "newPassword123"
}
```

返回示例：

```json
{
  "code": 200,
  "msg": "密码重置成功",
  "data": null
}
```

## 四、Redis 设计

### 1. 找回密码验证码

Key：

```text
blog:password:code:{email}
```

说明：

- 用于保存找回密码验证码
- 过期时间建议 5 分钟

### 2. 找回密码发送频控

Key：

```text
blog:password:send:{email}
```

说明：

- 用于限制验证码发送频率
- 过期时间建议 60 秒

## 五、DTO 设计

### 1. PasswordCodeSendDto

字段：

- `email`

校验建议：

- `@NotBlank(message = "邮箱不能为空")`
- `@Email(message = "邮箱格式不正确")`

### 2. PasswordResetDto

字段：

- `email`
- `code`
- `newPassword`
- `confirmPassword`

校验建议：

- `email`：`@NotBlank + @Email`
- `code`：`@NotBlank`
- `newPassword`：`@NotBlank + @Size`
- `confirmPassword`：`@NotBlank`

## 六、Service 设计

继续复用当前认证服务，不新增独立模块。

在以下文件中扩展方法：

- `IBlogAuthService`
- `BlogAuthServiceImpl`

建议新增方法：

```java
void sendResetPasswordCode(PasswordCodeSendDto dto);

void resetPassword(PasswordResetDto dto);
```

## 七、用户服务设计

如果当前用户服务中没有直接更新密码的方法，需要补充以下能力之一：

```java
void updatePasswordByEmail(String email, String encodedPassword);
```

或者：

```java
void updatePasswordByUserId(Long userId, String encodedPassword);
```

建议优先通过用户 ID 更新，语义更稳定。

## 八、数据库设计

本方案第一版不新增表。

直接更新：

- `blog_user.password`

## 九、日志设计

建议接入当前操作日志体系，在控制器方法上增加操作日志注解。

建议记录：

- 发送找回密码验证码
- 重置密码成功
- 重置密码失败

示例：

```java
@Log(title = "认证管理", businessType = BusinessType.OTHER)
```

## 十、安全建议

### 1. 验证码必须一次性使用

验证码校验成功后应立即删除 Redis 中的验证码缓存。

### 2. 新密码必须加密

重置密码时必须使用：

- `BCryptPasswordEncoder`

### 3. 重置成功后建议清理旧 token

这样可以避免用户旧登录态仍然有效。

### 4. 发送频率必须限制

避免同一邮箱被短时间反复刷接口。

## 十一、建议开发顺序

### 第一步

新增 Redis 常量：

- `blog:password:code:{email}`
- `blog:password:send:{email}`

### 第二步

新增 DTO：

- `PasswordCodeSendDto`
- `PasswordResetDto`

### 第三步

扩展认证服务接口：

- `IBlogAuthService`

### 第四步

在 `BlogAuthServiceImpl` 中实现：

- 发送找回密码验证码
- 校验验证码
- 重置密码

### 第五步

扩展用户服务，增加密码更新能力。

### 第六步

在 `AuthController` 中新增接口：

- `POST /auth/password/code`
- `POST /auth/password/reset`

### 第七步

为新接口补充操作日志注解。

### 第八步

执行编译校验并联调：

```powershell
.\mvnw.cmd -pl admin -am compile -DskipTests
```

## 十二、第一版边界

第一版只做以下内容：

- 邮箱验证码发送
- 邮箱验证码校验
- 密码重置
- BCrypt 加密
- Redis 频控
- 操作日志记录

第一版暂不做：

- 找回密码专用 resetToken
- 图形验证码二次校验
- 短信找回
- 多步骤前端状态机
- 独立安全审计增强

## 十三、结论

当前项目最适合的忘记密码方案是：

- 两个接口
- 两个 DTO
- 两个 Redis Key
- 复用现有认证服务
- 直接更新 `blog_user.password`

这套方案和当前项目已有的邮箱、Redis、JWT、用户服务结构高度兼容，适合作为下一步认证能力增强的直接落地方案。
