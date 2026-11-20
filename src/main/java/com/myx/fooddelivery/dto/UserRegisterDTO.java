package com.myx.fooddelivery.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String phone;
    private Integer role;//0普通 1商家 3骑手
}
