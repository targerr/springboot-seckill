package com.wanggs.springbootseckill.enums;


/**
 * @author Wgs
 * @version 1.0
 * @create：2020/05/21
 */
public enum SeckillEnum {
    START(0, "活动开始"),
    ACTIVE(1, "活动中"),
    END(2, "活动结束"),
    ;
    Integer code;
    String desc;

    SeckillEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
