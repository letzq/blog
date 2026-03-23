# 认证开发清单

## 目标

为当前博客项目设计并实现一套支持以下能力的认证体系：

- 账号 + 密码 + 验证码登录
- 邮箱 + 验证码登录
- 邮箱验证码注册
- JWT 签发
- Redis 存储验证码和登录态
- 邮件发送验证码

## 认证接口设计

建议采用多接口拆分，而不是把所有认证方式塞进一个接口：

1. `POST /auth/captcha`
用途：获取图形验证码或验证码标识，用于账号密码登录前的人机校验。

2. `POST /auth/login/password`
用途：账号 + 密码 + 验证码登录。

3. `POST /auth/email/code`
用途：发送邮箱验证码，支持登录和注册两种场景。

4. `POST /auth/login/email`
用途：邮箱 + 验证码登录。

5. `POST /auth/register`
用途：用户名 + 密码 + 邮箱 + 邮箱验证码注册。

## 登录与注册规则

### 账号密码登录

入参建议：

- `userName`
- `password`
- `captchaKey`
- `captchaCode`

校验流程：

1. 校验图形验证码是否存在、是否正确、是否过期
2. 根据 `userName` 查询用户
3. 校验用户是否存在
4. 校验用户状态是否正常
5. 使用 `PasswordEncoder.matches` 校验密码
6. 签发 JWT
7. 写入 Redis 登录态

### 邮箱验证码登录

入参建议：

- `email`
- `code`

校验流程：

1. 根据 `email` 查询用户
2. 校验用户是否存在
3. 校验用户状态是否正常
4. 校验 Redis 中的邮箱登录验证码
5. 验证通过后签发 JWT
6. 清理已使用验证码

规则建议：

- 只允许已注册邮箱登录
- 不在邮箱登录时自动创建账号

### 注册

入参建议：

- `userName`
- `password`
- `nickName`
- `email`
- `code`

校验流程：

1. 校验用户名是否已存在
2. 校验邮箱是否已存在
3. 校验邮箱注册验证码
4. 使用 BCrypt 加密密码
5. 创建用户
6. 清理验证码
7. 可选：注册成功后直接签发 JWT

## 验证码设计

### 账号密码登录验证码

推荐做法：

- 第一阶段先做数字或字母验证码
- 生成验证码后放入 Redis
- 返回 `captchaKey` 给前端
- 登录时前端提交 `captchaKey + captchaCode`

Redis key 建议：

- `blog:captcha:login:{captchaKey}`

过期时间建议：

- 5 分钟

### 邮箱验证码

发送场景建议：

- `LOGIN`
- `REGISTER`

Redis key 建议：

- `blog:email:code:login:{email}`
- `blog:email:code:register:{email}`

发送频率限制 key：

- `blog:email:send:login:{email}`
- `blog:email:send:register:{email}`

规则建议：

- 验证码 6 位数字
- 有效期 5 分钟
- 同一邮箱 60 秒内禁止重复发送

## JWT 设计

当前项目已经有：

- `JwtProperties`
- `JwtUtils`
- `TokenService`
- `/auth/login` 示例接口

后续改造方向：

1. 保留 JWT 配置和签发逻辑
2. 把登录接口拆成密码登录和邮箱登录
3. 后面再补 JWT 过滤器接管受保护接口

## 邮件发送设计

建议新增：

- Spring Mail 依赖
- `MailProperties`
- `MailService`

邮件配置来源：

- `host = smtp.163.com`
- `port = 25`
- `from = m17870433516@163.com`
- `user = m17870433516`
- `pass = VAhTi38ahC7ZQJy4`

注意：

- 这些配置建议写入 `application-local.yml`
- `dev/prod` 环境建议改成环境变量
- 密码属于敏感信息，后续应考虑改成授权码+环境变量管理

## 模块落点建议

### framework

- JWT 配置与工具
- Redis 工具
- 邮件配置与邮件发送工具
- 验证码生成工具
- 安全过滤器

### system

- 认证 DTO
- 认证 VO
- 用户查询与注册业务
- 验证码业务逻辑

### admin

- `AuthController`

## DTO / VO 规划

建议新增这些对象：

- `PasswordLoginDto`
- `EmailCodeSendDto`
- `EmailLoginDto`
- `RegisterDto`
- `CaptchaVo`
- `LoginVo`

## 开发顺序

1. 新增邮件依赖和邮件配置
2. 新增邮件发送工具
3. 新增验证码 Redis key 常量
4. 新增邮箱验证码发送接口
5. 新增账号密码登录验证码生成接口
6. 改造密码登录接口为 `账号 + 密码 + 验证码`
7. 新增邮箱验证码登录接口
8. 新增注册接口
9. 优化统一异常提示
10. 补 JWT 认证过滤器
11. 编译并联调

## 需要重点注意的问题

1. 当前 `application-local.yml` 中数据库名和其他环境不一致，后续最好统一。
2. 邮件发送失败需要单独提示，不要直接吞异常。
3. 登录验证码和邮箱验证码要严格区分场景。
4. 注册时建议同时校验用户名唯一和邮箱唯一。
5. 当前示例里密码已经做了 BCrypt 加密，后续登录必须统一走 `PasswordEncoder.matches`。

## 审核后准备落地的第一步

审核通过后，建议先从这一步开始：

1. 加 Spring Mail 依赖
2. 配邮件配置
3. 建 `MailProperties` 和 `MailService`
4. 先打通邮箱验证码发送
