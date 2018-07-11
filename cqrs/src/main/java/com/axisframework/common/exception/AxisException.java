package com.axisframework.common.exception;

/**
 * 基础异常类，其他异常必须继承它
 *
 * @author luoqi
 * @since 1.0
 */
public class AxisException extends RuntimeException {

    public AxisException(String message) {
        super(message);
    }

    public AxisException(String message, Throwable cause) {
        super(message, cause);
    }

}
