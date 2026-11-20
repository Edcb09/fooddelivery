package com.myx.fooddelivery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_coupon")
public class UserCoupon {
    @TableId(type= IdType.AUTO)
    private Long id;
    private Long userId;
    private Long couponId;
    private Long orderId;//核销订单ID
    private Integer status;//0未使用 1已使用 2已过期
    private LocalDateTime expireTime;
    private LocalDateTime createTime;
}
