CREATE TABLE IF NOT EXISTS `mysql_lock` (
  `id` INT (11) NOT NULL,
  `key` VARCHAR (64) DEFAULT NULL,
  `times` INT (11) NOT NULL,
  `timeout` INT (11) NOT NULL,
  `value` VARCHAR (256) DEFAULT NULL,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '数据库锁';