package com.wanggs.springbootseckill.exception;

import com.wanggs.springbootseckill.enums.ResultEnum;
import lombok.Getter;

/**
 * @author Wgs
 * @version 1.0
 * @create：2020/05/21
 */
@Getter
public class SeckillException extends RuntimeException {
    private Integer code;

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SeckillException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
