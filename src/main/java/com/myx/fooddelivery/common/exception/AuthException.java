package com.myx.fooddelivery.common.exception;

import com.myx.fooddelivery.common.enums.ErrorCode;

public class AuthException extends BusinessException{
    public AuthException(){
        super(ErrorCode.UNAUTHORIZED);
    }

    public AuthException(String customMessage){
        super(ErrorCode.UNAUTHORIZED,customMessage);
    }

}
