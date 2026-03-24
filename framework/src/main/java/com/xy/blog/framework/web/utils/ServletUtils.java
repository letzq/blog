package com.xy.blog.framework.web.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Servlet 请求工具类。
 * 用于获取当前请求、客户端 IP、浏览器信息和设备信息。
 */
public final class ServletUtils {

    private static final String UNKNOWN = "unknown";

    private ServletUtils() {
    }

    /**
     * 获取当前线程中的 HttpServletRequest。
     *
     * @return 当前请求对象，不存在时返回 null
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes instanceof ServletRequestAttributes servletRequestAttributes) {
            return servletRequestAttributes.getRequest();
        }
        return null;
    }

    /**
     * 获取客户端 IP。
     * 会优先从常见反向代理请求头中取值。
     *
     * @return 客户端 IP，不存在时返回空字符串
     */
    public static String getClientIp() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return "";
        }

        String[] headerNames = {
            "X-Forwarded-For",
            "X-Real-IP",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
        };

        for (String headerName : headerNames) {
            String ip = request.getHeader(headerName);
            if (hasText(ip)) {
                return extractFirstIp(ip);
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * 获取 User-Agent 原始字符串。
     *
     * @return User-Agent，不存在时返回空字符串
     */
    public static String getUserAgent() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return "";
        }
        String userAgent = request.getHeader("User-Agent");
        return userAgent == null ? "" : userAgent;
    }

    /**
     * 获取浏览器名称。
     *
     * @return 浏览器名称
     */
    public static String getBrowser() {
        String userAgent = getUserAgent().toLowerCase();
        if (!hasText(userAgent)) {
            return "Unknown";
        }
        if (userAgent.contains("edg")) {
            return "Edge";
        }
        if (userAgent.contains("chrome")) {
            return "Chrome";
        }
        if (userAgent.contains("firefox")) {
            return "Firefox";
        }
        if (userAgent.contains("safari") && !userAgent.contains("chrome")) {
            return "Safari";
        }
        if (userAgent.contains("opera") || userAgent.contains("opr")) {
            return "Opera";
        }
        if (userAgent.contains("msie") || userAgent.contains("trident")) {
            return "Internet Explorer";
        }
        return "Unknown";
    }

    /**
     * 获取操作系统名称。
     *
     * @return 操作系统名称
     */
    public static String getOs() {
        String userAgent = getUserAgent().toLowerCase();
        if (!hasText(userAgent)) {
            return "Unknown";
        }
        if (userAgent.contains("windows")) {
            return "Windows";
        }
        if (userAgent.contains("mac os")) {
            return "macOS";
        }
        if (userAgent.contains("android")) {
            return "Android";
        }
        if (userAgent.contains("iphone") || userAgent.contains("ipad") || userAgent.contains("ios")) {
            return "iOS";
        }
        if (userAgent.contains("linux")) {
            return "Linux";
        }
        return "Unknown";
    }

    /**
     * 获取设备信息。
     * 当前按常见 User-Agent 关键字区分移动端、平板和桌面端。
     *
     * @return 设备信息描述
     */
    public static String getDeviceInfo() {
        String userAgent = getUserAgent().toLowerCase();
        if (!hasText(userAgent)) {
            return "Unknown";
        }
        if (userAgent.contains("ipad") || userAgent.contains("tablet")) {
            return "Tablet";
        }
        if (userAgent.contains("mobile") || userAgent.contains("android") || userAgent.contains("iphone")) {
            return "Mobile";
        }
        return "Desktop";
    }

    private static boolean hasText(String value) {
        return StringUtils.hasText(value) && !UNKNOWN.equalsIgnoreCase(value);
    }

    private static String extractFirstIp(String ip) {
        if (!ip.contains(",")) {
            return ip.trim();
        }
        return ip.split(",")[0].trim();
    }
}