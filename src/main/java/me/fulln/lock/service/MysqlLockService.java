package me.fulln.lock.service;

import lombok.extern.slf4j.Slf4j;
import me.fulln.lock.dao.MysqlLockDao;
import me.fulln.lock.domain.LockDomain;
import me.fulln.lock.entity.MysqlLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * @author fulln
 * @version 0.0.1
 * @program locks
 * @description 使用mysql 实现的分布式锁
 * @date 2021/6/3 1:35
 **/
@Slf4j
@Service("mysqlLockService")
public class MysqlLockService implements LockDomain {

    @Autowired
    private MysqlLockDao mysqlLockDao;

    /**
     * 获取锁
     *
     * @return 是否成功
     */
    @Override
    public boolean tryLock(String key) {
        try {
            MysqlLock lock = new MysqlLock(key);
            return mysqlLockDao.insert(lock) > 0;
        } catch (DuplicateKeyException e) {
            log.info("[数据库锁失败！],KEY={},插入数据库失败，锁已被获取", key);
            return false;
        }
    }

    /**
     * 释放锁
     *
     * @return 是否成功
     */
    @Override
    public boolean releaseLock(String key) {
//        mysqlLockDao.delete()
        return false;
    }
}
