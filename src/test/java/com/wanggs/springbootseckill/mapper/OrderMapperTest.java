package com.wanggs.springbootseckill.mapper;

import com.wanggs.springbootseckill.pojo.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Wgs on 2020/5/21.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMapperTest {
    @Resource
    private OrderMapper orderMapper;

    @Test
    public void save() {
        Order order = new Order();
        order.setOrderNo("1111");
        order.setOrderStatus(0);
        order.setUserid("44");
        order.setRecvName("王哈哈");
        order.setRecvMobile("1393310xxxx");
        order.setRecvAddress("杭州西湖区");
        order.setAmout(19.8f);
        order.setPostage(0f);
        order.setCreateTime(new Date());
        orderMapper.insert(order);

    }

}