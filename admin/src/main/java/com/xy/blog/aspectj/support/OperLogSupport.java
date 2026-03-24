package com.xy.blog.aspectj.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xy.blog.framework.aspectj.annotation.Log;
import com.xy.blog.framework.security.context.UserContext;
import com.xy.blog.framework.web.domain.Result;
import com.xy.blog.framework.web.utils.ServletUtils;
import com.xy.blog.system.entity.po.BlogOperLog;
import com.xy.blog.system.service.IBlogOperLogService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

/**
 * 操作日志支撑类。
 * 统一封装操作日志保存、请求参数序列化和敏感字段脱敏。
 */
@Component
@RequiredArgsConstructor
public class OperLogSupport {

    private static final int MAX_TEXT_LENGTH = 4000;

    private final IBlogOperLogService blogOperLogService;
    private final ObjectMapper objectMapper;

    /**
     * 记录切面中的操作日志。
     */
    public void record(MethodSignature signature, Log logAnnotation, Object[] args, Object result, Throwable throwable, long costTime) {
        BlogOperLog operLog = buildBaseOperLog(logAnnotation, costTime, throwable);
        operLog.setMethod(signature.getDeclaringTypeName() + "." + signature.getName() + "()");
        if (logAnnotation.saveRequestData()) {
            operLog.setOperParam(limitLength(maskSensitiveData(toJson(args))));
        }
        operLog.setJsonResult(buildJsonResult(logAnnotation, result, throwable));
        blogOperLogService.saveOperLog(operLog);
    }

    /**
     * 记录进入 Controller 前发生的参数校验或请求解析异常。
     */
    public void recordFromCurrentRequest(Throwable throwable, String message) {
        HttpServletRequest request = ServletUtils.getRequest();
        if (request == null) {
            return;
        }

        Object handler = request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return;
        }

        Log logAnnotation = handlerMethod.getMethodAnnotation(Log.class);
        if (logAnnotation == null) {
            return;
        }

        BlogOperLog operLog = buildBaseOperLog(logAnnotation, 0L, throwable);
        operLog.setMethod(handlerMethod.getBeanType().getName() + "." + handlerMethod.getMethod().getName() + "()");
        if (logAnnotation.saveRequestData()) {
            operLog.setOperParam(limitLength(maskSensitiveData(buildRequestBodySnapshot(request))));
        }
        operLog.setJsonResult(limitLength(maskSensitiveData(toJson(Result.error(message)))));
        blogOperLogService.saveOperLog(operLog);
    }

    private BlogOperLog buildBaseOperLog(Log logAnnotation, long costTime, Throwable throwable) {
        HttpServletRequest request = ServletUtils.getRequest();
        BlogOperLog operLog = new BlogOperLog();
        operLog.setTitle(logAnnotation.title());
        operLog.setBusinessType(logAnnotation.businessType().name());
        operLog.setRequestMethod(request == null ? "" : request.getMethod());
        operLog.setOperName(getCurrentUserName());
        operLog.setOperUrl(request == null ? "" : request.getRequestURI());
        operLog.setOperIp(ServletUtils.getClientIp());
        operLog.setOperLocation("");
        operLog.setStatus(throwable == null ? "1" : "0");
        operLog.setErrorMsg(throwable == null ? "" : limitLength(throwable.getMessage()));
        operLog.setCostTime(costTime);
        operLog.setOperTime(LocalDateTime.now());
        return operLog;
    }

    private String buildJsonResult(Log logAnnotation, Object result, Throwable throwable) {
        if (!logAnnotation.saveResponseData()) {
            return "";
        }
        if (throwable == null) {
            return limitLength(maskSensitiveData(toJson(result)));
        }
        return limitLength(maskSensitiveData(toJson(Result.error(throwable.getMessage()))));
    }

    private String getCurrentUserName() {
        String userName = UserContext.getUserName();
        if (userName != null && !userName.isBlank()) {
            return userName;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return "anonymous";
        }
        return authentication.getName();
    }

    private String toJson(Object value) {
        if (value == null) {
            return "";
        }
        try {
            if (value instanceof Object[] array) {
                return objectMapper.writeValueAsString(Arrays.asList(array));
            }
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException exception) {
            return String.valueOf(value);
        }
    }

    private String buildRequestBodySnapshot(HttpServletRequest request) {
        return request.getParameterMap().isEmpty() ? "" : toJson(request.getParameterMap());
    }

    private String maskSensitiveData(String content) {
        if (content == null || content.isBlank()) {
            return "";
        }
        return content
            .replaceAll("(?i)\"password\"\\s*:\\s*\".*?\"", "\"password\":\"******\"")
            .replaceAll("(?i)\"captchaCode\"\\s*:\\s*\".*?\"", "\"captchaCode\":\"******\"")
            .replaceAll("(?i)\"code\"\\s*:\\s*\".*?\"", "\"code\":\"******\"")
            .replaceAll("(?i)\"token\"\\s*:\\s*\".*?\"", "\"token\":\"******\"");
    }

    private String limitLength(String content) {
        if (content == null || content.isBlank()) {
            return "";
        }
        return content.length() > MAX_TEXT_LENGTH ? content.substring(0, MAX_TEXT_LENGTH) : content;
    }
}