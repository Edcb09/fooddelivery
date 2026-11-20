package com.myx.fooddelivery.common.exception;

import com.myx.fooddelivery.common.enums.ErrorCode;

public class ForbiddenException extends BusinessException{
    public ForbiddenException(){
        super(ErrorCode.FORBIDDEN);
    }

    public ForbiddenException(String customMessage){
        super(ErrorCode.FORBIDDEN,customMessage);
    }
}
