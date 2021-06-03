package me.fulln.lock.config;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.stereotype.Component;

/**
 * @author fulln
 * @version 0.0.1
 * @program locks
 * @description
 * @date 2021/6/4 3:10
 **/
@Component
public class RetryLockInterceptor extends RetryOperationsInterceptor {

    /**
     * 做重试的拦截,在这里进行串行化操作
     *
     * @param methodInvocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        //TODO 多请求串行化执行
        return super.invoke(methodInvocation);
    }
}
