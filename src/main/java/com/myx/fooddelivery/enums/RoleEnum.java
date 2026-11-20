package com.myx.fooddelivery.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    USER(0,"普通用户"),
    MERCHANT(1,"商家"),
    ADMIN(2,"平台管理员"),
    RIDER(3,"骑手");

    private final Integer code;
    private final String desc;


}
