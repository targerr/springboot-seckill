package com.wanggs.springbootseckill.VO;

import lombok.Data;

/**
 * @author Wgs
 * @version 1.0
 * @create：2020/05/21
 */
@Data
public class ResultVo<T> {
    private Integer code;
    private String msg;
    private T data;
}
