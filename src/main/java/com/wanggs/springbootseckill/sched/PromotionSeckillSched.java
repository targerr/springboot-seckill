package com.wanggs.springbootseckill.sched;

import com.wanggs.springbootseckill.mapper.PromotionSeckillMapper;
import com.wanggs.springbootseckill.pojo.PromotionSeckill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Wgs
 * @version 1.0
 * @create：2020/05/20
 */
@Component
@Slf4j
public class PromotionSeckillSched {
    @Autowired
    private PromotionSeckillMapper promotionSeckillMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0/5 * * * * ?")
    public void start() {
        List<PromotionSeckill> promotionSeckills = promotionSeckillMapper.findUnstartSecKill();
        if (!CollectionUtils.isEmpty(promotionSeckills)) {
            log.info("【秒杀】 活动开始!");
            promotionSeckills.forEach(promotionSeckill -> {
                // 删除缓存库存
                redisTemplate.delete("seckill:count:" + promotionSeckill.getPsId());
                // 库存商品编号
                for (int i = 0; i < promotionSeckill.getPsCount(); i++) {
                    redisTemplate.opsForList().rightPush("seckill:count:" + promotionSeckill.getPsId(), promotionSeckill.getGoodsId());
                }
                // 更新为进行中
                promotionSeckill.setStatus(1);
                promotionSeckillMapper.updateStatus(promotionSeckill);
            });

        }
    }
    @Scheduled(cron = "0/5 * * * * ?")
    public void end(){
        List<PromotionSeckill> promotionSeckills = promotionSeckillMapper.findDownstartSecKill();
        if (!CollectionUtils.isEmpty(promotionSeckills)){
            log.info("【秒杀】 活动结束");
           for (PromotionSeckill promotionSeckill : promotionSeckills){
               // 删除缓存库存
               redisTemplate.delete("seckill:count:" + promotionSeckill.getPsId());
               promotionSeckill.setStatus(2);
               promotionSeckillMapper.updateStatus(promotionSeckill);
           }
        }
    }
}
