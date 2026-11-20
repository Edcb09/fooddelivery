package com.myx.fooddelivery.config;


import com.myx.fooddelivery.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")//默认拦截所有请求
                .excludePathPatterns(//放行以下路径
                        "/api/user/login",
                        "/api/user/register",
                        "/error"//放行Springboot默认的错误页面
                );
    }
}
