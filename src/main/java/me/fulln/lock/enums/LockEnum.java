package me.fulln.lock.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum LockEnum {
    /**
     * 锁的相关枚举
     */
    MYSQL_LOCK(1, "mysqlLockService", "数据库非重入锁"),
    MYSQL_REENTER_LOCK(2, "mysqlLockReenterService", "数据库可重入锁"),
    REDIS_LOCK(3, "redisLockService", "redis锁"),
    ZOOKEEPER_LOCK(4, "zkLockService", "zk锁");

    public final int code;
    public final String beanName;
    public final String desc;

    public static LockEnum findByCode(Integer code) {
        return Arrays.stream(values()).filter(enums -> enums.code == code).findAny().orElse(MYSQL_LOCK);
    }

}
