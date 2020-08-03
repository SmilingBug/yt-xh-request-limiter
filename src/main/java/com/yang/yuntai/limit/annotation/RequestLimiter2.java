package com.yang.yuntai.limit.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * API接口访问控制
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequestLimiter2 {
    /**
     *
     * @return
     */
    String value() default "";

    /**
     * 每秒向桶中放入令牌的数量   默认最大即不做限流
     * @return
     */
    double perSecond() default 5000.0;

    /**
     * 获取令牌的等待时间  默认0
     * @return
     */
    int timeout() default 0;

    /**
     * 超时时间单位 默认毫秒
     * @return
     */
    TimeUnit timeoutUnit() default TimeUnit.MILLISECONDS;

}
