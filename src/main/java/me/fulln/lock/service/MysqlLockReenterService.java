package me.fulln.lock.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import me.fulln.lock.dao.MysqlLockDao;
import me.fulln.lock.domain.LockDomain;
import me.fulln.lock.entity.MysqlLock;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.MDC;
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
@Service
public class MysqlLockReenterService implements LockDomain {

    /**
     * 最大重试次数
     */
    public static final int MAX_RETRY_TIMES = 5;

    @Autowired
    private MysqlLockDao mysqlLockDao;

    /**
     * 获取锁
     * 可重入模式
     *
     * @return 是否成功
     */
    @Override
    public String tryLock(String key, String version) {
        try {
            var lock = new MysqlLock(key);
            int insert;
            if (StringUtils.isNotBlank(version)) {
                lock.setVersion(version);
                UpdateWrapper<MysqlLock> update = new UpdateWrapper<>();
                update.lambda()
                        .setSql("times = times + 1")
                        .eq(MysqlLock::getLockKey, key)
                        .eq(MysqlLock::getDeleteFlag, false)
                        .eq(MysqlLock::getVersion, version);
                insert = mysqlLockDao.update(null, update);
            } else {
                insert = mysqlLockDao.insert(lock);
            }
            if (insert > 0) {
                return lock.getVersion();
            }
        } catch (DuplicateKeyException e) {
            log.error("[数据库重入锁失败！],KEY={},插入数据库失败，锁已被获取", key);
        }
        return null;
    }

    /**
     * 释放锁
     * 可重入模式
     *
     * @return 是否成功
     */
    @Override
    public boolean releaseLock(String key, String version) {
        try {
            MDC.put(key, NumberUtils.toInt(MDC.get(key), 0) + "");
            UpdateWrapper<MysqlLock> update = new UpdateWrapper<>();
            update.lambda()
                    .setSql(StringUtils.isNotBlank(version), "times = times - 1")
                    .setSql(StringUtils.isNotBlank(version), "`delete_flag`= CASE WHEN `times` > 0 THEN  0 ELSE 1 END")
                    .setSql(StringUtils.isBlank(version), "delete_flag = 1")
                    .eq(MysqlLock::getLockKey, key)
                    .eq(MysqlLock::getDeleteFlag, false)
                    .eq(MysqlLock::getVersion, version);
            boolean b = mysqlLockDao.update(null, update) > 0;
            MDC.clear();
            return b;
        } catch (Exception e) {
            log.error("[数据库重入锁释放失败！],KEY={},version={}", key, version);
            int i = NumberUtils.toInt(MDC.get(key), 0);
            if (i <= MAX_RETRY_TIMES) {
                MDC.put(key, i + 1 + "");
                releaseLock(key, version);
            } else {
                MDC.clear();
            }
        }
        return false;
    }
}
