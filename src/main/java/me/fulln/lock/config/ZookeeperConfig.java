package me.fulln.lock.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ZookeeperConfig {

    @Value("${zookeeper.address}")
    private String connectString;

    @Value("${zookeeper.timeout}")
    private int timeout;

    @Bean(name = "zkClient")
    public CuratorFramework zkClient() {
        CuratorFramework client = CuratorFrameworkFactory
                .newClient(connectString, timeout, 1000 * 15, new RetryNTimes(10, 5000));
        client.start();
        return client;
    }

}