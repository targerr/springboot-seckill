package com.wanggs.springbootseckill.mapper;

import com.wanggs.springbootseckill.pojo.PromotionSeckill;

import java.util.List;

/**
 * @author Wgs
 * @version 1.0
 * @create：2020/05/20
 */
public interface PromotionSeckillMapper {
    List<PromotionSeckill> findUnstartSecKill();

    void updateStatus(PromotionSeckill promotionSeckill);

    List<PromotionSeckill> findDownstartSecKill();

    PromotionSeckill findByPsId(Long psId);
}
