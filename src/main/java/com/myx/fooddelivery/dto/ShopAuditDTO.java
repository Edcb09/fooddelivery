package com.myx.fooddelivery.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShopAuditDTO {
    @NotNull(message = "店铺ID不能为空")
    private Long shopId;

    @NotNull(message = "审核状态不能为空")
    private Integer auditStatus;//1 通过 2 拒绝

    private String rejectReason;//拒绝理由
}
