package com.xy.blog.aspectj;

import com.xy.blog.aspectj.support.OperLogSupport;
import com.xy.blog.framework.aspectj.annotation.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 操作日志切面。
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final OperLogSupport operLogSupport;

    @Around("@annotation(logAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint, Log logAnnotation) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = null;
        Throwable throwable = null;

        try {
            result = joinPoint.proceed();
            return result;
        } catch (Throwable exception) {
            throwable = exception;
            throw exception;
        } finally {
            saveOperLog(joinPoint, logAnnotation, result, throwable, System.currentTimeMillis() - startTime);
        }
    }

    private void saveOperLog(ProceedingJoinPoint joinPoint, Log logAnnotation, Object result, Throwable throwable, long costTime) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            operLogSupport.record(signature, logAnnotation, joinPoint.getArgs(), result, throwable, costTime);
        } catch (Exception exception) {
            log.error("Save operation log failed", exception);
        }
    }
}
