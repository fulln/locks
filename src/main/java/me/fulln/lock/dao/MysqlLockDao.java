package me.fulln.lock.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.fulln.lock.entity.MysqlLock;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author fulln
 * @version 0.0.1
 * @program locks
 * @description
 * @date 2021/6/3 1:28
 **/
@Mapper
public interface MysqlLockDao extends BaseMapper<MysqlLock> {


}
