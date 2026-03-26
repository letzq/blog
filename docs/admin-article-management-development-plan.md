# 后台文章管理开发清单

## 1. 目标说明

本文档用于定义博客后台管理员端的文章管理接口设计，覆盖以下模块：

- 文章分类管理
- 文章标签管理
- 文章管理

设计目标：

- 后台接口统一使用 `/system/**` 前缀
- 角色控制先按当前已落地的角色体系执行
- 优先满足 `admin` 和 `editor` 两类后台管理需求
- 与前台 `/app/**` 公开接口、作者自管理接口清晰隔离
- 所有接口补齐中文 OpenAPI 注释与统一返回结构

## 2. 权限设计

当前阶段建议使用角色级控制：

- `admin`
  - 拥有全部后台文章管理权限
  - 可管理分类、标签、全部文章
- `editor`
  - 可管理分类、标签、文章
  - 当前阶段可与 `admin` 保持同级内容管理权限
- `author`
  - 不进入后台管理文章模块
  - 继续使用 `/app/user/article/**`

推荐的接口权限：

- 分类管理：`hasAnyRole('admin','editor')`
- 标签管理：`hasAnyRole('admin','editor')`
- 文章管理：`hasAnyRole('admin','editor')`

后续如果要区分更细粒度权限，再升级到权限字符表。

## 3. 分类管理接口清单

基础前缀：

- `/system/article/category`

### 3.1 分类分页列表

- 方法：`GET`
- 路径：`/system/article/category/list`
- 说明：分页查询后台分类列表

查询参数建议：

- `categoryName`
- `status`
- `pageNum`
- `pageSize`

返回字段建议：

- `categoryId`
- `categoryName`
- `parentId`
- `sortNum`
- `status`
- `remark`
- `createTime`
- `articleCount`

### 3.2 分类详情

- 方法：`GET`
- 路径：`/system/article/category/{categoryId}`
- 说明：查询分类详情

### 3.3 新增分类

- 方法：`POST`
- 路径：`/system/article/category`
- 说明：新增文章分类

入参建议：

- `categoryName`
- `parentId`
- `sortNum`
- `status`
- `remark`

### 3.4 修改分类

- 方法：`PUT`
- 路径：`/system/article/category`
- 说明：修改文章分类

### 3.5 修改分类状态

- 方法：`PUT`
- 路径：`/system/article/category/changeStatus`
- 说明：单独切换分类状态

入参建议：

- `categoryId`
- `status`

### 3.6 删除分类

- 方法：`DELETE`
- 路径：`/system/article/category/{categoryIds}`
- 说明：删除分类

删除约束建议：

- 分类下存在文章时禁止删除
- 存在子分类时禁止删除

## 4. 标签管理接口清单

基础前缀：

- `/system/article/tag`

### 4.1 标签分页列表

- 方法：`GET`
- 路径：`/system/article/tag/list`
- 说明：分页查询后台标签列表

查询参数建议：

- `tagName`
- `status`
- `pageNum`
- `pageSize`

返回字段建议：

- `tagId`
- `tagName`
- `sortNum`
- `status`
- `remark`
- `createTime`
- `articleCount`

### 4.2 标签详情

- 方法：`GET`
- 路径：`/system/article/tag/{tagId}`
- 说明：查询标签详情

### 4.3 新增标签

- 方法：`POST`
- 路径：`/system/article/tag`

入参建议：

- `tagName`
- `sortNum`
- `status`
- `remark`

### 4.4 修改标签

- 方法：`PUT`
- 路径：`/system/article/tag`

### 4.5 修改标签状态

- 方法：`PUT`
- 路径：`/system/article/tag/changeStatus`

### 4.6 删除标签

- 方法：`DELETE`
- 路径：`/system/article/tag/{tagIds}`

删除约束建议：

- 标签已被文章使用时禁止删除

## 5. 文章管理接口清单

基础前缀：

- `/system/article`

### 5.1 文章分页列表

- 方法：`GET`
- 路径：`/system/article/list`
- 说明：分页查询后台文章列表

查询参数建议：

- `title`
- `status`
- `categoryId`
- `userName`
- `beginTime`
- `endTime`
- `pageNum`
- `pageSize`

返回字段建议：

- `articleId`
- `title`
- `summary`
- `coverImage`
- `categoryId`
- `categoryName`
- `authorId`
- `authorName`
- `status`
- `isTop`
- `publishTime`
- `viewCount`
- `updateTime`

### 5.2 文章详情

- 方法：`GET`
- 路径：`/system/article/{articleId}`
- 说明：查询后台文章详情

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
- `status`
- `isTop`
- `isOriginal`
- `originalUrl`
- `allowComment`
- `viewCount`
- `likeCount`
- `publishTime`
- `updateTime`
- `tagIds`

### 5.3 新增文章

- 方法：`POST`
- 路径：`/system/article`
- 说明：后台新增文章

