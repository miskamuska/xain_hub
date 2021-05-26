package com.somecoder.demo.common.exception;

import com.somecoder.demo.common.ErrorEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 业务异常
 * @author liangkeyu
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomException extends RuntimeException {
    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String msg;

    /**
     * 构造函数
     * @param code  错误码
     * @param msg   错误信息
     */
    public CustomException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    public CustomException(String message) {
        super(message);
    }

    public CustomException(ErrorEnum errorEnum) {
        this(errorEnum.getMsg());
    }
}
