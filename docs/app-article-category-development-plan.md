# 前台文章与分类接口开发清单

## 1. 目标说明

本文档用于定义博客前台文章与分类接口的第一阶段方案。

设计目标：

- 前台公开接口统一使用 `/app` 前缀
- 未登录用户也可以查看已发布的文章和分类数据
- 支持按分类筛选文章
- 为后续前台作者中心接口预留扩展空间

当前阶段先落地公开接口，不包含后台管理接口。

## 2. 路由分组约定

前台公开接口统一前缀：

- `/app/category/**`
- `/app/article/**`

后续前台登录用户接口建议前缀：

- `/app/user/**`

后台管理接口仍保持现有风格：

- `/system/**`
- `/auth/**`
- `/common/**`

## 3. 公开接口设计原则

- 所有 `/app/**` 公开接口默认允许匿名访问
- 文章公开接口只返回 `status = 1` 的已发布文章
- 分类公开接口只返回 `status = 1` 的启用分类
- 公开接口不返回后台内部字段、审计字段、草稿数据
- 文章列表默认按 `is_top DESC, publish_time DESC` 排序
- 前台详情接口可增加浏览量

## 4. 分类接口清单

### 4.1 查询分类列表

- 方法：`GET`
- 路径：`/app/category/list`
- 说明：查询前台可见分类列表
- 访问权限：匿名可访问

返回字段建议：

- `categoryId`
- `categoryName`
- `sortNum`
- `articleCount`

返回示例：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "categoryId": 1,
      "categoryName": "Java",
      "sortNum": 1,
      "articleCount": 12
    },
    {
      "categoryId": 2,
      "categoryName": "Spring Boot",
      "sortNum": 2,
      "articleCount": 8
    }
  ]
}
```

### 4.2 查询分类详情

- 方法：`GET`
- 路径：`/app/category/{categoryId}`
- 说明：查询单个分类详情
- 访问权限：匿名可访问

返回字段建议：

- `categoryId`
- `categoryName`
- `remark`
- `sortNum`
- `articleCount`

返回示例：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "categoryId": 1,
    "categoryName": "Java",
    "remark": "Java 语言相关内容",
    "sortNum": 1,
    "articleCount": 12
  }
}
```

## 5. 文章公开接口清单

### 5.1 查询文章分页列表

- 方法：`GET`
- 路径：`/app/article/list`
- 说明：分页查询前台文章列表
- 访问权限：匿名可访问

查询参数建议：

- `pageNum`
- `pageSize`
- `categoryId`
- `keyword`

查询规则：

- 只查询已发布文章
- 支持按分类筛选
- 支持按标题关键字搜索
- 默认按置顶优先、发布时间倒序

返回字段建议：

- `articleId`
- `title`
- `summary`
- `coverImage`
- `categoryId`
- `categoryName`
- `authorName`
- `publishTime`
- `viewCount`
- `isTop`

返回示例：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "total": 2,
    "rows": [
      {
        "articleId": 10,
        "title": "Spring Boot 整合 Redis",
        "summary": "介绍 Spring Boot 与 Redis 的基础整合方式。",
        "coverImage": "https://cdn.example.com/article/cover/2026/03/26/abc.jpg",
        "categoryId": 2,
        "categoryName": "Spring Boot",
        "authorName": "ROOT",
        "publishTime": "2026-03-26 10:30:00",
        "viewCount": 120,
        "isTop": "1"
      },
      {
        "articleId": 9,
        "title": "Java 并发基础",
        "summary": "从线程、锁和线程池三个角度介绍并发开发。",
        "coverImage": null,
        "categoryId": 1,
        "categoryName": "Java",
        "authorName": "ROOT",
        "publishTime": "2026-03-25 20:15:00",
        "viewCount": 86,
        "isTop": "0"
      }
    ]
  }
}
```

### 5.2 查询文章详情

- 方法：`GET`
- 路径：`/app/article/{articleId}`
- 说明：查询文章详情
- 访问权限：匿名可访问

查询规则：

- 只允许查看已发布文章
- 可在接口中增加浏览量

返回字段建议：

- `articleId`
- `title`
- `summary`
- `coverImage`
- `contentMd`
- `categoryId`
- `categoryName`
- `authorId`
- `authorName`
- `publishTime`
- `viewCount`
- `likeCount`
- `isOriginal`
- `originalUrl`
- `allowComment`
- `tags`

返回示例：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "articleId": 10,
    "title": "Spring Boot 整合 Redis",
    "summary": "介绍 Spring Boot 与 Redis 的基础整合方式。",
    "coverImage": "https://cdn.example.com/article/cover/2026/03/26/abc.jpg",
    "contentMd": "# Spring Boot 整合 Redis\\n\\n这里是正文内容。",
    "categoryId": 2,
    "categoryName": "Spring Boot",
    "authorId": 1,
    "authorName": "ROOT",
    "publishTime": "2026-03-26 10:30:00",
    "viewCount": 121,
    "likeCount": 5,
    "isOriginal": "1",
    "originalUrl": null,
    "allowComment": "1",
    "tags": [
      {
        "tagId": 1,
        "tagName": "Redis"
      },
      {
        "tagId": 2,
        "tagName": "Spring Boot"
      }
    ]
  }
}
```

### 5.3 查询热门文章

- 方法：`GET`
- 路径：`/app/article/hot/list`
- 说明：查询热门文章列表
- 访问权限：匿名可访问

查询规则：

- 只查询已发布文章
- 按浏览量倒序返回前 N 条

### 5.4 查询最新文章

- 方法：`GET`
- 路径：`/app/article/latest/list`
- 说明：查询最新文章列表
- 访问权限：匿名可访问

查询规则：

- 只查询已发布文章
- 按发布时间倒序返回前 N 条

### 5.5 查询文章归档

- 方法：`GET`
- 路径：`/app/article/archive/list`
- 说明：查询文章归档数据
- 访问权限：匿名可访问

## 6. 第一阶段优先落地范围

第一阶段先开发以下 3 个接口：

- `GET /app/category/list`
- `GET /app/article/list`
- `GET /app/article/{articleId}`

原因：

- 首页需要分类和文章列表
- 分类页需要按分类查询文章
- 详情页需要文章详情

这 3 个接口完成后，前台首页、分类页、详情页就能先跑起来。

## 7. OpenAPI 注释要求

控制器层必须补齐中文接口文档注释：

- 类上使用 `@Tag`
- 方法上使用 `@Operation`
- DTO / VO 字段上使用 `@Schema`

文档要求：

- 注释全部使用中文
- 说明接口用途、权限边界、关键筛选条件
- 返回对象字段名称尽量与前端展示语义一致

## 8. 权限与安全要求

- `/app/**` 第一阶段默认开放匿名访问
- 公开接口只返回已发布和启用数据
- 草稿、下线文章不能通过公开接口访问
- 后续作者接口和后台接口应继续和 `/app/**` 公开接口隔离

## 9. 下一步开发顺序

建议按下面顺序落代码：

1. 分类公开列表接口
2. 文章公开分页接口
3. 文章公开详情接口
4. 热门文章接口
5. 最新文章接口
6. 文章归档接口
7. 前台作者自管理文章接口

## 10. 下一步优化建议

- 文章列表接口完成后，可继续补分类文章数量统计缓存
- 文章详情接口完成后，可接入浏览量自增
- 后续如果前台要支持标签筛选，可继续扩展 `tagId` 查询条件
- 作者自管理接口建议继续沿用 `/app/user/article/**` 风格，不和公开接口混用
