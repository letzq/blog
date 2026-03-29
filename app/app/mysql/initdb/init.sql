/*
 Navicat Premium Dump SQL

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : localhost:3306
 Source Schema         : blogs

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 28/03/2026 00:53:22
*/
U

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
use `blogs`;

-- ----------------------------
-- Table structure for blog_article
-- ----------------------------
DROP TABLE IF EXISTS `blog_article`;
CREATE TABLE `blog_article`  (
  `article_id` bigint NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `category_id` bigint NULL DEFAULT NULL COMMENT '分类ID',
  `user_id` bigint NOT NULL COMMENT '作者用户ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章标题',
  `summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章摘要',
  `cover_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面图地址',
  `content_md` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Markdown内容',
  `is_top` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '是否置顶（0否 1是）',
  `is_original` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '是否原创（0转载 1原创）',
  `original_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原文地址',
  `allow_comment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '是否允许评论（0否 1是）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '文章状态（0草稿 1发布 2下线）',
  `view_count` bigint NOT NULL DEFAULT 0 COMMENT '浏览量',
  `like_count` bigint NOT NULL DEFAULT 0 COMMENT '点赞数',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`article_id`) USING BTREE,
  INDEX `idx_blog_article_category_id`(`category_id` ASC) USING BTREE COMMENT '分类索引',
  INDEX `idx_blog_article_user_id`(`user_id` ASC) USING BTREE COMMENT '作者索引',
  INDEX `idx_blog_article_status`(`status` ASC) USING BTREE COMMENT '文章状态索引',
  INDEX `idx_blog_article_publish_time`(`publish_time` ASC) USING BTREE COMMENT '发布时间索引'
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_article
-- ----------------------------
INSERT INTO `blog_article` VALUES (4, 1, 1, 'Java 21 新特性在博客项目中的实践', '结合当前博客项目，介绍 Java 21 在后端开发中的几个实用特性。', 'https://ncbc-ai.oss-cn-beijing.aliyuncs.com/avatar/2026/03/26/024989c622d5429f88695cb5b9771d2c.jpg', '# Java 21 新特性在博客项目中的实践\n\n这是一篇用于接口联调的测试文章，主要介绍记录类、模式匹配和虚拟线程。\n\n## 核心内容\nqqqq\n- 记录类\n- 模式匹配\n- 虚拟线程\n', '1', '1', NULL, '1', '1', 149, 12, '2026-03-26 16:38:40', '测试文章', 'system', '2026-03-26 16:38:40', 'system', '2026-03-26 23:14:29');
INSERT INTO `blog_article` VALUES (5, 2, 1, 'Spring Boot 3 整合 JWT 与 Redis 登录方案', '演示如何在 Spring Boot 3 项目中接入 JWT 登录与 Redis 登录态管理。', 'https://ncbc-ai.oss-cn-beijing.aliyuncs.com/article/cover/demo-spring-security.jpg', '# Spring Boot 3 整合 JWT 与 Redis 登录方案\n\n这是一篇用于接口联调的测试文章，内容围绕认证流程设计。\n\n## 方案摘要\n\n- 登录签发 JWT\n- Redis 维护 token 状态\n- 过滤器恢复用户上下文\n', '0', '1', NULL, '1', '1', 98, 8, '2026-03-25 16:38:40', '测试文章', 'system', '2026-03-26 16:38:40', NULL, '2026-03-27 17:09:04');
INSERT INTO `blog_article` VALUES (6, 3, 1, 'Redis 缓存设计与博客首页性能优化', '围绕分类、标签、热门文章等前台接口，说明 Redis 缓存的应用方式。', 'https://ncbc-ai.oss-cn-beijing.aliyuncs.com/article/cover/demo-redis-cache.jpg', '# Redis 缓存设计与博客首页性能优化\n\n这是一篇用于接口联调的测试文章，主要说明缓存前台公开接口的思路。\n\n## 可缓存数据\n\n- 分类列表\n- 标签列表\n- 热门文章\n- 最新文章\n', '0', '1', NULL, '1', '1', 68, 5, '2026-03-24 16:38:40', '测试文章', 'system', '2026-03-26 16:38:40', NULL, '2026-03-27 17:09:01');
INSERT INTO `blog_article` VALUES (7, 1, 1, 'weqw', 'qweqw', 'qeqw', 'qweqweqw', '0', '1', NULL, '1', '1', 0, 0, '2026-03-27 17:14:27', NULL, 'system', '2026-03-27 17:13:58', 'system', '2026-03-27 17:13:58');

