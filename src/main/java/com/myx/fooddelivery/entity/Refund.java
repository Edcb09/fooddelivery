package com.myx.fooddelivery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("refund")
public class Refund {
    @TableId(type= IdType.AUTO)
    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private String reason;
    private Integer status;//0申请中 1同意 2拒绝 3已退款
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