入参建议：

- `title`
- `summary`
- `coverImage`
- `categoryId`
- `contentMd`
- `allowComment`
- `isOriginal`
- `originalUrl`
- `status`
- `isTop`
- `tagIds`
- `authorId`

说明：

- 后台文章管理可支持指定作者
- 如果不指定作者，可默认当前登录后台用户

### 5.4 修改文章

- 方法：`PUT`
- 路径：`/system/article`

### 5.5 修改文章状态

- 方法：`PUT`
- 路径：`/system/article/changeStatus`
- 说明：草稿、发布、下线切换

入参建议：

- `articleId`
- `status`

### 5.6 文章置顶切换

- 方法：`PUT`
- 路径：`/system/article/changeTop`

入参建议：

- `articleId`
- `isTop`

### 5.7 删除文章

- 方法：`DELETE`
- 路径：`/system/article/{articleIds}`

### 5.8 查询文章可选标签

- 方法：`GET`
- 路径：`/system/article/tagOptions`
- 说明：给文章编辑页加载可选标签列表

### 5.9 查询文章可选分类

- 方法：`GET`
- 路径：`/system/article/categoryOptions`
- 说明：给文章编辑页加载可选分类列表

## 6. DTO 设计建议

### 分类模块

- `BlogArticleCategoryQueryDto`
- `BlogArticleCategoryCreateDto`
- `BlogArticleCategoryUpdateDto`
- `BlogArticleCategoryChangeStatusDto`

### 标签模块

- `BlogTagQueryDto`
- `BlogTagCreateDto`
- `BlogTagUpdateDto`
- `BlogTagChangeStatusDto`

### 文章模块

- `BlogArticleQueryDto`
- `BlogArticleCreateDto`
- `BlogArticleUpdateDto`
- `BlogArticleChangeStatusDto`
- `BlogArticleChangeTopDto`

要求：

- 后台接口入参统一用 DTO
- 所有校验规则写在 DTO 上
- 不直接拿数据库实体当请求对象

## 7. VO 设计建议

### 分类模块

- `BlogArticleCategoryListVo`
- `BlogArticleCategoryDetailVo`
- `BlogArticleCategoryOptionVo`

### 标签模块

- `BlogTagListVo`
- `BlogTagDetailVo`
- `BlogTagOptionVo`

### 文章模块

- `BlogArticleListVo`
- `BlogArticleDetailVo`

## 8. 核心业务规则

### 分类规则

- 分类名称唯一
- 状态只允许 `0/1`
- 删除前校验是否存在子分类和文章引用

### 标签规则

- 标签名称唯一
- 状态只允许 `0/1`
- 删除前校验是否被文章引用

### 文章规则

- 状态只允许 `0/1/2`
- 转载文章必须填写原文地址
- 标签不能重复
- 分类必须存在且启用
- 标签必须存在且启用
- 发布文章时如果 `publishTime` 为空，自动填当前时间

## 9. OpenAPI 注释要求

控制器层统一要求：

- 类上使用 `@Tag`
- 方法上使用 `@Operation`
- DTO / VO 字段上使用 `@Schema`

要求：

- 注释全部使用中文
- 接口说明要写清用途和权限边界
- 对后台状态变更类接口写清可选状态值

## 10. 操作日志要求

后台管理接口统一接入 AOP 操作日志：

- 分类新增/修改/删除/状态切换
- 标签新增/修改/删除/状态切换
- 文章新增/修改/删除/状态切换/置顶切换

建议业务类型：

- 查询：`BusinessType.OTHER`
- 新增：`BusinessType.INSERT`
- 修改：`BusinessType.UPDATE`
- 删除：`BusinessType.DELETE`

## 11. 建议开发顺序

建议按下面顺序落地：

1. 分类管理
   - 分类列表
   - 分类新增
   - 分类修改
   - 分类状态切换
   - 分类删除

2. 标签管理
   - 标签列表
   - 标签新增
   - 标签修改
   - 标签状态切换
   - 标签删除

3. 文章管理
   - 文章列表
   - 文章详情
   - 文章新增
   - 文章修改
   - 文章状态切换
   - 文章置顶切换
   - 文章删除

## 12. 第一阶段建议范围

为了尽快形成后台可用闭环，建议先做这一批：

- `GET /system/article/category/list`
- `POST /system/article/category`
- `PUT /system/article/category`
- `GET /system/article/tag/list`
- `POST /system/article/tag`
- `PUT /system/article/tag`
- `GET /system/article/list`
- `GET /system/article/{articleId}`
- `POST /system/article`
- `PUT /system/article`

## 13. 下一步优化建议

- 文章管理第一版完成后，再补分类和标签的删除保护
- 后续可以加文章统计接口，例如分类文章数、标签文章数、文章发布趋势
- 如果后台编辑页要更方便，可再补一个统一的“文章编辑页初始化接口”
