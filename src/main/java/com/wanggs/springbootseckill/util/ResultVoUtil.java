package com.wanggs.springbootseckill.util;

import com.wanggs.springbootseckill.VO.ResultVo;
import lombok.Data;

/**
 * @author Wgs
 * @version 1.0
 * @create：2020/05/21
 */
@Data
public class ResultVoUtil {
    public static ResultVo success(String msg, Object object) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(0);
        resultVo.setMsg(msg);
        resultVo.setData(object);
        return resultVo;
    }

    public static ResultVo success(Object object) {
        return success(null, object);
    }

    public static ResultVo error(String msg, Integer code) {
        ResultVo resultVo = new ResultVo();
        resultVo.setMsg(msg);
        resultVo.setCode(code);
        return resultVo;
    }
}
