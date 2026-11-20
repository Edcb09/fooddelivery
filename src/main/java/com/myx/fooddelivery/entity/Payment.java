package com.myx.fooddelivery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("payment")
public class Payment {
    @TableId(type= IdType.AUTO)
    private Long id;
    private String orderNo;
    private String tradeNo;
    private Integer payMethod;//1微信 2支付宝
    private BigDecimal amount;
    private Integer payStatus;//1成功 0失败
    private LocalDateTime payTime;
}
