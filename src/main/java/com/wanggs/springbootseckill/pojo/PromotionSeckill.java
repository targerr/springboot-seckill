package com.wanggs.springbootseckill.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author Wgs
 * @version 1.0
 * @create：2020/05/20
 */
@Data
public class PromotionSeckill {
    private Long psId;
    private Long goodsId;
    private Integer psCount;
    private Date startTime;
    private Date endTime;
    private Integer status;
    private Float currentPrice;
}
