CREATE TABLE IF NOT EXISTS `mysql_lock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lock_key` varchar(64) DEFAULT NULL,
  `times` int(11) DEFAULT NULL,
  `timeout` int(11) DEFAULT NULL,
  `delete_flag` tinyint(1) NOT NULL DEFAULT '0',
  `version` varchar(128) DEFAULT NULL,
  `val` varchar(256) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`lock_key`,`delete_flag`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='数据库锁';
