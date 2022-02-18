package com.fanxb.backend.entity;

import lombok.Data;

/**
 * 统一返回类
 *
 * @author fanxb
 * @date 2019/3/19 18:05
 */
@Data
public class ResultObject {

    /**
     * 状态，1：正常，0：异常，-1：未认证
     */
    private int code;
    private String message;
    private Object data;

    public ResultObject() {
    }

    public ResultObject(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResultObject unAuth() {
        return new ResultObject(-1, "", null);
    }

    public static ResultObject success() {
        return new ResultObject(1, null, null);
    }

    public static ResultObject success(Object data) {
        return new ResultObject(1, null, data);
    }
}
