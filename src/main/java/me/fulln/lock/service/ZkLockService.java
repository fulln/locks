package me.fulln.lock.service;

import lombok.extern.slf4j.Slf4j;
import me.fulln.lock.domain.LockDomain;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fulln
 * @version 0.0.1
 * @program locks
 * @description
 * @date 2021/6/5 3:08
 **/
@Slf4j
@Component
public class ZkLockService implements LockDomain {

    @Autowired
    private ZooKeeper zooKeeper;
    /**
     * 获取锁
     *
     * @param key
     * @param version
     * @return 是否成功
     */
    @Override
    public String tryLock(String key, String version) {
        try {
            String realKey = String.format("/%s-", key);

            if (zooKeeper.exists(realKey, false) != null) {
                return null;
            } else {

                String path = zooKeeper.create(realKey,
                        "lock".getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL_SEQUENTIAL);
                List<String> children = zooKeeper.getChildren(realKey, false);
            }

        } catch (KeeperException | InterruptedException e) {
            log.error("【创建持久化节点异常】{},{}", key, e);
        }
        return null;
    }

    /**
     * 释放锁
     *
     * @param key
     * @param version
     * @return 是否成功
     */
    @Override
    public boolean releaseLock(String key, String version) {
        try {
            //version参数指定要更新的数据的版本, 如果version和真实的版本不同, 更新操作将失败. 指定version为-1则忽略版本检查
            zooKeeper.delete(String.format("/%s", key), -1);
            return true;
        } catch (Exception e) {
            log.error("【删除持久化节点异常】{},{}", key, e);
            return false;
        }
    }
}
