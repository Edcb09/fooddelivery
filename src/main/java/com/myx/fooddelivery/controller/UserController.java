package com.myx.fooddelivery.controller;

import com.myx.fooddelivery.common.Result;
import com.myx.fooddelivery.common.UserContext;
import com.myx.fooddelivery.dto.UserLoginDTO;
import com.myx.fooddelivery.dto.UserRegisterDTO;
import com.myx.fooddelivery.entity.User;
import com.myx.fooddelivery.service.UserService;
import com.myx.fooddelivery.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    //注册接口
    @PostMapping("/register")
    public Result<Void> register(@RequestBody UserRegisterDTO dto){
        userService.register(dto);
        return Result.success();
    }

    //登录接口
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO loginDTO){
        UserLoginVO vo=userService.login(loginDTO);
        return Result.success(vo);
    }

    @GetMapping("/info")
    public Result<String> getUserInfo(){
        //直接从ThreadLocal里拿，不需要前端传任何参数
        Long userId= UserContext.getUserId();
        Integer role= UserContext.getRole();
        return Result.success("当前登录的用户ID是："+userId+"，角色是："+role);
    }
}
