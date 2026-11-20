package com.myx.fooddelivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.myx.fooddelivery.common.JwtUtil;
import com.myx.fooddelivery.dto.UserLoginDTO;
import com.myx.fooddelivery.dto.UserRegisterDTO;
import com.myx.fooddelivery.entity.User;
import com.myx.fooddelivery.mapper.UserMapper;
import com.myx.fooddelivery.service.UserService;
import com.myx.fooddelivery.vo.UserLoginVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    //BCrypt密码加密器
    private final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Override
    public void register(UserRegisterDTO registerDTO){
        //1、查重：用户名是否已被注册
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,registerDTO.getUsername());
        if(userMapper.selectCount(wrapper)>0){
            throw new RuntimeException("用户名已存在！");
        }

        //2、DTO转换为Entity
        User user=new User();
        BeanUtils.copyProperties(registerDTO,user);

        //3、加密密码
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        //4、设置默认状态
        user.setStatus(1);//正常，允许登录
        if(user.getRole()==null){
            user.setRole(0);
        }
        if(user.getGender()==null){
            user.setGender(0);
        }

        //5、插入数据库
        userMapper.insert(user);
    }

    @Override
    public UserLoginVO login(UserLoginDTO loginDTO){
        //1、根据用户名查询用户
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,loginDTO.getUsername());
        User user=userMapper.selectOne(wrapper);

        //2、校验：用户是否存在
        if(user==null){
            throw new RuntimeException("账号或密码错误");
        }

        //3、校验：用户是否存在
        if(user.getStatus()==0){
            throw new RuntimeException("该账号已被封禁，请联系管理员");
        }

        //4、校验：密码是否正确（BCrypt里的matches方法用来比较明文和密文）
        if(!passwordEncoder.matches(loginDTO.getPassword(),user.getPassword())){
            throw new RuntimeException("账号或密码错误");
        }

        //5、登陆成功，生成JWT Token
        String token= jwtUtil.createToken(user.getId(), user.getRole());

        //6、封装成VO返回给前端
        UserLoginVO vo=new UserLoginVO();
        vo.setToken(token);
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setRole(user.getRole());

        return vo;
    }
}
