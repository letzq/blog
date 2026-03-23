-- Blog base schema
-- MySQL 8.0+

CREATE TABLE IF NOT EXISTS `blog_user` (
  `user_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` VARCHAR(30) NOT NULL COMMENT '登录账号',
  `nick_name` VARCHAR(30) NOT NULL COMMENT '用户昵称',
  `email` VARCHAR(50) DEFAULT NULL COMMENT '用户邮箱',
  `phonenumber` VARCHAR(20) DEFAULT NULL COMMENT '手机号码',
  `password` VARCHAR(100) NOT NULL COMMENT '登录密码',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像地址',
  `sex` CHAR(1) DEFAULT '0' COMMENT '用户性别（0未知 1男 2女）',
  `status` CHAR(1) NOT NULL DEFAULT '1' COMMENT '账号状态（0停用 1正常）',
  `del_flag` CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0存在 1删除）',
  `login_ip` VARCHAR(128) DEFAULT NULL COMMENT '最后登录IP',
  `login_date` DATETIME DEFAULT NULL COMMENT '最后登录时间',
  `wx_openid` VARCHAR(64) DEFAULT NULL COMMENT '微信OpenID',
  `wx_unionid` VARCHAR(64) DEFAULT NULL COMMENT '微信UnionID',
  `wx_nickname` VARCHAR(100) DEFAULT NULL COMMENT '微信昵称',
  `wx_avatar` VARCHAR(255) DEFAULT NULL COMMENT '微信头像',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `create_by` VARCHAR(64) DEFAULT NULL COMMENT '创建者',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT NULL COMMENT '更新者',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_blog_user_name` (`user_name`) COMMENT '登录账号唯一索引',
  UNIQUE KEY `uk_blog_user_email` (`email`) COMMENT '用户邮箱唯一索引',
  UNIQUE KEY `uk_blog_user_wx_openid` (`wx_openid`) COMMENT '微信OpenID唯一索引',
  KEY `idx_blog_user_phone` (`phonenumber`) COMMENT '手机号索引',
  KEY `idx_blog_user_status` (`status`) COMMENT '账号状态索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='博客用户表';


CREATE TABLE IF NOT EXISTS `blog_role` (
  `role_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` VARCHAR(30) NOT NULL COMMENT '角色名称',
  `role_key` VARCHAR(100) NOT NULL COMMENT '角色权限字符',
  `role_sort` INT NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `status` CHAR(1) NOT NULL DEFAULT '1' COMMENT '角色状态（0停用 1正常）',
  `del_flag` CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0存在 1删除）',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `create_by` VARCHAR(64) DEFAULT NULL COMMENT '创建者',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT NULL COMMENT '更新者',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `uk_blog_role_key` (`role_key`) COMMENT '角色权限字符唯一索引',
  KEY `idx_blog_role_status` (`status`) COMMENT '角色状态索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='博客角色表';


CREATE TABLE IF NOT EXISTS `blog_user_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_blog_user_role` (`user_id`, `role_id`) COMMENT '用户角色唯一索引',
  KEY `idx_blog_user_role_user_id` (`user_id`) COMMENT '用户索引',
  KEY `idx_blog_user_role_role_id` (`role_id`) COMMENT '角色索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='博客用户角色关联表';


CREATE TABLE IF NOT EXISTS `blog_article_category` (
  `category_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_name` VARCHAR(50) NOT NULL COMMENT '分类名称',
  `parent_id` BIGINT NOT NULL DEFAULT 0 COMMENT '父分类ID',
  `ancestors` VARCHAR(500) DEFAULT '' COMMENT '祖级列表',
  `sort_num` INT NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `status` CHAR(1) NOT NULL DEFAULT '1' COMMENT '分类状态（0停用 1正常）',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `create_by` VARCHAR(64) DEFAULT NULL COMMENT '创建者',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT NULL COMMENT '更新者',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `uk_blog_article_category_name` (`category_name`) COMMENT '分类名称唯一索引',
  KEY `idx_blog_article_category_parent_id` (`parent_id`) COMMENT '父分类索引',
  KEY `idx_blog_article_category_status` (`status`) COMMENT '分类状态索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章分类表';


CREATE TABLE IF NOT EXISTS `blog_article` (
  `article_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `category_id` BIGINT DEFAULT NULL COMMENT '分类ID',
  `user_id` BIGINT NOT NULL COMMENT '作者用户ID',
  `title` VARCHAR(200) NOT NULL COMMENT '文章标题',
  `summary` VARCHAR(500) DEFAULT NULL COMMENT '文章摘要',
  `cover_image` VARCHAR(255) DEFAULT NULL COMMENT '封面图地址',
  `content_md` LONGTEXT NOT NULL COMMENT 'Markdown内容',
  `is_top` CHAR(1) NOT NULL DEFAULT '0' COMMENT '是否置顶（0否 1是）',
  `is_original` CHAR(1) NOT NULL DEFAULT '1' COMMENT '是否原创（0转载 1原创）',
  `original_url` VARCHAR(255) DEFAULT NULL COMMENT '原文地址',
  `allow_comment` CHAR(1) NOT NULL DEFAULT '1' COMMENT '是否允许评论（0否 1是）',
  `status` CHAR(1) NOT NULL DEFAULT '0' COMMENT '文章状态（0草稿 1发布 2下线）',
  `view_count` BIGINT NOT NULL DEFAULT 0 COMMENT '浏览量',
  `like_count` BIGINT NOT NULL DEFAULT 0 COMMENT '点赞数',
  `publish_time` DATETIME DEFAULT NULL COMMENT '发布时间',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `create_by` VARCHAR(64) DEFAULT NULL COMMENT '创建者',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT NULL COMMENT '更新者',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`article_id`),
  KEY `idx_blog_article_category_id` (`category_id`) COMMENT '分类索引',
  KEY `idx_blog_article_user_id` (`user_id`) COMMENT '作者索引',
  KEY `idx_blog_article_status` (`status`) COMMENT '文章状态索引',
  KEY `idx_blog_article_publish_time` (`publish_time`) COMMENT '发布时间索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章表';


CREATE TABLE IF NOT EXISTS `blog_tag` (
  `tag_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `tag_name` VARCHAR(50) NOT NULL COMMENT '标签名称',
  `sort_num` INT NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `status` CHAR(1) NOT NULL DEFAULT '1' COMMENT '标签状态（0停用 1正常）',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `create_by` VARCHAR(64) DEFAULT NULL COMMENT '创建者',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT NULL COMMENT '更新者',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`tag_id`),
  UNIQUE KEY `uk_blog_tag_name` (`tag_name`) COMMENT '标签名称唯一索引',
  KEY `idx_blog_tag_status` (`status`) COMMENT '标签状态索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章标签表';


CREATE TABLE IF NOT EXISTS `blog_article_tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `article_id` BIGINT NOT NULL COMMENT '文章ID',
  `tag_id` BIGINT NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_blog_article_tag` (`article_id`, `tag_id`) COMMENT '文章标签唯一索引',
  KEY `idx_blog_article_tag_article_id` (`article_id`) COMMENT '文章索引',
  KEY `idx_blog_article_tag_tag_id` (`tag_id`) COMMENT '标签索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章标签关联表';
