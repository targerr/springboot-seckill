package com.wanggs.springbootseckill.controller;

import com.wanggs.springbootseckill.VO.ResultVo;
import com.wanggs.springbootseckill.service.PromotionSeckillService;
import com.wanggs.springbootseckill.util.ResultVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wgs
 * @version 1.0
 * @create：2020/05/21
 */
@RestController
@RequestMapping("/sell")
public class SeckillController {
    @Autowired
    private PromotionSeckillService promotionSeckillService;

    @RequestMapping("/seckill")
    public ResultVo sekill(@RequestParam(value = "psid", required = true) Long psid,
                           @RequestParam(value = "userid", required = true) String userId) {
        try {
            promotionSeckillService.processSeckill(psid, userId, 1);
            return ResultVoUtil.success("下单成功!");
        } catch (Exception e) {
         //   e.printStackTrace();
            return ResultVoUtil.error(e.getMessage(), 500);
        }
    }
}
