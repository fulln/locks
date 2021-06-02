package me.fulln.lock.module;

import me.fulln.lock.domain.LockDomain;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author fulln
 * @version 0.0.1
 * @program locks
 * @description 锁步骤
 * @date 2021/6/3 2:10
 **/
@Component
public class LockModule implements ApplicationContextAware {

    private Map<String, LockDomain> lockMaps;

    public LockDomain selectLock(String type) {
        return lockMaps.get(type);
    }

    public LockDomain selectLock() {
        return lockMaps.get("mysqlLockService");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.lockMaps = applicationContext.getBeansOfType(LockDomain.class);
    }


}
