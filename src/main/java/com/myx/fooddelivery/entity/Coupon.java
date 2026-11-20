package com.myx.fooddelivery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("coupon")
public class Coupon {
    @TableId(type= IdType.AUTO)
    private Long id;
    private Long shopId;//NULL=平台通用券
    private String name;
    private Integer type;//1满减 2折扣
    private BigDecimal threshold;
    private BigDecimal value;
    private Integer validDays;
    private Integer status;//1启用 0停用
    private LocalDateTime createTime;
}
