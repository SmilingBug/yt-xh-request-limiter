package com.yang.yuntai.limit.constants;

/**
 * @program: request-limiter
 * @description: LimitType
 * @author: yang Qiankun
 * @create: 2019-08-18 11:52
 **/
public enum LimitType {
    /**
     * 自定义key
     */
    CUSTOMER,
    /**
     * 根据请求者IP
     */
    IP;
}
