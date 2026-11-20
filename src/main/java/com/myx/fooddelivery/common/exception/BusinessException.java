package com.myx.fooddelivery.common.exception;


import com.myx.fooddelivery.common.enums.ErrorCode;
import lombok.Getter;
@Getter
public class BusinessException extends RuntimeException{
    private final Integer code;

    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.code=errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode,String customMessage){
        super(customMessage);
        this.code=errorCode.getCode();
    }

    //兼容直接传字符串的写法（默认使用系统错误码）
    public BusinessException(String message){
        super(message);
        this.code=ErrorCode.SYSTEM_ERROR.getCode();
    }
}
