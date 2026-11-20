package com.myx.fooddelivery.service;


import com.myx.fooddelivery.common.exception.BusinessException;
import com.myx.fooddelivery.dto.UserRegisterDTO;
import com.myx.fooddelivery.mapper.UserMapper;
import com.myx.fooddelivery.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)//开启Mockito假数据模式
public class UserServiceTest {
    @Mock//假对象
    private UserMapper userMapper;

    @InjectMocks//把上面的假Mapper注入到真正的Service里
    private UserServiceImpl userService;

    @Test
    public void testRegister_UserExists(){
        //1、准备数据
        UserRegisterDTO dto=new UserRegisterDTO();
        dto.setUsername("test_user");

        //2、模拟行为：当调用selectCount时，返回1（表示数据库里已经有人了）
        when(userMapper.selectCount(any())).thenReturn(1L);

        //3、断言：执行register时，是不是抛出了BusinessException异常
        BusinessException exception=assertThrows(BusinessException.class,()->{
            userService.register(dto);
        });

        //4、验证异常信息对不对
        assertEquals("该用户名已被注册，请换一个！",exception.getMessage());

    }
}
