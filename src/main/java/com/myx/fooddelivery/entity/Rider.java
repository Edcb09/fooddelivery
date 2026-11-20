package com.myx.fooddelivery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("rider")
public class Rider {
    @TableId(type= IdType.AUTO)
    private Long id;
    private Long userId;
    private String name;
    private String phone;
    private Integer status;//1空闲 2配送中 3休息
    private Integer auditStatus;//0待审核 1通过 2拒绝
    private LocalDateTime createTime;
}
