package com.myx.fooddelivery.common;

import com.myx.fooddelivery.common.enums.ErrorCode;
import com.myx.fooddelivery.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    //专门捕获自定义业务异常
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e){
        log.warn("业务异常：{}",e.getMessage());
        return Result.error(e.getCode(),e.getMessage());
    }

    //捕获参数校验异常（@Validated / @Valid 触发），只返回 DTO 上写的中文提示
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidationException(MethodArgumentNotValidException e){
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.error(ErrorCode.PARAM_ERROR.getCode(),msg);
    }

    //捕获未知异常
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e)
    {
        //可以在控制台打印出真实的报错，方便程序员排查Bug
        log.error("系统异常：",e);//系统异常只在后台打印完整堆栈
        //返回给前端一个统一的格式
        return Result.error(ErrorCode.SYSTEM_ERROR.getCode(), "服务器开小差了，请稍后再试！");
    }
}
