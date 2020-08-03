package com.yang.yuntai.limit.controller;


import com.yang.yuntai.limit.annotation.RequestLimiter;
import com.yang.yuntai.limit.annotation.RequestLimiter2;
import com.yang.yuntai.limit.exception.RequestLimitException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: common-interface
 * @description:角色控制器
 * @author: yang Qiankun
 * @create: 2019-06-19 21:14
 **/
@RestController
public class RequestLimitController {

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    @RequestLimiter2(perSecond = 600)
    @PostMapping("/test")
    public Integer testLimiter() {
        Integer res = null;
        try {
            // 意味着100S内最多可以访问10次
            res =  ATOMIC_INTEGER.incrementAndGet();
        }catch (RequestLimitException e){
            System.out.println("捕获了异常！");
        }
        return res;
    }
}
