package com.wanggs.springbootseckill.service.impl;

import com.wanggs.springbootseckill.mapper.OrderMapper;
import com.wanggs.springbootseckill.pojo.Order;
import com.wanggs.springbootseckill.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Wgs
 * @version 1.0
 * @create：2020/05/21
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Override
    public void insert(Order order) {
         orderMapper.insert(order);
    }
}
