package com.wanggs.springbootseckill.service.impl;

import com.wanggs.springbootseckill.enums.SeckillEnum;
import com.wanggs.springbootseckill.exception.SeckillException;
import com.wanggs.springbootseckill.mapper.PromotionSeckillMapper;
import com.wanggs.springbootseckill.pojo.PromotionSeckill;
import com.wanggs.springbootseckill.service.PromotionSeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Wgs
 * @version 1.0
 * @create：2020/05/21
 */
@Service
@Slf4j
public class PromotionSeckillServiceImpl implements PromotionSeckillService {
    @Autowired
    private PromotionSeckillMapper promotionSeckillMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void processSeckill(Long psId, String userId, Integer num) {
        PromotionSeckill promotionSeckill = promotionSeckillMapper.findByPsId(psId);
        if (promotionSeckill == null) {
            log.error("【秒杀】 活动不存在！");
            throw new SeckillException("【秒杀】 活动不存在！");
        } else {
            // 活动是否结束
            if (SeckillEnum.START.getCode().equals(promotionSeckill.getStatus())) {
                log.error("【秒杀】 活动未开始！");
                throw new SeckillException("【秒杀】 活动未开始！");
            }
            if (SeckillEnum.END.getCode().equals(promotionSeckill.getStatus())) {
                log.error("【秒杀】 活动已结束！");
                throw new SeckillException("【秒杀】 活动已结束！");
            }
            // 弹出一条数据
            Integer goodsId = (Integer) redisTemplate.opsForList().leftPop("seckill:count:" + promotionSeckill.getPsId());
            if (goodsId == null) {
                log.error("【秒杀】 抱歉，该商品已被抢光，下次再来吧！！！");
                throw new SeckillException("【秒杀】 抱歉，该商品已被抢光，下次再来吧！！");
            }
            // 判断用户是否领取过
            boolean isExisted = redisTemplate.opsForSet().isMember("seckill:users:" + promotionSeckill.getPsId(), userId);
            if (isExisted) {
                // 领取过 放入活动
                redisTemplate.opsForList().rightPush("seckill:count:" + promotionSeckill.getPsId(), promotionSeckill.getGoodsId());
                log.error("【秒杀】 抱歉，您已经参加过此活动，请勿重复抢购！");
                throw new SeckillException("【秒杀】 抱歉，您已经参加过此活动，请勿重复抢购！");
            }
            // 领取
            redisTemplate.opsForSet().add("seckill:users:" + promotionSeckill.getPsId(), userId);
            log.info("【秒杀活动】恭喜领取成功!");
        }
    }
}
