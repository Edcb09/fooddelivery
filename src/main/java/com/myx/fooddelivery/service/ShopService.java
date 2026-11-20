package com.myx.fooddelivery.service;


import com.myx.fooddelivery.dto.ShopAuditDTO;
import com.myx.fooddelivery.dto.ShopCreateDTO;
import org.springframework.stereotype.Service;

@Service
public interface ShopService {
    void createShop(ShopCreateDTO createDTO);
    void auditShop(ShopAuditDTO shopAuditDTO);
}
