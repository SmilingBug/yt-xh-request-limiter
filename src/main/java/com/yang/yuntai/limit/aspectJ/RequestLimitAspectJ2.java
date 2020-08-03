package com.yang.yuntai.limit.aspectJ;

import com.google.common.util.concurrent.RateLimiter;
import com.yang.yuntai.limit.annotation.RequestLimiter2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: request-limiter
 * @description: 切面
 * @author: yang Qiankun
 * @create: 2019-08-14 22:02
 **/
@Aspect
@Configuration
public class RequestLimitAspectJ2 {

    private static final Logger logger = LoggerFactory.getLogger(RequestLimitAspectJ.class);

    private static final String UNKNOWN = "unknown";

    private static final RateLimiter rateLimiter = RateLimiter.create(5000.0);

    private static final AtomicInteger invokeTimes = new AtomicInteger();


    @Around("execution(public * *(..)) && @annotation(com.yang.yuntai.limit.annotation.RequestLimiter2)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        logger.info("服务: {},拦截到方法: {}",this.getIpAddress(), pjp.getSignature().getName());
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        try {
            if (method.isAnnotationPresent(RequestLimiter2.class)) {
                RequestLimiter2 limitAnnotation = method.getAnnotation(RequestLimiter2.class);
                rateLimiter.setRate(limitAnnotation.perSecond());
                boolean tryAcquire = rateLimiter.tryAcquire(limitAnnotation.timeout(), limitAnnotation.timeoutUnit());
                if (tryAcquire) {
                    return pjp.proceed();
                } else {
                    logger.info("本次请求被限流----------------------------------------");
                    invokeTimes.incrementAndGet();
                    logger.info("限流次数达到： {}",invokeTimes.get());
                    return 1;
                }
            } else {
                return pjp.proceed();
            }
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
            throw new RuntimeException("RateLimiter deal exception");
        }
    }


    /**
     * 获取IP地址
     * @return
     */
    public String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
