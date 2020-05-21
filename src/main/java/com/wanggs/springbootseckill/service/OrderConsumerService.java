package com.wanggs.springbootseckill.service;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.wanggs.springbootseckill.mapper.OrderMapper;
import com.wanggs.springbootseckill.pojo.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Wgs
 * @version 1.0
 * @create：2020/05/21
 */
@Component
@Slf4j
public class OrderConsumerService {
    @Resource
    private OrderMapper orderMapper;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "queue-order"),
                    exchange = @Exchange(value = "exchange-order", type = "fanout")
            )
    )
    @RabbitHandler
    public void handleMessage(@Payload JSONObject object, Channel channel, @Headers Map<String, Object> headers) {
        try {
            log.info("【MQ】 消费消息: {}", object);
            // 延时是处理支付,物流,日志记录等...
            Thread.sleep(1000);

            Order order = new Order();
            order.setOrderNo(object.get("orderNo").toString());
            order.setOrderStatus(0);
            order.setUserid(object.get("userId").toString());
            order.setRecvName("王哈哈");
            order.setRecvMobile("1393310xxxx");
            order.setRecvAddress("杭州西湖区");
            order.setAmout(19.8f);
            order.setPostage(0f);
            order.setCreateTime(new Date());
            orderMapper.insert(order);

            Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(tag, false);//消息确认
        } catch (IOException e) {
            e.printStackTrace();
            log.error("【MQ】 消费信息异常: {}", e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
