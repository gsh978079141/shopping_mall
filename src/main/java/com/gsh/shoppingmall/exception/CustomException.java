package com.gsh.shoppingmall.exception;

import com.gsh.shoppingmall.util.ResultEnum;
import lombok.Data;

/**
 * @author gsh
 * @since 2018/6/6 9:56
 */
@Data
public class CustomException extends RuntimeException {
    private Integer code;

    public CustomException(ResultEnum resultEnum) {
        super(resultEnum.getInfo());
        this.code = resultEnum.getCode();
    }

    public CustomException(Integer code , String info) {
        super(info);
        this.code = code;
    }
}
