package com.xy.blog.framework.web.domain;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 统一响应结果。
 */
@Data
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int SUCCESS = 200;
    private static final int ERROR = 500;

    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(SUCCESS);
        result.setMsg("操作成功");
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = success();
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(ERROR);
        result.setMsg(msg);
        return result;
    }
}