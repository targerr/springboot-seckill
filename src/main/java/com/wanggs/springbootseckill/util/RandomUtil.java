package com.wanggs.springbootseckill.util;

import java.util.Random;

/**
 * @author Wgs
 * @version 1.0
 * @create：2020/05/21
 */
public class RandomUtil {
    /**
     * 时间+随机数
     *
     * @return
     */
    public static String createOrderNo() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
