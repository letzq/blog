package com.xy.blog.framework.web.handler;

import com.xy.blog.framework.exception.BusinessException;
import com.xy.blog.framework.web.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器。
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常。
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException exception) {
        return Result.error(exception.getMessage());
    }

    /**
     * 处理请求体参数校验异常。
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        return Result.error(fieldError == null ? "参数校验失败" : fieldError.getDefaultMessage());
    }

    /**
     * 处理表单参数校验异常。
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        return Result.error(fieldError == null ? "参数校验失败" : fieldError.getDefaultMessage());
    }

    /**
     * 处理其他未捕获异常。
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception exception) {
        log.error("System exception", exception);
        return Result.error("服务器内部错误");
    }
}