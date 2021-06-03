package me.fulln.lock.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum LockEnum {
    /**
     * 锁的相关枚举
     */
    MYSQL_LOCK(1, "mysqlLockService"),
    MYSQL_REENTER_LOCK(2, "mysqlLockReenterService");


    public final int code;
    public final String beanName;

    public static LockEnum findByCode(Integer code) {
        return Arrays.stream(values()).filter(enums -> enums.code == code).findAny().orElse(MYSQL_LOCK);
    }

}
