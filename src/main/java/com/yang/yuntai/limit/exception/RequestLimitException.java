package com.yang.yuntai.limit.exception;

/**
 * @program: yt-xh-request-limiter
 * @description: RequestLimitException
 * @author: yang Qiankun
 * @create: 2019-08-26 22:01
 **/
public class RequestLimitException extends RuntimeException{

    public RequestLimitException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
