package com.myx.fooddelivery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("orders")
public class Orders {
    @TableId(type= IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long userId;
    private Long shopId;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private BigDecimal goodsAmount;
    private BigDecimal packFee;
    private BigDecimal deliveryFee;
    private BigDecimal discountAmount;
    private BigDecimal payAmount;
    private Integer payMethod;//1微信 2支付宝
    private LocalDateTime payTime;
    private Integer status;//0待支付 1已支付 2商家已接单 3配送中 4已完成 5已取消 6退款中 7已退款
    private String cancelReason;
    private String remark;
    private LocalDateTime acceptTime;
    private LocalDateTime deliverTime;
    private LocalDateTime receiveTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
