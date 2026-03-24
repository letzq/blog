package com.xy.blog.framework.security.context;

/**
 * 当前用户上下文。
 * 使用 ThreadLocal 保存当前请求线程中的用户信息。
 */
public final class UserContext {

    private static final ThreadLocal<LoginUserInfo> CONTEXT = new ThreadLocal<>();

    private UserContext() {
    }

    /**
     * 设置当前登录用户信息。
     *
     * @param userId 用户ID
     * @param userName 用户名
     */
    public static void set(Long userId, String userName) {
        CONTEXT.set(new LoginUserInfo(userId, userName));
    }

    /**
     * 获取当前登录用户信息。
     *
     * @return 当前用户信息，不存在时返回 null
     */
    public static LoginUserInfo get() {
        return CONTEXT.get();
    }

    /**
     * 获取当前用户ID。
     *
     * @return 用户ID，不存在时返回 null
     */
    public static Long getUserId() {
        LoginUserInfo loginUserInfo = get();
        return loginUserInfo == null ? null : loginUserInfo.userId();
    }

    /**
     * 获取当前用户名。
     *
     * @return 用户名，不存在时返回 null
     */
    public static String getUserName() {
        LoginUserInfo loginUserInfo = get();
        return loginUserInfo == null ? null : loginUserInfo.userName();
    }

    /**
     * 清理当前线程上下文。
     */
    public static void clear() {
        CONTEXT.remove();
    }

    /**
     * 当前登录用户信息。
     *
     * @param userId 用户ID
     * @param userName 用户名
     */
    public record LoginUserInfo(Long userId, String userName) {
    }
}
