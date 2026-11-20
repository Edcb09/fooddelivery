package com.myx.fooddelivery.vo;

import lombok.Data;

@Data
public class UserLoginVO {
    private String token;//JWT令牌
    private Long userId;
    private String username;
    private Integer role;
}
