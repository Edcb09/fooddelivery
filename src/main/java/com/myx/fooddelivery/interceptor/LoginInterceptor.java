package com.myx.fooddelivery.interceptor;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.myx.fooddelivery.common.JwtUtil;
import com.myx.fooddelivery.common.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception{
        //1、放行OPTIONS请求
        if (HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }

        //2、从请求头中获取Token（通常前端会把Token放在Authorization字段里）
        String token=request.getHeader("Authorization");
        if(token==null||token.isEmpty()){
            throw new RuntimeException("未登录，请先登录！");
        }

        //3、解析Token
        try{
            if(token.startsWith("Bearer")){
                token=token.substring(7);
            }
            DecodedJWT jwt= jwtUtil.verifyToken(token);

            //4、解析成功，把用户信息存入ThreadLocal
            Long userId=jwt.getClaim("userId").asLong();
            Integer role=jwt.getClaim("role").asInt();
            UserContext.setUserId(userId);
            UserContext.setRole(role);

            //5、放行，让请求进入Controller
            return true;
        }catch (Exception e){
            //Token过期或伪造，抛出异常
            throw new RuntimeException("登录已过期或凭证无效，请重新登录！");
        }
    }

    //请求完后清理ThreadLocal
    @Override
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response,Object handler,Exception ex){
        UserContext.clear();
    }
}
