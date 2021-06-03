package me.fulln.lock.controller;

import me.fulln.lock.enums.LockEnum;
import me.fulln.lock.module.LockModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fulln
 * @version 0.0.1
 * @program locks
 * @description
 * @date 2021/6/2 0:08
 **/
@RequestMapping("/api/lock")
@RestController
public class DatabaseLockController {

    @Autowired
    private LockModule module;


    @Retryable(interceptor = "retryLockInterceptor")
    @GetMapping("/tryLock")
    public String getLock(@RequestParam("key") String key,
                          @RequestParam(value = "version", required = false) String version) {
        String b = module.selectLock(LockEnum.MYSQL_REENTER_LOCK).tryLock(key, version);
        return String.format("锁获取状态 key=%s->version=【%s】", key, b);
    }

    @Retryable(interceptor = "retryLockInterceptor")
    @GetMapping("/releaseLock")
    public String releaseLock(@RequestParam("key") String key,
                              @RequestParam(value = "version", required = false) String version) {
        Boolean b = module.selectLock(LockEnum.MYSQL_REENTER_LOCK).releaseLock(key, version);
        return String.format("锁释放状态 key=%s-> state=【%s】", key, b);
    }


}
