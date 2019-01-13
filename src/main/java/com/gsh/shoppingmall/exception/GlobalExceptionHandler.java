package com.gsh.shoppingmall.exception;

import com.gsh.shoppingmall.util.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

/**
 * @author gsh
 * @date 2018/4/25 16:43
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    @ResponseBody
    public Result customException(HttpServletRequest request, CustomException e) {
        return Result.error(e.getCode(), e.getMessage());
    }
}
