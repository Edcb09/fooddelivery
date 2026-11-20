package com.myx.fooddelivery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("product")
public class Product {
    @TableId(type= IdType.AUTO)
    private Long id;
    private Long shopId;
    private Long categoryId;
    private String name;
    private String description;
    private String image;
    private BigDecimal price;
    private Integer monthlySales;
    private Integer status;//1上架 0下架
    private Integer version;//乐观锁版本号，防超卖
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
