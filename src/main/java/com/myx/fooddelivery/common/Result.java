package com.myx.fooddelivery.common;


import lombok.Data;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Data
public class Result <T>{
    private Integer code;
    private String msg;
    private T data;

    //构造方法
    public Result(Integer code,String msg,T data)
    {
        this.code=code;
        this.msg=msg;
        this.data=data;
    }

    //成功时的快捷方法(带数据)
    public static <T> Result<T> success(T data)
    {
        return new Result<T>(200,"操作成功",data);
    }

    //成功时的快捷方法（不带数据，比如删除成功）
    public static <T> Result<T> success()
    {
        return new Result<T>(200,"操作成功",null);
    }

    //失败时的快捷方法
    public static <T> Result<T> error(String msg)
    {
        return new Result<>(500,msg,null);
    }

}
