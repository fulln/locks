CREATE TABLE if not EXISTS `mysql_lock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lock_key` varchar(64) DEFAULT NULL,
  `times` int(11) DEFAULT NULL,
  `timeout` int(11) DEFAULT NULL,
  `val` varchar(256) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`lock_key`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='数据库锁'
