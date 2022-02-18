package com.fanxb.backend.config;

import com.fanxb.backend.entity.ResultObject;
import com.fanxb.backend.entity.exception.CustomBaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

/**
 * 全局异常处理类
 *
 * @author fanxb
 * @date 2022/2/16 15:28
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandleConfig {
    @ExceptionHandler(Exception.class)
    public ResultObject handleException(Exception e) {
        CustomBaseException be;
        if (e instanceof CustomBaseException) {
            be = (CustomBaseException) e;
            //手动抛出的异常，仅记录message
            log.info("baseException:{}", be.getMessage());
        } else if (e instanceof ConstraintViolationException) {
            //url参数、数组类参数校验报错类
            ConstraintViolationException ce = (ConstraintViolationException) e;
            //针对参数校验异常，建立了一个异常类
            be = new CustomBaseException(ce.getMessage());
        } else if (e instanceof MethodArgumentNotValidException) {
            //json对象类参数校验报错类
            MethodArgumentNotValidException ce = (MethodArgumentNotValidException) e;
            be = new CustomBaseException(Objects.requireNonNull(ce.getFieldError()).getDefaultMessage());
        } else {
            //其它异常，非自动抛出的,无需给前端返回具体错误内容（用户不需要看见空指针之类的异常信息）
            log.error("other exception:{}", e.getMessage(), e);
            be = new CustomBaseException("系统异常，请稍后重试", e);
        }
        return new ResultObject(be.getCode(), be.getMessage(), null);
    }
}
