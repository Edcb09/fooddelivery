package com.myx.fooddelivery.common;


import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final Integer code=500;

    public BusinessException(String message){
        super(message);
    }
}
