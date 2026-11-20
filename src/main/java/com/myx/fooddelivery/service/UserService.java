package com.myx.fooddelivery.service;
import com.myx.fooddelivery.dto.UserLoginDTO;
import com.myx.fooddelivery.dto.UserRegisterDTO;
import com.myx.fooddelivery.entity.User;
import com.myx.fooddelivery.vo.UserLoginVO;

public interface UserService {
    void register(UserRegisterDTO dto);
    UserLoginVO login(UserLoginDTO loginDTO);
}
