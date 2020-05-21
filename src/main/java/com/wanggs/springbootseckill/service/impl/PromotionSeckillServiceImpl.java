package com.wanggs.springbootseckill.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wanggs.springbootseckill.enums.ResultEnum;
import com.wanggs.springbootseckill.enums.SeckillEnum;
import com.wanggs.springbootseckill.exception.SeckillException;
import com.wanggs.springbootseckill.mapper.PromotionSeckillMapper;
import com.wanggs.springbootseckill.pojo.PromotionSeckill;
import com.wanggs.springbootseckill.service.PromotionSeckillService;
import com.wanggs.springbootseckill.util.RandomUtil;
import com.wanggs.springbootseckill.util.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
    @Resource // rabbitMq客户端
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisLock redisLock;
    private static int TIMEOUT = 10 * 1000;

    @Override
    public void processSeckill(Long psId, String userId, Integer num) {
        // 加锁
        Long time = System.currentTimeMillis() + TIMEOUT;
        try {
            if (!redisLock.lock(String.valueOf(psId), String.valueOf(time))) {
                System.out.println("");
                log.error("【秒杀】 人太多稍后再试!!");
                throw new SeckillException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            PromotionSeckill promotionSeckill = promotionSeckillMapper.findByPsId(psId);
            if (promotionSeckill == null) {
                log.error("【秒杀】 活动不存在！");
                throw new SeckillException(ResultEnum.PRODUCT_NOT_EXIST);
            } else {
                // 活动是否结束
                if (SeckillEnum.START.getCode().equals(promotionSeckill.getStatus())) {
                    log.error("【秒杀】 活动未开始！");
                    throw new SeckillException(11, "【秒杀】 活动未开始！");
                }
                if (SeckillEnum.END.getCode().equals(promotionSeckill.getStatus())) {
                    log.error("【秒杀】 活动已结束！");
                    throw new SeckillException(12, "【秒杀】 活动已结束！");
                }
                // 弹出一条数据
                Integer goodsId = (Integer) redisTemplate.opsForList().leftPop("seckill:count:" + promotionSeckill.getPsId());
                if (goodsId == null) {
                    log.error("【秒杀】 抱歉，该商品已被抢光，下次再来吧！！！");
                    throw new SeckillException(16, "【秒杀】 抱歉，该商品已被抢光，下次再来吧！！");
                }
                // 判断用户是否领取过
                boolean isExisted = redisTemplate.opsForSet().isMember("seckill:users:" + promotionSeckill.getPsId(), userId);
                if (isExisted) {
                    // 领取过 放入活动
                    redisTemplate.opsForList().rightPush("seckill:count:" + promotionSeckill.getPsId(), promotionSeckill.getGoodsId());
                    log.error("【秒杀】 抱歉，您已经参加过此活动，请勿重复抢购！");
                    throw new SeckillException(23, "【秒杀】 抱歉，您已经参加过此活动，请勿重复抢购！");
                }
                // 领取
                redisTemplate.opsForSet().add("seckill:users:" + promotionSeckill.getPsId(), userId);
                log.info("【秒杀活动】恭喜领取成功!");
                // mq异步消峰解耦
                sendOrderToQuery(userId);
                log.info("【MQ】 创建订单");
            }
        } finally {
            // 解锁
            redisLock.unlock(String.valueOf(psId), String.valueOf(time));
        }
    }

    // 订单信息推送队列
    public String sendOrderToQuery(String userId) {
        String orderNo = RandomUtil.createOrderNo();
        JSONObject data = new JSONObject();
        data.put("userId", userId);
        data.put("orderNo", orderNo);

        //附加额外的订单信息
        rabbitTemplate.convertAndSend("exchange-order", null, data);
        return orderNo;
    }
}
