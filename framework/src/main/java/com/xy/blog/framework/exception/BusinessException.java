package com.xy.blog.framework.exception;

/**
 * 业务异常
 *
 * @author xy
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
