package com.xy.blog.common.constant;

/**
 * 缓存 Key 常量定义。
 */
public final class CacheConstants {

    public static final String LOGIN_TOKEN_KEY_PREFIX = "blog:login:token:";
    public static final String LOGIN_CAPTCHA_CODE_KEY_PREFIX = "blog:captcha:login:";
    public static final String EMAIL_LOGIN_CODE_KEY_PREFIX = "blog:email:code:login:";
    public static final String EMAIL_REGISTER_CODE_KEY_PREFIX = "blog:email:code:register:";
    public static final String EMAIL_LOGIN_SEND_LIMIT_KEY_PREFIX = "blog:email:send:login:";
    public static final String EMAIL_REGISTER_SEND_LIMIT_KEY_PREFIX = "blog:email:send:register:";
    public static final String PASSWORD_RESET_CODE_KEY_PREFIX = "blog:password:code:";
    public static final String PASSWORD_RESET_SEND_LIMIT_KEY_PREFIX = "blog:password:send:";

    private CacheConstants() {
    }
}