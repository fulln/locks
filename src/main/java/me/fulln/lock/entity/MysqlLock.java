package me.fulln.lock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 */
@Data
public class MysqlLock implements Serializable {

    private static final long serialVersionUID = 4234795919647812864L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String lockKey;
    private Integer times;
    private Integer timeout;
    private String version;
    private Boolean deleteFlag;
    private String val;
    private Date createTime;
    private Date updateTime;

    public MysqlLock(String key) {
        this.lockKey = key;
        this.val = "123";
        this.timeout = 3;
        this.version = System.currentTimeMillis() + "";
        this.times = 1;
        this.deleteFlag = false;
        this.createTime = new Date();
        this.updateTime = new Date();
    }
}