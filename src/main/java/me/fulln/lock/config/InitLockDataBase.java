package me.fulln.lock.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

/**
 * @author fulln
 * @version 0.0.1
 * @program locks
 * @description 用来初始化数据表使用
 * @date 2021/6/2 0:10
 **/
@Slf4j
@Component
public class InitLockDataBase implements InitializingBean {

    @Autowired
    private DataSource dataSource;

    private static final String SQL_FILE_NAME = "init.sql";

    @Override
    public void afterPropertiesSet(){
        try {
            var scriptRunner = new ScriptRunner(dataSource.getConnection());
            var classPathResource = new ClassPathResource(SQL_FILE_NAME);
            var reader = new InputStreamReader(classPathResource.getInputStream());
            scriptRunner.runScript(reader);
        } catch (SQLException | IOException e) {
           log.info("[初始化sql脚本异常] 执行sql脚本异常,请检查",e);
             throw new IllegalStateException("sql脚本执行异常");
        }
    }
}
