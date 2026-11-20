package com.myx.fooddelivery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {
    @TableId(type= IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String nickname;
    private String avatar;
    private Integer gender;//0未知 1男 2女
    private Integer role;//0普通用户 1商家 2平台管理员 3骑手
    private Integer status;//1正常 0禁用
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
