package com.fanxb.backend.entity.exception;

import cn.hutool.core.util.StrUtil;

/**
 * 类功能简述： 自定义错误类，默认错误码为0,表示一般错误
 * 类功能详述：
 *
 * @author fanxb
 * @date 2019/3/19 18:09
 */
public class CustomBaseException extends RuntimeException {

    private String message;
    /**
     * 默认0
     */
    private final int code = 0;

    public CustomBaseException() {
        this(null, null);
    }

    public CustomBaseException(String message) {
        this(message, null);
    }

    public CustomBaseException(Exception e) {
        this(null, e);
    }

    public CustomBaseException(String message, Exception e) {
        super(e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        if (StrUtil.isEmpty(this.message)) {
            return super.getMessage();
        } else {
            return this.message;
        }
    }

    public int getCode() {
        return code;
    }
}
