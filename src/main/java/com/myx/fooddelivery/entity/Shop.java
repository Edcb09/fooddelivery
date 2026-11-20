package com.myx.fooddelivery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("shop")
public class Shop {
    @TableId(type= IdType.AUTO)
    private Long id;
    private Long ownerId;
    private String shopName;
    private String category;//店铺品类：快餐/奶茶/火锅等
    private String logo;
    private String description;
    private String address;
    private String phone;
    private String businessHours;
    private BigDecimal minOrderAmount;
    private BigDecimal deliveryFee;
    private BigDecimal packFee;
    private BigDecimal rating;
    private Integer monthlySales;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private Integer status;//1营业中 0休息中
    private Integer auditStatus;//0待审核 1通过 2拒绝
    private String auditReason;//审核拒绝原因
    private Integer isDeleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
