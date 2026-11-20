package com.myx.fooddelivery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("delivery_order")
public class DeliveryOrder {
    @TableId(type= IdType.AUTO)
    private Long id;
    private Long orderId;
    private Long riderId;
    private Integer status;//0待分配 1已分配 2取餐中 3配送中 4已送达
    private LocalDateTime pickupTime;
    private LocalDateTime deliverTime;
    private LocalDateTime createTime;
}
