package me.fulln.lock.service;

import lombok.extern.slf4j.Slf4j;
import me.fulln.lock.domain.LockDomain;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

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
    private CuratorFramework client;

    /**
     * 获取锁，非重入
     *
     * @param key
     * @param version
     * @return 是否成功
     */
    @Override
    public synchronized String tryLock(String key, String version) {
        try {
            //设置版本号
            version = StringUtils.defaultString(version, String.format("%d", System.currentTimeMillis() / 1000));

            var stat = client.checkExists().forPath("/" + key);
            if (stat != null) {
                log.warn("已经在zk里面加上了锁，当前持有者为{}", version);
                return null;
            } else {
                var s = client.create()
                        .withMode(CreateMode.EPHEMERAL)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath("/" + key, version.getBytes());

                log.info("节点{}创建成功,开始判断是否获取到对应的锁={}", key, s);

                var children = client.getChildren().forPath(s)
                        .stream().sorted().collect(Collectors.toList());
                if (CollectionUtils.isEmpty(children)) {
                    return version;
                }

                if (key.equals(children.stream().findFirst().get())) {
                    return version;
                }
            }
        } catch (Exception e) {
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
            var stat = client.checkExists().forPath("/" + key);
            if (stat != null) {
                //version参数指定要更新的数据的版本, 如果version和真实的版本不同, 更新操作将失败. 指定version为-1则忽略版本检查
                client.delete().withVersion(NumberUtils.toInt(version, -1)).forPath("/" + key);
                return true;
            }
        } catch (Exception e) {
            log.error("【删除持久化节点异常】{}", key, e);
        }
        return false;
    }
}
