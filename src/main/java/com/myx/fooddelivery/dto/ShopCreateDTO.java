package com.myx.fooddelivery.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopCreateDTO {


    @NotBlank(message = "店铺名称不能为空")
    @Size(max=20,message = "店铺名称不能超过20个字符")
    private String shopName;

    @NotBlank(message = "店铺电话不能为空")
    private String phone;

    @NotBlank(message = "店铺地址不能为空")
    private String address;

    @NotBlank(message = "店铺品类不能为空")
    private String category;

    private String description;

    @NotNull(message = "起送价不能为空")
    @DecimalMin(value = "0.00",message = "起送价不能低于0元")
    private BigDecimal minOrderAmount;

    @NotNull(message = "配送费不能为空")
    @DecimalMin(value = "0.00",message = "配送费不能低于0元")
    private BigDecimal deliveryFee;
}
