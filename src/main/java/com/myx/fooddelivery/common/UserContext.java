package com.myx.fooddelivery.common;

public class UserContext {
    //使用ThreadLocal 存当前登录用户的Id和角色
    private static final ThreadLocal<Long> USER_ID_HOLDER=new ThreadLocal<>();
    private static final ThreadLocal<Integer> ROLE_HOLDER=new ThreadLocal<>();

    public static void setUserId(Long userId){
        USER_ID_HOLDER.set(userId);
    }
    public static Long getUserId(){
        return USER_ID_HOLDER.get();
    }
    public static void setRole(Integer role){
        ROLE_HOLDER.set(role);
    }
    public static Integer getRole(){
        return ROLE_HOLDER.get();
    }

    //请求结束后必须清理，防止线程池复用导致数据错乱
    public static void clear(){
        USER_ID_HOLDER.remove();
        ROLE_HOLDER.remove();
    }
}
