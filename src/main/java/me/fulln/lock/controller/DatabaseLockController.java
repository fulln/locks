package me.fulln.lock.controller;

import me.fulln.lock.module.LockModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/tryLock")
    public String getLock(String key) {
        boolean b = module.selectLock().tryLock(key);
        return String.format("锁获取状态 key=%s->【%s】", key, b);
    }


}
