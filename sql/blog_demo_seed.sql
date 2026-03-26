START TRANSACTION;

INSERT INTO blog_article_category (category_name, parent_id, ancestors, sort_num, status, remark, create_by)
SELECT 'Java', 0, '', 1, '1', 'Java 相关文章', 'system'
WHERE NOT EXISTS (SELECT 1 FROM blog_article_category WHERE category_name = 'Java');

INSERT INTO blog_article_category (category_name, parent_id, ancestors, sort_num, status, remark, create_by)
SELECT 'Spring Boot', 0, '', 2, '1', 'Spring Boot 相关文章', 'system'
WHERE NOT EXISTS (SELECT 1 FROM blog_article_category WHERE category_name = 'Spring Boot');

INSERT INTO blog_article_category (category_name, parent_id, ancestors, sort_num, status, remark, create_by)
SELECT 'Redis', 0, '', 3, '1', 'Redis 相关文章', 'system'
WHERE NOT EXISTS (SELECT 1 FROM blog_article_category WHERE category_name = 'Redis');

INSERT INTO blog_tag (tag_name, sort_num, status, remark, create_by)
SELECT 'Java', 1, '1', 'Java 标签', 'system'
WHERE NOT EXISTS (SELECT 1 FROM blog_tag WHERE tag_name = 'Java');

INSERT INTO blog_tag (tag_name, sort_num, status, remark, create_by)
SELECT 'Spring Boot', 2, '1', 'Spring Boot 标签', 'system'
WHERE NOT EXISTS (SELECT 1 FROM blog_tag WHERE tag_name = 'Spring Boot');

INSERT INTO blog_tag (tag_name, sort_num, status, remark, create_by)
SELECT 'Redis', 3, '1', 'Redis 标签', 'system'
WHERE NOT EXISTS (SELECT 1 FROM blog_tag WHERE tag_name = 'Redis');

SET @author_id = (SELECT user_id FROM blog_user WHERE user_name = 'root' AND del_flag = '0' LIMIT 1);
SET @cat_java = (SELECT category_id FROM blog_article_category WHERE category_name = 'Java' LIMIT 1);
SET @cat_spring = (SELECT category_id FROM blog_article_category WHERE category_name = 'Spring Boot' LIMIT 1);
SET @cat_redis = (SELECT category_id FROM blog_article_category WHERE category_name = 'Redis' LIMIT 1);
SET @tag_java = (SELECT tag_id FROM blog_tag WHERE tag_name = 'Java' LIMIT 1);
SET @tag_spring = (SELECT tag_id FROM blog_tag WHERE tag_name = 'Spring Boot' LIMIT 1);
SET @tag_redis = (SELECT tag_id FROM blog_tag WHERE tag_name = 'Redis' LIMIT 1);

INSERT INTO blog_article (
    category_id, user_id, title, summary, cover_image, content_md,
    is_top, is_original, original_url, allow_comment, status,
    view_count, like_count, publish_time, remark, create_by
)
SELECT @cat_java, @author_id,
       'Java 21 新特性在博客项目中的实践',
       '结合当前博客项目，介绍 Java 21 在后端开发中的几个实用特性。',
       'https://ncbc-ai.oss-cn-beijing.aliyuncs.com/article/cover/demo-java-21.jpg',
       '# Java 21 新特性在博客项目中的实践\n\n这是一篇用于接口联调的测试文章，主要介绍记录类、模式匹配和虚拟线程。\n\n## 核心内容\n\n- 记录类\n- 模式匹配\n- 虚拟线程\n',
       '1', '1', NULL, '1', '1',
       128, 12, NOW(), '测试文章', 'system'
WHERE NOT EXISTS (SELECT 1 FROM blog_article WHERE title = 'Java 21 新特性在博客项目中的实践');

INSERT INTO blog_article (
    category_id, user_id, title, summary, cover_image, content_md,
    is_top, is_original, original_url, allow_comment, status,
    view_count, like_count, publish_time, remark, create_by
)
SELECT @cat_spring, @author_id,
       'Spring Boot 3 整合 JWT 与 Redis 登录方案',
       '演示如何在 Spring Boot 3 项目中接入 JWT 登录与 Redis 登录态管理。',
       'https://ncbc-ai.oss-cn-beijing.aliyuncs.com/article/cover/demo-spring-security.jpg',
       '# Spring Boot 3 整合 JWT 与 Redis 登录方案\n\n这是一篇用于接口联调的测试文章，内容围绕认证流程设计。\n\n## 方案摘要\n\n- 登录签发 JWT\n- Redis 维护 token 状态\n- 过滤器恢复用户上下文\n',
       '0', '1', NULL, '1', '1',
       96, 8, DATE_SUB(NOW(), INTERVAL 1 DAY), '测试文章', 'system'
WHERE NOT EXISTS (SELECT 1 FROM blog_article WHERE title = 'Spring Boot 3 整合 JWT 与 Redis 登录方案');

INSERT INTO blog_article (
    category_id, user_id, title, summary, cover_image, content_md,
    is_top, is_original, original_url, allow_comment, status,
    view_count, like_count, publish_time, remark, create_by
)
SELECT @cat_redis, @author_id,
       'Redis 缓存设计与博客首页性能优化',
       '围绕分类、标签、热门文章等前台接口，说明 Redis 缓存的应用方式。',
       'https://ncbc-ai.oss-cn-beijing.aliyuncs.com/article/cover/demo-redis-cache.jpg',
       '# Redis 缓存设计与博客首页性能优化\n\n这是一篇用于接口联调的测试文章，主要说明缓存前台公开接口的思路。\n\n## 可缓存数据\n\n- 分类列表\n- 标签列表\n- 热门文章\n- 最新文章\n',
       '0', '1', NULL, '1', '1',
       64, 5, DATE_SUB(NOW(), INTERVAL 2 DAY), '测试文章', 'system'
WHERE NOT EXISTS (SELECT 1 FROM blog_article WHERE title = 'Redis 缓存设计与博客首页性能优化');

SET @article_java = (SELECT article_id FROM blog_article WHERE title = 'Java 21 新特性在博客项目中的实践' LIMIT 1);
SET @article_spring = (SELECT article_id FROM blog_article WHERE title = 'Spring Boot 3 整合 JWT 与 Redis 登录方案' LIMIT 1);
SET @article_redis = (SELECT article_id FROM blog_article WHERE title = 'Redis 缓存设计与博客首页性能优化' LIMIT 1);

INSERT INTO blog_article_tag (article_id, tag_id)
SELECT @article_java, @tag_java
WHERE NOT EXISTS (SELECT 1 FROM blog_article_tag WHERE article_id = @article_java AND tag_id = @tag_java);

INSERT INTO blog_article_tag (article_id, tag_id)
SELECT @article_spring, @tag_spring
WHERE NOT EXISTS (SELECT 1 FROM blog_article_tag WHERE article_id = @article_spring AND tag_id = @tag_spring);

INSERT INTO blog_article_tag (article_id, tag_id)
SELECT @article_spring, @tag_redis
WHERE NOT EXISTS (SELECT 1 FROM blog_article_tag WHERE article_id = @article_spring AND tag_id = @tag_redis);

INSERT INTO blog_article_tag (article_id, tag_id)
SELECT @article_redis, @tag_redis
WHERE NOT EXISTS (SELECT 1 FROM blog_article_tag WHERE article_id = @article_redis AND tag_id = @tag_redis);

COMMIT;
