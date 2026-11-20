package com.myx.fooddelivery.common;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //专门捕获自定义业务异常
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e){
        e.printStackTrace();
        return Result.error("业务异常，"+e.getMessage());
    }

    //捕获参数校验异常（@Validated / @Valid 触发），只返回 DTO 上写的中文提示
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidationException(MethodArgumentNotValidException e){
        String msg = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("；"));
        return Result.error(msg);
    }

    //捕获未知异常
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e)
    {
        //可以在控制台打印出真实的报错，方便程序员排查Bug
        e.printStackTrace();
        //返回给前端一个统一的格式
        return Result.error("服务器开小差了，请稍后再试！错误原因："+e.getMessage());
    }
}
