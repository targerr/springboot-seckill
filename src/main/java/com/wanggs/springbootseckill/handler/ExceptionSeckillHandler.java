package com.wanggs.springbootseckill.handler;

import com.wanggs.springbootseckill.VO.ResultVo;
import com.wanggs.springbootseckill.exception.SeckillException;
import com.wanggs.springbootseckill.util.ResultVoUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Wgs
 * @version 1.0
 * @create：2020/05/21
 */
@ControllerAdvice
public class ExceptionSeckillHandler {
    @ResponseBody
    @ExceptionHandler(value = SeckillException.class)
    public ResultVo exceptionHandlerSeckill(SeckillException e) {
        return ResultVoUtil.error(e.getMessage(), e.getCode());
    }
}
