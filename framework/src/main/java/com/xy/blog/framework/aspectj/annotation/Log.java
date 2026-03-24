package com.xy.blog.framework.aspectj.annotation;

import com.xy.blog.framework.aspectj.enums.BusinessType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志记录注解。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 模块标题。
     */
    String title();

    /**
     * 业务类型。
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 是否保存请求参数。
     */
    boolean saveRequestData() default true;

    /**
     * 是否保存返回结果。
     */
    boolean saveResponseData() default true;
}
