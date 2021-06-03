package me.fulln.lock.domain;

/**
 * @author fulln
 * @version 0.0.1
 * @program locks
 * @description 锁的域方法
 * @date 2021/6/1 23:58
 **/
public interface LockDomain {

    /**
     * 获取锁
     *
     * @return 是否成功
     */

    String tryLock(String key, String version);

    /**
     * 释放锁
     *
     * @return 是否成功
     */
    boolean releaseLock(String key, String version);
}
