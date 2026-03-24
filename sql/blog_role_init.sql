-- 博客后台角色初始化数据
-- 建议在 blog_role 和 blog_user_role 表创建完成后执行

INSERT INTO `blog_role` (`role_name`, `role_key`, `role_sort`, `status`, `del_flag`, `remark`)
VALUES
('超级管理员', 'admin', 1, '1', '0', '拥有后台全部管理权限'),
('内容编辑', 'editor', 2, '1', '0', '负责文章、分类、标签和评论等内容管理'),
('普通作者', 'author', 3, '1', '0', '负责维护自己的文章和个人资料')
ON DUPLICATE KEY UPDATE
`role_name` = VALUES(`role_name`),
`role_sort` = VALUES(`role_sort`),
`status` = VALUES(`status`),
`del_flag` = VALUES(`del_flag`),
`remark` = VALUES(`remark`);
