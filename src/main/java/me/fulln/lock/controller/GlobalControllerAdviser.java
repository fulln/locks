package me.fulln.lock.controller;

import me.fulln.lock.exception.RetryException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author fulln
 * @version 0.0.1
 * @program locks
 * @description
 * @date 2021/6/4 21:58
 **/
@RestControllerAdvice
public class GlobalControllerAdviser {

    @ExceptionHandler(RetryException.class)
    public String handleRetryException(Exception e) {
        return String.format("锁获取失败,key=%s", e.getMessage());
    }
}
