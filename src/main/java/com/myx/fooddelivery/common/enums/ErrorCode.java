package com.myx.fooddelivery.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //通用错误
    PARAM_ERROR(1001,"参数错误"),
    //认证与权限
    UNAUTHORIZED(2001,"未登录或凭证失效"),
    FORBIDDEN(2002,"无权限访问"),
    //业务错误
    USER_EXISTS(3001,"用户名已存在"),
    STOCK_INSUFFICIENT(3002,"库存不足"),
    SHOP_NOT_FOUND(3003,"店铺不存在"),
    //系统错误
    SYSTEM_ERROR(9999,"系统内部错误");

    private final Integer code;
    private final String message;
}