-- ----------------------------
-- Table structure for blog_article_category
-- ----------------------------
DROP TABLE IF EXISTS `blog_article_category`;
CREATE TABLE `blog_article_category`  (
  `category_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父分类ID',
  `ancestors` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '祖级列表',
  `sort_num` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '分类状态（0停用 1正常）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`category_id`) USING BTREE,
  UNIQUE INDEX `uk_blog_article_category_name`(`category_name` ASC) USING BTREE COMMENT '分类名称唯一索引',
  INDEX `idx_blog_article_category_parent_id`(`parent_id` ASC) USING BTREE COMMENT '父分类索引',
  INDEX `idx_blog_article_category_status`(`status` ASC) USING BTREE COMMENT '分类状态索引'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_article_category
-- ----------------------------
INSERT INTO `blog_article_category` VALUES (1, 'Java', 0, '', 1, '1', 'Java ????', 'system', '2026-03-26 16:37:57', NULL, '2026-03-26 16:37:57');
INSERT INTO `blog_article_category` VALUES (2, 'Spring Boot', 0, '', 2, '1', 'Spring Boot ????', 'system', '2026-03-26 16:37:57', NULL, '2026-03-26 16:37:57');
INSERT INTO `blog_article_category` VALUES (3, 'Redis', 0, '', 3, '1', 'Redis ????', 'system', '2026-03-26 16:37:57', NULL, '2026-03-26 16:37:57');

-- ----------------------------
-- Table structure for blog_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_article_tag`;
CREATE TABLE `blog_article_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `article_id` bigint NOT NULL COMMENT '文章ID',
  `tag_id` bigint NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_blog_article_tag`(`article_id` ASC, `tag_id` ASC) USING BTREE COMMENT '文章标签唯一索引',
  INDEX `idx_blog_article_tag_article_id`(`article_id` ASC) USING BTREE COMMENT '文章索引',
  INDEX `idx_blog_article_tag_tag_id`(`tag_id` ASC) USING BTREE COMMENT '标签索引'
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章标签关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_article_tag
-- ----------------------------
INSERT INTO `blog_article_tag` VALUES (1, 1, 1);
INSERT INTO `blog_article_tag` VALUES (2, 2, 2);
INSERT INTO `blog_article_tag` VALUES (3, 2, 3);
INSERT INTO `blog_article_tag` VALUES (4, 3, 3);
INSERT INTO `blog_article_tag` VALUES (9, 4, 1);
INSERT INTO `blog_article_tag` VALUES (6, 5, 2);
INSERT INTO `blog_article_tag` VALUES (7, 5, 3);
INSERT INTO `blog_article_tag` VALUES (8, 6, 3);
INSERT INTO `blog_article_tag` VALUES (10, 7, 1);

-- ----------------------------
-- Table structure for blog_login_log
-- ----------------------------
DROP TABLE IF EXISTS `blog_login_log`;
CREATE TABLE `blog_login_log`  (
  `login_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '登录日志ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `login_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录方式，PASSWORD/EMAIL_CODE',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录IP',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录地点',
  `browser` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '浏览器',
  `os` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作系统',
  `device_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '设备信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '登录状态，1成功0失败',
  `message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '结果消息',
  `login_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  PRIMARY KEY (`login_log_id`) USING BTREE,
  INDEX `idx_user_name`(`user_name` ASC) USING BTREE,
  INDEX `idx_login_type`(`login_type` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_login_time`(`login_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '登录日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_login_log
-- ----------------------------
INSERT INTO `blog_login_log` VALUES (1, NULL, NULL, 'EMAIL_CODE', 'kgs_g3880@sina.com', '0:0:0:0:0:0:0:1', '', 'Unknown', 'Unknown', 'Desktop', '0', '该邮箱未注册', '2026-03-24 15:44:36');
INSERT INTO `blog_login_log` VALUES (2, NULL, NULL, 'EMAIL_CODE', 'kgs_g3880@sina.com', '0:0:0:0:0:0:0:1', '', 'Unknown', 'Unknown', 'Desktop', '0', '该邮箱未注册', '2026-03-24 15:46:50');
INSERT INTO `blog_login_log` VALUES (3, NULL, NULL, 'EMAIL_CODE', 'kgs_g3880@sina.com', '0:0:0:0:0:0:0:1', '', 'Unknown', 'Unknown', 'Desktop', '0', '该邮箱未注册', '2026-03-24 15:47:42');
INSERT INTO `blog_login_log` VALUES (4, NULL, NULL, 'EMAIL_CODE', 'kgs_g3880@sina.com', '0:0:0:0:0:0:0:1', '', 'Unknown', 'Unknown', 'Desktop', '0', '该邮箱未注册', '2026-03-24 15:53:55');
INSERT INTO `blog_login_log` VALUES (5, NULL, NULL, 'EMAIL_CODE', 'kgs_g3880@sina.com', '0:0:0:0:0:0:0:1', '', 'Unknown', 'Unknown', 'Desktop', '0', '该邮箱未注册', '2026-03-24 16:05:46');
INSERT INTO `blog_login_log` VALUES (6, NULL, 'root', 'PASSWORD', NULL, '0:0:0:0:0:0:0:1', '', 'Unknown', 'Unknown', 'Desktop', '0', '验证码不存在或已过期', '2026-03-24 18:43:55');
INSERT INTO `blog_login_log` VALUES (7, NULL, 'root', 'PASSWORD', NULL, '0:0:0:0:0:0:0:1', '', 'Unknown', 'Unknown', 'Desktop', '0', '验证码不存在或已过期', '2026-03-24 18:44:22');
INSERT INTO `blog_login_log` VALUES (8, 1, 'root', 'PASSWORD', '489572770@qq.com', '0:0:0:0:0:0:0:1', '', 'Unknown', 'Unknown', 'Desktop', '1', '登录成功', '2026-03-24 18:44:36');
INSERT INTO `blog_login_log` VALUES (9, 1, 'root', 'EMAIL_CODE', '489572770@qq.com', '0:0:0:0:0:0:0:1', '', 'Unknown', 'Unknown', 'Desktop', '1', '登录成功', '2026-03-24 20:31:57');
INSERT INTO `blog_login_log` VALUES (10, 1, 'root', 'EMAIL_CODE', '489572770@qq.com', '0:0:0:0:0:0:0:1', '', 'Unknown', 'Unknown', 'Desktop', '1', '登录成功', '2026-03-24 22:45:09');
INSERT INTO `blog_login_log` VALUES (11, 1, 'root', 'EMAIL_CODE', '489572770@qq.com', '0:0:0:0:0:0:0:1', '', 'Unknown', 'Unknown', 'Desktop', '1', '登录成功', '2026-03-25 09:49:31');
INSERT INTO `blog_login_log` VALUES (12, 1, 'root', 'PASSWORD', '489572770@qq.com', '0:0:0:0:0:0:0:1', '', 'Chrome', 'Android', 'Mobile', '1', '登录成功', '2026-03-26 00:02:37');
INSERT INTO `blog_login_log` VALUES (13, 1, 'root', 'EMAIL_CODE', '489572770@qq.com', '0:0:0:0:0:0:0:1', '', 'Safari', 'macOS', 'Mobile', '1', '登录成功', '2026-03-26 00:13:10');
INSERT INTO `blog_login_log` VALUES (14, 1, 'root', 'PASSWORD', '489572770@qq.com', '0:0:0:0:0:0:0:1', '', 'Chrome', 'Android', 'Mobile', '1', '登录成功', '2026-03-26 00:27:30');
INSERT INTO `blog_login_log` VALUES (15, 1, 'root', 'PASSWORD', '489572770@qq.com', '0:0:0:0:0:0:0:1', '', 'Safari', 'macOS', 'Mobile', '1', '登录成功', '2026-03-26 00:29:27');
INSERT INTO `blog_login_log` VALUES (16, NULL, 'roo', 'PASSWORD', NULL, '0:0:0:0:0:0:0:1', '', 'Chrome', 'Android', 'Mobile', '0', '用户名或密码错误', '2026-03-26 00:31:45');
INSERT INTO `blog_login_log` VALUES (17, NULL, 'roots', 'PASSWORD', NULL, '0:0:0:0:0:0:0:1', '', 'Chrome', 'Android', 'Mobile', '0', '用户名或密码错误', '2026-03-26 00:31:59');
INSERT INTO `blog_login_log` VALUES (18, 1, 'root', 'PASSWORD', '489572770@qq.com', '0:0:0:0:0:0:0:1', '', 'Chrome', 'Android', 'Mobile', '1', '登录成功', '2026-03-26 00:32:11');
INSERT INTO `blog_login_log` VALUES (19, 1, 'root', 'PASSWORD', '489572770@qq.com', '0:0:0:0:0:0:0:1', '', 'Safari', 'macOS', 'Mobile', '1', '登录成功', '2026-03-26 00:46:58');
INSERT INTO `blog_login_log` VALUES (20, 1, 'root', 'PASSWORD', '489572770@qq.com', '0:0:0:0:0:0:0:1', '', 'Safari', 'macOS', 'Mobile', '1', '登录成功', '2026-03-26 00:48:46');
INSERT INTO `blog_login_log` VALUES (21, 1, 'root', 'PASSWORD', '489572770@qq.com', '0:0:0:0:0:0:0:1', '', 'Safari', 'macOS', 'Mobile', '1', '登录成功', '2026-03-26 00:51:32');
INSERT INTO `blog_login_log` VALUES (22, 3, 'ROOT', 'PASSWORD', '12312@qq.com', '0:0:0:0:0:0:0:1', '', 'Safari', 'macOS', 'Mobile', '1', '登录成功', '2026-03-26 01:09:03');
INSERT INTO `blog_login_log` VALUES (23, 1, 'root', 'PASSWORD', '489572770@qq.com', '0:0:0:0:0:0:0:1', '', 'Safari', 'macOS', 'Mobile', '1', '登录成功', '2026-03-26 15:19:34');
INSERT INTO `blog_login_log` VALUES (24, 1, 'root', 'PASSWORD', '489572770@qq.com', '0:0:0:0:0:0:0:1', '', 'Safari', 'macOS', 'Mobile', '1', '登录成功', '2026-03-26 18:45:24');
INSERT INTO `blog_login_log` VALUES (25, 1, 'root', 'PASSWORD', '489572770@qq.com', '0:0:0:0:0:0:0:1', '', 'Safari', 'macOS', 'Mobile', '1', '登录成功', '2026-03-26 20:04:33');
INSERT INTO `blog_login_log` VALUES (26, 1, 'root', 'PASSWORD', '489572770@qq.com', '0:0:0:0:0:0:0:1', '', 'Safari', 'macOS', 'Mobile', '1', '登录成功', '2026-03-27 17:09:45');

-- ----------------------------
-- Table structure for blog_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `blog_oper_log`;
CREATE TABLE `blog_oper_log`  (
  `oper_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '操作日志ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模块标题',
  `business_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务类型，INSERT/UPDATE/DELETE/OTHER',
  `method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方式',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作IP',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作地点',
  `oper_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '请求参数',
  `json_result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '返回结果',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '操作状态，1成功0失败',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '错误消息',
  `cost_time` bigint NULL DEFAULT 0 COMMENT '耗时，单位毫秒',
  `oper_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`oper_log_id`) USING BTREE,
  INDEX `idx_title`(`title` ASC) USING BTREE,
  INDEX `idx_business_type`(`business_type` ASC) USING BTREE,
  INDEX `idx_oper_name`(`oper_name` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_oper_time`(`oper_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_oper_log
-- ----------------------------
INSERT INTO `blog_oper_log` VALUES (1, '认证管理', 'OTHER', 'com.xy.blog.controller.AuthController.sendEmailCode()', 'POST', 'anonymousUser', '/auth/email/code', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"k8qlqi.qxv78@sohu.com\",\"scene\":\"LOGIN\"}]', NULL, '0', '该邮箱未注册', 258, '2026-03-24 15:43:11');
INSERT INTO `blog_oper_log` VALUES (2, '认证管理', 'OTHER', 'com.xy.blog.controller.AuthController.sendEmailCode()', 'POST', 'anonymousUser', '/auth/email/code', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"k5qgs2_i63@yahoo.cn\",\"scene\":\"REGISTER\"}]', NULL, '1', '', 673, '2026-03-24 15:43:24');
INSERT INTO `blog_oper_log` VALUES (3, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.loginByEmail()', 'POST', 'anonymousUser', '/auth/login/email', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"kgs_g3880@sina.com\",\"code\":\"******\"}]', NULL, '0', '该邮箱未注册', 32, '2026-03-24 15:44:36');
INSERT INTO `blog_oper_log` VALUES (4, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.loginByEmail()', 'POST', 'anonymousUser', '/auth/login/email', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"kgs_g3880@sina.com\",\"code\":\"******\"}]', NULL, '0', '该邮箱未注册', 25, '2026-03-24 15:46:50');
INSERT INTO `blog_oper_log` VALUES (5, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.loginByEmail()', 'POST', 'anonymousUser', '/auth/login/email', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"kgs_g3880@sina.com\",\"code\":\"******\"}]', NULL, '0', '该邮箱未注册', 244, '2026-03-24 15:47:42');
INSERT INTO `blog_oper_log` VALUES (6, '认证管理', 'OTHER', 'com.xy.blog.controller.AuthController.sendEmailCode()', 'POST', 'anonymousUser', '/auth/email/code', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"k5qgs2_i63@yahoo.cn\",\"scene\":\"REGISTER\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":\"验证码发送成功\"}', '1', '', 974, '2026-03-24 15:47:57');
INSERT INTO `blog_oper_log` VALUES (7, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.loginByEmail()', 'POST', 'anonymousUser', '/auth/login/email', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"kgs_g3880@sina.com\",\"code\":\"******\"}]', '{\"code\":500,\"msg\":\"该邮箱未注册\",\"data\":null}', '0', '该邮箱未注册', 23, '2026-03-24 15:53:55');
INSERT INTO `blog_oper_log` VALUES (8, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.loginByEmail()', 'POST', 'anonymousUser', '/auth/login/email', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"kgs_g3880@sina.com\",\"code\":\"******\"}]', '{\"code\":500,\"msg\":\"该邮箱未注册\",\"data\":null}', '0', '该邮箱未注册', 289, '2026-03-24 16:05:46');
INSERT INTO `blog_oper_log` VALUES (9, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'anonymousUser', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"root\",\"password\":\"******\",\"captchaKey\":\"15584d09dcbc4237bea30ce01f66802f\",\"captchaCode\":\"******\"}]', '{\"code\":500,\"msg\":\"The specified key byte array is 104 bits which is not secure enough for any JWT HMAC-SHA algorithm.  The JWT JWA Specification (RFC 7518, Section 3.2) states that keys used with HMAC-SHA algorithms MUST have a size >= 256 bits (the key size must be greater than or equal to the hash output size).  Consider using the Jwts.SIG.HS256.key() builder (or HS384.key() or HS512.key()) to create a key guaranteed to be secure enough for your preferred HMAC-SHA algorithm.  See https://tools.ietf.org/html/rfc7518#section-3.2 for more information.\",\"data\":null}', '0', 'The specified key byte array is 104 bits which is not secure enough for any JWT HMAC-SHA algorithm.  The JWT JWA Specification (RFC 7518, Section 3.2) states that keys used with HMAC-SHA algorithms MUST have a size >= 256 bits (the key size must be greater than or equal to the hash output size).  Consider using the Jwts.SIG.HS256.key() builder (or HS384.key() or HS512.key()) to create a key guaranteed to be secure enough for your preferred HMAC-SHA algorithm.  See https://tools.ietf.org/html/rfc7518#section-3.2 for more information.', 380, '2026-03-24 18:40:59');
INSERT INTO `blog_oper_log` VALUES (10, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'anonymousUser', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"root\",\"password\":\"******\",\"captchaKey\":\"15584d09dcbc4237bea30ce01f66802f\",\"captchaCode\":\"******\"}]', '{\"code\":500,\"msg\":\"验证码不存在或已过期\",\"data\":null}', '0', '验证码不存在或已过期', 700, '2026-03-24 18:43:55');
INSERT INTO `blog_oper_log` VALUES (11, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'anonymousUser', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"root\",\"password\":\"******\",\"captchaKey\":\"15584d09dcbc4237bea30ce01f66802f\",\"captchaCode\":\"******\"}]', '{\"code\":500,\"msg\":\"验证码不存在或已过期\",\"data\":null}', '0', '验证码不存在或已过期', 70, '2026-03-24 18:44:22');
INSERT INTO `blog_oper_log` VALUES (12, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'root', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"root\",\"password\":\"******\",\"captchaKey\":\"35bbea5207844bb281ceccede8189aa2\",\"captchaCode\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"token\":\"******\",\"header\":\"Authorization\",\"prefix\":\"Bearer \",\"expireTime\":360,\"userName\":\"root\"}}', '1', '', 783, '2026-03-24 18:44:36');
INSERT INTO `blog_oper_log` VALUES (13, '认证管理', 'OTHER', 'com.xy.blog.controller.AuthController.sendEmailCode()', 'POST', 'anonymousUser', '/auth/email/code', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"489572770@qq.com\",\"scene\":\"LOGIN\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":\"验证码发送成功\"}', '1', '', 982, '2026-03-24 20:31:26');
INSERT INTO `blog_oper_log` VALUES (14, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.loginByEmail()', 'POST', 'root', '/auth/login/email', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"489572770@qq.com\",\"code\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"token\":\"******\",\"header\":\"Authorization\",\"prefix\":\"Bearer \",\"expireTime\":360,\"userName\":\"root\"}}', '1', '', 181, '2026-03-24 20:31:57');
INSERT INTO `blog_oper_log` VALUES (15, '认证管理', 'OTHER', 'com.xy.blog.controller.AuthController.sendResetPasswordCode()', 'POST', 'root', '/auth/password/code', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"4895727701@qq.com\"}]', '{\"code\":500,\"msg\":\"该邮箱未注册\",\"data\":null}', '0', '该邮箱未注册', 9, '2026-03-24 20:34:39');
INSERT INTO `blog_oper_log` VALUES (16, '认证管理', 'OTHER', 'com.xy.blog.controller.AuthController.sendResetPasswordCode()', 'POST', 'root', '/auth/password/code', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"489572770@qq.com\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":\"找回密码验证码发送成功\"}', '1', '', 13, '2026-03-24 20:34:43');
INSERT INTO `blog_oper_log` VALUES (17, '认证管理', 'OTHER', 'com.xy.blog.controller.AuthController.sendEmailCode()', 'POST', 'anonymousUser', '/auth/email/code', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"489572770@qq.com\",\"scene\":\"LOGIN\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":\"验证码发送成功\"}', '1', '', 258, '2026-03-24 22:44:37');
INSERT INTO `blog_oper_log` VALUES (18, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.loginByEmail()', 'POST', 'root', '/auth/login/email', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"489572770@qq.com\",\"code\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"token\":\"******\",\"header\":\"Authorization\",\"prefix\":\"Bearer \",\"expireTime\":360,\"userName\":\"root\"}}', '1', '', 52, '2026-03-24 22:45:09');
INSERT INTO `blog_oper_log` VALUES (19, '认证管理', 'OTHER', 'com.xy.blog.controller.AuthController.sendEmailCode()', 'POST', 'anonymousUser', '/auth/email/code', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"489572770@qq.com\",\"scene\":\"LOGIN\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":\"验证码发送成功\"}', '1', '', 1190, '2026-03-25 09:49:02');
INSERT INTO `blog_oper_log` VALUES (20, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.loginByEmail()', 'POST', 'root', '/auth/login/email', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"489572770@qq.com\",\"code\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"token\":\"******\",\"header\":\"Authorization\",\"prefix\":\"Bearer \",\"expireTime\":360,\"userName\":\"root\"}}', '1', '', 60, '2026-03-25 09:49:31');
INSERT INTO `blog_oper_log` VALUES (21, '文件上传', 'OTHER', 'com.xy.blog.controller.common.OssController.uploadAvatar()', 'POST', 'root', '/common/oss/upload/avatar', '0:0:0:0:0:0:0:1', '', NULL, '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"url\":\"https://ncbc-ai.oss-cn-beijing.aliyuncs.com/avatar/2026/03/25/625e1e645d264177b0dfa3eac6f946a5.png\",\"fileName\":\"avatar/2026/03/25/625e1e645d264177b0dfa3eac6f946a5.png\",\"originalName\":\"admin.png\",\"contentType\":\"image/png\",\"size\":66364}}', '1', '', 456, '2026-03-25 09:50:01');
INSERT INTO `blog_oper_log` VALUES (22, '个人中心', 'UPDATE', 'com.xy.blog.controller.system.SysUserProfileController.updateAvatar()', 'PUT', 'root', '/system/user/profile/avatar', '0:0:0:0:0:0:0:1', '', '[{\"avatar\":\"https://ncbc-ai.oss-cn-beijing.aliyuncs.com/avatar/2026/03/25/625e1e645d264177b0dfa3eac6f946a5.png\"}]', '', '1', '', 25, '2026-03-25 09:53:52');
INSERT INTO `blog_oper_log` VALUES (23, '用户管理', 'OTHER', 'com.xy.blog.controller.system.SysUserController.getInfo()', 'GET', 'root', '/system/user/1', '0:0:0:0:0:0:0:1', '', '[1]', '', '1', '', 10, '2026-03-25 09:54:57');
INSERT INTO `blog_oper_log` VALUES (24, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'root', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"root\",\"password\":\"******\",\"captchaKey\":\"4c3136ebef184cee8f09e53958d3ffc2\",\"captchaCode\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"token\":\"******\",\"header\":\"Authorization\",\"prefix\":\"Bearer \",\"expireTime\":360,\"userName\":\"root\"}}', '1', '', 835, '2026-03-26 00:02:37');
INSERT INTO `blog_oper_log` VALUES (25, '文件上传', 'OTHER', 'com.xy.blog.controller.common.OssController.uploadAvatar()', 'POST', 'root', '/common/oss/upload/avatar', '0:0:0:0:0:0:0:1', '', NULL, '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"url\":\"https://ncbc-ai.oss-cn-beijing.aliyuncs.com/avatar/2026/03/26/024989c622d5429f88695cb5b9771d2c.jpg\",\"fileName\":\"avatar/2026/03/26/024989c622d5429f88695cb5b9771d2c.jpg\",\"originalName\":\"测试.jpg\",\"contentType\":\"image/jpeg\",\"size\":35703}}', '1', '', 470, '2026-03-26 00:10:17');
INSERT INTO `blog_oper_log` VALUES (26, '个人中心', 'UPDATE', 'com.xy.blog.controller.system.SysUserProfileController.updateAvatar()', 'PUT', 'root', '/system/user/profile/avatar', '0:0:0:0:0:0:0:1', '', '[{\"avatar\":\"https://ncbc-ai.oss-cn-beijing.aliyuncs.com/avatar/2026/03/26/024989c622d5429f88695cb5b9771d2c.jpg\"}]', '', '1', '', 29, '2026-03-26 00:10:18');
INSERT INTO `blog_oper_log` VALUES (27, '认证管理', 'OTHER', 'com.xy.blog.controller.AuthController.sendEmailCode()', 'POST', 'anonymousUser', '/auth/email/code', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"489572770@qq.com\",\"scene\":\"LOGIN\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":\"验证码发送成功\"}', '1', '', 27, '2026-03-26 00:12:36');
INSERT INTO `blog_oper_log` VALUES (28, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.loginByEmail()', 'POST', 'root', '/auth/login/email', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"489572770@qq.com\",\"code\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"token\":\"******\",\"header\":\"Authorization\",\"prefix\":\"Bearer \",\"expireTime\":360,\"userName\":\"root\"}}', '1', '', 17, '2026-03-26 00:13:10');
INSERT INTO `blog_oper_log` VALUES (29, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'root', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"root\",\"password\":\"******\",\"captchaKey\":\"caaae00ea1c14329860c1e1201844c63\",\"captchaCode\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"token\":\"******\",\"header\":\"Authorization\",\"prefix\":\"Bearer \",\"expireTime\":360,\"userName\":\"root\"}}', '1', '', 190, '2026-03-26 00:27:30');
INSERT INTO `blog_oper_log` VALUES (30, '个人中心', 'UPDATE', 'com.xy.blog.controller.system.SysUserProfileController.updateProfile()', 'PUT', 'root', '/system/user/profile', '0:0:0:0:0:0:0:1', '', '[{\"nickName\":\"闫一诺\",\"email\":\"489572770@qq.com\",\"phonenumber\":\"14441378646\",\"sex\":\"1\"}]', '', '1', '', 27, '2026-03-26 00:28:18');
INSERT INTO `blog_oper_log` VALUES (31, '认证管理', 'LOGOUT', 'com.xy.blog.controller.AuthController.logout()', 'POST', 'anonymousUser', '/auth/logout', '0:0:0:0:0:0:0:1', '', NULL, '{\"code\":500,\"msg\":\"当前未登录或登录状态已失效\",\"data\":null}', '0', '当前未登录或登录状态已失效', 0, '2026-03-26 00:28:50');
INSERT INTO `blog_oper_log` VALUES (32, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'root', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"ROOT\",\"password\":\"******\",\"captchaKey\":\"4d1f28bbeec545dc87b331f58ae9e0ca\",\"captchaCode\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"token\":\"******\",\"header\":\"Authorization\",\"prefix\":\"Bearer \",\"expireTime\":360,\"userName\":\"root\"}}', '1', '', 165, '2026-03-26 00:29:27');
INSERT INTO `blog_oper_log` VALUES (33, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'anonymousUser', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"roo\",\"password\":\"******\",\"captchaKey\":\"1ecd0aed67c24478af536b4c017e228e\",\"captchaCode\":\"******\"}]', '{\"code\":500,\"msg\":\"用户名或密码错误\",\"data\":null}', '0', '用户名或密码错误', 57, '2026-03-26 00:31:45');
INSERT INTO `blog_oper_log` VALUES (34, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'anonymousUser', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"roots\",\"password\":\"******\",\"captchaKey\":\"b4680f2c1b144d70964200e58eeb75d0\",\"captchaCode\":\"******\"}]', '{\"code\":500,\"msg\":\"用户名或密码错误\",\"data\":null}', '0', '用户名或密码错误', 17, '2026-03-26 00:31:59');
INSERT INTO `blog_oper_log` VALUES (35, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'root', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"rooT\",\"password\":\"******\",\"captchaKey\":\"ac193c14a6484f9280f96b58381795db\",\"captchaCode\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"token\":\"******\",\"header\":\"Authorization\",\"prefix\":\"Bearer \",\"expireTime\":360,\"userName\":\"root\"}}', '1', '', 157, '2026-03-26 00:32:11');
INSERT INTO `blog_oper_log` VALUES (36, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'root', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"root\",\"password\":\"******\",\"captchaKey\":\"62ff65fdfc684c558d096e1a0a7323a2\",\"captchaCode\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"token\":\"******\",\"header\":\"Authorization\",\"prefix\":\"Bearer \",\"expireTime\":360,\"userName\":\"root\"}}', '1', '', 198, '2026-03-26 00:46:58');
INSERT INTO `blog_oper_log` VALUES (37, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'root', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"root\",\"password\":\"******\",\"captchaKey\":\"c7ef729027ae474db1dc381b5fcac279\",\"captchaCode\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"token\":\"******\",\"header\":\"Authorization\",\"prefix\":\"Bearer \",\"expireTime\":360,\"userName\":\"root\"}}', '1', '', 171, '2026-03-26 00:48:46');
INSERT INTO `blog_oper_log` VALUES (38, '认证管理', 'LOGOUT', 'com.xy.blog.controller.AuthController.logout()', 'POST', 'anonymousUser', '/auth/logout', '0:0:0:0:0:0:0:1', '', NULL, '{\"code\":500,\"msg\":\"当前未登录或登录状态已失效\",\"data\":null}', '0', '当前未登录或登录状态已失效', 0, '2026-03-26 00:50:03');
INSERT INTO `blog_oper_log` VALUES (39, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'root', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"root\",\"password\":\"******\",\"captchaKey\":\"062715a954a94c859e605887d2a11bd4\",\"captchaCode\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"token\":\"******\",\"header\":\"Authorization\",\"prefix\":\"Bearer \",\"expireTime\":360,\"userName\":\"root\"}}', '1', '', 172, '2026-03-26 00:51:32');
INSERT INTO `blog_oper_log` VALUES (40, '认证管理', 'LOGOUT', 'com.xy.blog.controller.AuthController.logout()', 'POST', 'anonymous', '/auth/logout', '0:0:0:0:0:0:0:1', '', NULL, '{\"code\":200,\"msg\":\"操作成功\",\"data\":\"退出登录成功\"}', '1', '', 2, '2026-03-26 00:54:33');
INSERT INTO `blog_oper_log` VALUES (41, '认证管理', 'OTHER', 'com.xy.blog.controller.AuthController.sendEmailCode()', 'POST', 'anonymousUser', '/auth/email/code', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"12312@qq.com\",\"scene\":\"REGISTER\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":\"验证码发送成功\"}', '1', '', 10, '2026-03-26 00:54:55');
INSERT INTO `blog_oper_log` VALUES (42, '认证管理', 'INSERT', 'com.xy.blog.controller.AuthController.register()', 'POST', 'anonymousUser', '/auth/register', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"ROOT\",\"nickName\":\"123\",\"email\":\"12312@qq.com\",\"phonenumber\":null,\"password\":\"******\",\"code\":\"******\"}]', '{\"code\":500,\"msg\":\"用户名已存在\",\"data\":null}', '0', '用户名已存在', 120, '2026-03-26 00:55:43');
INSERT INTO `blog_oper_log` VALUES (43, '认证管理', 'INSERT', 'com.xy.blog.controller.AuthController.register()', 'POST', 'anonymousUser', '/auth/register', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"ROOT\",\"nickName\":\"123\",\"email\":\"12312@qq.com\",\"phonenumber\":null,\"password\":\"******\",\"code\":\"******\"}]', '{\"code\":500,\"msg\":\"邮箱验证码不存在或已过期\",\"data\":null}', '0', '邮箱验证码不存在或已过期', 947, '2026-03-26 00:58:49');
INSERT INTO `blog_oper_log` VALUES (44, '认证管理', 'OTHER', 'com.xy.blog.controller.AuthController.sendEmailCode()', 'POST', 'anonymousUser', '/auth/email/code', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"12312@qq.com\",\"scene\":\"REGISTER\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":\"验证码发送成功\"}', '1', '', 135, '2026-03-26 00:58:54');
INSERT INTO `blog_oper_log` VALUES (45, '认证管理', 'INSERT', 'com.xy.blog.controller.AuthController.register()', 'POST', 'anonymousUser', '/auth/register', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"ROOT\",\"nickName\":\"123\",\"email\":\"12312@qq.com\",\"phonenumber\":null,\"password\":\"******\",\"code\":\"******\"}]', '{\"code\":500,\"msg\":\"角色不能为空\",\"data\":null}', '0', '角色不能为空', 70, '2026-03-26 00:59:27');
INSERT INTO `blog_oper_log` VALUES (46, '认证管理', 'INSERT', 'com.xy.blog.controller.AuthController.register()', 'POST', 'anonymousUser', '/auth/register', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"ROOT\",\"nickName\":\"123\",\"email\":\"12312@qq.com\",\"phonenumber\":null,\"password\":\"******\",\"code\":\"******\"}]', '{\"code\":500,\"msg\":\"邮箱验证码不存在或已过期\",\"data\":null}', '0', '邮箱验证码不存在或已过期', 1, '2026-03-26 00:59:29');
INSERT INTO `blog_oper_log` VALUES (47, '认证管理', 'INSERT', 'com.xy.blog.controller.AuthController.register()', 'POST', 'anonymousUser', '/auth/register', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"ROOT\",\"nickName\":\"123\",\"email\":\"12312@qq.com\",\"phonenumber\":null,\"password\":\"******\",\"code\":\"******\"}]', '{\"code\":500,\"msg\":\"邮箱验证码不存在或已过期\",\"data\":null}', '0', '邮箱验证码不存在或已过期', 1, '2026-03-26 00:59:30');
INSERT INTO `blog_oper_log` VALUES (48, '认证管理', 'OTHER', 'com.xy.blog.controller.AuthController.sendEmailCode()', 'POST', 'anonymousUser', '/auth/email/code', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"12312@qq.com\",\"scene\":\"REGISTER\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":\"验证码发送成功\"}', '1', '', 763, '2026-03-26 01:05:14');
INSERT INTO `blog_oper_log` VALUES (49, '认证管理', 'INSERT', 'com.xy.blog.controller.AuthController.register()', 'POST', 'anonymousUser', '/auth/register', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"ROOT\",\"nickName\":\"123\",\"email\":\"12312@qq.com\",\"phonenumber\":null,\"password\":\"******\",\"code\":\"******\"}]', '{\"code\":500,\"msg\":\"\\r\\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'ROOT\' for key \'blog_user.uk_blog_user_name\'\\r\\n### The error may exist in com/xy/blog/system/mapper/BlogUserMapper.java (best guess)\\r\\n### The error may involve com.xy.blog.system.mapper.BlogUserMapper.insert-Inline\\r\\n### The error occurred while setting parameters\\r\\n### SQL: INSERT INTO blog_user  ( user_name, nick_name, email,  password,   status, del_flag,       create_by, create_time, update_by, update_time )  VALUES (  ?, ?, ?,  ?,   ?, ?,       ?, ?, ?, ?  )\\r\\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'ROOT\' for key \'blog_user.uk_blog_user_name\'\\n; Duplicate entry \'ROOT\' for key \'blog_user.uk_blog_user_name\'\",\"data\":null}', '0', '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'ROOT\' for key \'blog_user.uk_blog_user_name\'\r\n### The error may exist in com/xy/blog/system/mapper/BlogUserMapper.java (best guess)\r\n### The error may involve com.xy.blog.system.mapper.BlogUserMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO blog_user  ( user_name, nick_name, email,  password,   status, del_flag,       create_by, create_time, update_by, update_time )  VALUES (  ?, ?, ?,  ?,   ?, ?,       ?, ?, ?, ?  )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'ROOT\' for key \'blog_user.uk_blog_user_name\'\n; Duplicate entry \'ROOT\' for key \'blog_user.uk_blog_user_name\'', 1577, '2026-03-26 01:05:34');
INSERT INTO `blog_oper_log` VALUES (50, '认证管理', 'INSERT', 'com.xy.blog.controller.AuthController.register()', 'POST', 'anonymousUser', '/auth/register', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"ROOT\",\"nickName\":\"123\",\"email\":\"12312@qq.com\",\"phonenumber\":null,\"password\":\"******\",\"code\":\"******\"}]', '{\"code\":500,\"msg\":\"邮箱验证码不存在或已过期\",\"data\":null}', '0', '邮箱验证码不存在或已过期', 16, '2026-03-26 01:08:19');
INSERT INTO `blog_oper_log` VALUES (51, '认证管理', 'OTHER', 'com.xy.blog.controller.AuthController.sendEmailCode()', 'POST', 'anonymousUser', '/auth/email/code', '0:0:0:0:0:0:0:1', '', '[{\"email\":\"12312@qq.com\",\"scene\":\"REGISTER\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":\"验证码发送成功\"}', '1', '', 17, '2026-03-26 01:08:21');
INSERT INTO `blog_oper_log` VALUES (52, '认证管理', 'INSERT', 'com.xy.blog.controller.AuthController.register()', 'POST', 'ROOT', '/auth/register', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"ROOT\",\"nickName\":\"123\",\"email\":\"12312@qq.com\",\"phonenumber\":null,\"password\":\"******\",\"code\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"userId\":3,\"userName\":\"ROOT\",\"nickName\":\"123\",\"email\":\"12312@qq.com\",\"phonenumber\":null,\"avatar\":null,\"status\":\"1\"}}', '1', '', 236, '2026-03-26 01:08:41');
INSERT INTO `blog_oper_log` VALUES (53, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'ROOT', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"ROOT\",\"password\":\"******\",\"captchaKey\":\"511850b0a63a48e7808e08e211d16006\",\"captchaCode\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"token\":\"******\",\"header\":\"Authorization\",\"prefix\":\"Bearer \",\"expireTime\":360,\"userName\":\"ROOT\"}}', '1', '', 373, '2026-03-26 01:09:03');
INSERT INTO `blog_oper_log` VALUES (54, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'root', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"root\",\"password\":\"******\",\"captchaKey\":\"82b00566a0574af48d9eae85494df311\",\"captchaCode\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"token\":\"******\",\"header\":\"Authorization\",\"prefix\":\"Bearer \",\"expireTime\":360,\"userName\":\"root\"}}', '1', '', 510, '2026-03-26 15:19:34');
INSERT INTO `blog_oper_log` VALUES (55, '个人中心', 'UPDATE', 'com.xy.blog.controller.system.SysUserProfileController.updateProfile()', 'PUT', 'root', '/system/user/profile', '0:0:0:0:0:0:0:1', '', '[{\"nickName\":\"渊\",\"email\":\"489572770@qq.com\",\"phonenumber\":\"14441378646\",\"sex\":\"1\"}]', '', '1', '', 72, '2026-03-26 15:19:58');
INSERT INTO `blog_oper_log` VALUES (56, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'root', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"root\",\"password\":\"******\",\"captchaKey\":\"89e74875a14f4bb0acb3493436f419ed\",\"captchaCode\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"token\":\"******\",\"header\":\"Authorization\",\"prefix\":\"Bearer \",\"expireTime\":360,\"userName\":\"root\"}}', '1', '', 272, '2026-03-26 18:45:24');
INSERT INTO `blog_oper_log` VALUES (57, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'root', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"root\",\"password\":\"******\",\"captchaKey\":\"4f1f658644934403be4f04e8aeb8490a\",\"captchaCode\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"token\":\"******\",\"header\":\"Authorization\",\"prefix\":\"Bearer \",\"expireTime\":360,\"userName\":\"root\"}}', '1', '', 439, '2026-03-26 20:04:33');
INSERT INTO `blog_oper_log` VALUES (58, '认证管理', 'LOGIN', 'com.xy.blog.controller.AuthController.login()', 'POST', 'root', '/auth/login', '0:0:0:0:0:0:0:1', '', '[{\"userName\":\"root\",\"password\":\"******\",\"captchaKey\":\"8e791ec710024b8c9b70cfca69fb0260\",\"captchaCode\":\"******\"}]', '{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"token\":\"******\",\"header\":\"Authorization\",\"prefix\":\"Bearer \",\"expireTime\":360,\"userName\":\"root\"}}', '1', '', 192, '2026-03-27 17:09:45');

-- ----------------------------
-- Table structure for blog_role
-- ----------------------------
DROP TABLE IF EXISTS `blog_role`;
CREATE TABLE `blog_role`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符',
  `role_sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '角色状态（0停用 1正常）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0存在 1删除）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `uk_blog_role_key`(`role_key` ASC) USING BTREE COMMENT '角色权限字符唯一索引',
  INDEX `idx_blog_role_status`(`status` ASC) USING BTREE COMMENT '角色状态索引'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '博客角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_role
-- ----------------------------
INSERT INTO `blog_role` VALUES (1, '超级管理员', 'admin', 1, '1', '0', '拥有后台全部管理权限', NULL, '2026-03-24 23:05:14', NULL, '2026-03-24 23:05:14');
INSERT INTO `blog_role` VALUES (2, '内容编辑', 'editor', 2, '1', '0', '负责文章、分类、标签和评论等内容管理', NULL, '2026-03-24 23:05:14', NULL, '2026-03-24 23:05:14');
INSERT INTO `blog_role` VALUES (3, '普通作者', 'author', 3, '1', '0', '负责维护自己的文章和个人资料', NULL, '2026-03-24 23:05:14', NULL, '2026-03-24 23:05:14');

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag`  (
  `tag_id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `tag_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名称',
  `sort_num` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '标签状态（0停用 1正常）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`tag_id`) USING BTREE,
  UNIQUE INDEX `uk_blog_tag_name`(`tag_name` ASC) USING BTREE COMMENT '标签名称唯一索引',
  INDEX `idx_blog_tag_status`(`status` ASC) USING BTREE COMMENT '标签状态索引'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_tag
-- ----------------------------
INSERT INTO `blog_tag` VALUES (1, 'Java', 1, '1', 'Java ??', 'system', '2026-03-26 16:37:57', NULL, '2026-03-26 16:37:57');
INSERT INTO `blog_tag` VALUES (2, 'Spring Boot', 2, '1', 'Spring Boot ??', 'system', '2026-03-26 16:37:57', NULL, '2026-03-26 16:37:57');
INSERT INTO `blog_tag` VALUES (3, 'Redis', 3, '1', 'Redis ??', 'system', '2026-03-26 16:37:57', NULL, '2026-03-26 16:37:57');

-- ----------------------------
-- Table structure for blog_user
-- ----------------------------
DROP TABLE IF EXISTS `blog_user`;
CREATE TABLE `blog_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '登录账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `phonenumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '用户性别（0未知 1男 2女）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '账号状态（0停用 1正常）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0存在 1删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `wx_openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信OpenID',
  `wx_unionid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信UnionID',
  `wx_nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信昵称',
  `wx_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信头像',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `uk_blog_user_name`(`user_name` ASC) USING BTREE COMMENT '登录账号唯一索引',
  UNIQUE INDEX `uk_blog_user_email`(`email` ASC) USING BTREE COMMENT '用户邮箱唯一索引',
  UNIQUE INDEX `uk_blog_user_wx_openid`(`wx_openid` ASC) USING BTREE COMMENT '微信OpenID唯一索引',
  INDEX `idx_blog_user_phone`(`phonenumber` ASC) USING BTREE COMMENT '手机号索引',
  INDEX `idx_blog_user_status`(`status` ASC) USING BTREE COMMENT '账号状态索引'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '博客用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_user
-- ----------------------------
INSERT INTO `blog_user` VALUES (1, 'root', '渊', '489572770@qq.com', '14441378646', '$2a$10$cJKnHAfULYWTHcAhQF9l6OYrgpoiO8pqiCWGyCC.gueTTZIJY/Cti', 'https://ncbc-ai.oss-cn-beijing.aliyuncs.com/avatar/2026/03/26/024989c622d5429f88695cb5b9771d2c.jpg', '1', '1', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'system', '2026-03-24 02:31:03', 'system', '2026-03-24 15:55:57');
INSERT INTO `blog_user` VALUES (3, 'ROOT', '123', '12312@qq.com', NULL, '$2a$10$4OxjR/gq06r4MjAlGDJ6/eYR9CQCWn.SIIxevAVuB9.xQmnnqjzo.', NULL, '0', '1', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'system', '2026-03-26 01:08:41', 'system', '2026-03-26 01:08:41');

-- ----------------------------
-- Table structure for blog_user_role
-- ----------------------------
DROP TABLE IF EXISTS `blog_user_role`;
CREATE TABLE `blog_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_blog_user_role`(`user_id` ASC, `role_id` ASC) USING BTREE COMMENT '用户角色唯一索引',
  INDEX `idx_blog_user_role_user_id`(`user_id` ASC) USING BTREE COMMENT '用户索引',
  INDEX `idx_blog_user_role_role_id`(`role_id` ASC) USING BTREE COMMENT '角色索引'
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '博客用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_user_role
-- ----------------------------
INSERT INTO `blog_user_role` VALUES (1, 1, 1, '2026-03-24 23:07:02');
INSERT INTO `blog_user_role` VALUES (2, 3, 3, '2026-03-26 01:08:41');

SET FOREIGN_KEY_CHECKS = 1;
