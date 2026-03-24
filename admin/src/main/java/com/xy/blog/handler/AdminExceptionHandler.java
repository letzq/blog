package com.xy.blog.handler;

import com.xy.blog.aspectj.support.OperLogSupport;
import com.xy.blog.framework.web.domain.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 管理端异常处理器。
 * 用于补充记录进入 Controller 前发生的参数校验和请求解析异常。
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages = "com.xy.blog.controller")
@RequiredArgsConstructor
public class AdminExceptionHandler {

    private final OperLogSupport operLogSupport;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        String message = fieldError == null ? "参数校验失败" : fieldError.getDefaultMessage();
        operLogSupport.recordFromCurrentRequest(exception, message);
        return Result.error(message);
    }

    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        String message = fieldError == null ? "参数校验失败" : fieldError.getDefaultMessage();
        operLogSupport.recordFromCurrentRequest(exception, message);
        return Result.error(message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        String message = "请求体格式错误";
        operLogSupport.recordFromCurrentRequest(exception, message);
        return Result.error(message);
    }
}
