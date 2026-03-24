-- 博客后台用户角色绑定初始化数据
-- 建议在 blog_user、blog_role、blog_user_role 表准备完成后执行
-- 默认将登录账号为 admin 的用户绑定为超级管理员角色

INSERT INTO `blog_user_role` (`user_id`, `role_id`)
SELECT user.user_id, role.role_id
FROM `blog_user` user
INNER JOIN `blog_role` role ON role.role_key = 'admin'
WHERE user.user_name = 'admin'
  AND NOT EXISTS (
      SELECT 1
      FROM `blog_user_role` user_role
      WHERE user_role.user_id = user.user_id
        AND user_role.role_id = role.role_id
  );

-- 如需给其他账号绑定角色，可参考下面模板：
-- INSERT INTO `blog_user_role` (`user_id`, `role_id`)
-- SELECT user.user_id, role.role_id
-- FROM `blog_user` user
-- INNER JOIN `blog_role` role ON role.role_key = 'editor'
-- WHERE user.user_name = 'your_user_name'
--   AND NOT EXISTS (
--       SELECT 1
--       FROM `blog_user_role` user_role
--       WHERE user_role.user_id = user.user_id
--         AND user_role.role_id = role.role_id
--   );
