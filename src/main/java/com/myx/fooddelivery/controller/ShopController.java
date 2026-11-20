package com.myx.fooddelivery.controller;

import com.myx.fooddelivery.common.Result;
import com.myx.fooddelivery.dto.ShopCreateDTO;
import com.myx.fooddelivery.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @PostMapping("/create")
    //@Validated让DTO里的校验规则生效
    public Result<Void> createShop(@RequestBody @Validated ShopCreateDTO createDTO){
        shopService.createShop(createDTO);
        return Result.success();
    }
}
