package com.yang.yuntai.limit.impl;

import org.springframework.stereotype.Component;

/**
 * @program: request-limiter
 * @description: 业务类
 * @author: yang Qiankun
 * @create: 2019-08-14 21:57
 **/
@Component
public class TestBean {
    private String name = "yqk";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void test() {
        System.out.println(name);
    }
}
