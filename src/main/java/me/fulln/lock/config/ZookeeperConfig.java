package me.fulln.lock.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@Slf4j
public class ZookeeperConfig {

    @Value("${zookeeper.address}")
    private String connectString;

    @Value("${zookeeper.timeout}")
    private int timeout;

    @Bean(name = "zkClient")
    public ZooKeeper zkClient() {
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper(connectString, timeout, event -> {
                if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
                    //如果收到了服务端的响应事件,连接成功
                    log.info("zk连接成功");
                }
            });
        } catch (IOException e) {
            log.info("【初始化ZooKeeper连接状态....】={}", zooKeeper);
        }

        return zooKeeper;
    }
}