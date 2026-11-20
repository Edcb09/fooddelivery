package com.myx.fooddelivery.service;


import com.myx.fooddelivery.dto.ShopCreateDTO;
import org.springframework.stereotype.Service;

@Service
public interface ShopService {
    void createShop(ShopCreateDTO createDTO);
    void auditShop(Long shopId,Integer auditStatus,String rejectReason);
}
