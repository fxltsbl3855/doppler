#创建seed表
CREATE TABLE `TB_DATA_SEED` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `VALUE` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;

#创建app表
CREATE TABLE `TB_PARA_APP` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8;

#创建service表
CREATE TABLE `TB_PARA_SERVICE` (
  `ID` int(11),
  `NAME` varchar(100) DEFAULT NULL,
  `APP_ID` int(11) DEFAULT NULL,
   `INVOKE_ROLE` int(11) DEFAULT -1,
  PRIMARY KEY (`ID`),
  KEY `fk_appId` (`APP_ID`),
  CONSTRAINT `fk_appId` FOREIGN KEY (`APP_ID`) REFERENCES `TB_PARA_APP` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#创建serviceId生成策略表
CREATE TABLE `TB_PARA_SERVICE_ID_GEN` (
  `MAX_ID` int(11) NOT NULL,
  `HEAD` int(11) NOT NULL,
  `MAX_HEAD` int(11) NOT NULL,
  `ID_SCOPE` int(11) NOT NULL,
  PRIMARY KEY (`MAX_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#初始化生成策略数据，serviceId由head+max_id两部分组成max_id和head分别自增。
#head自增至26之后重置为0（为了配合hbase分区策略，hbase分多少个区，则max_head为多少）
#max_id自增值9999后后重置为0
INSERT INTO `TB_PARA_SERVICE_ID_GEN` VALUES (0, 0, 26, 10000);

#annotation
CREATE TABLE `annotation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `k` varchar(128) DEFAULT NULL,
  `value` varchar(6000) DEFAULT NULL,
  `ip` varchar(45) DEFAULT NULL,
  `port` varchar(11) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `spanId` bigint(128) DEFAULT NULL,
  `traceId` bigint(128) DEFAULT NULL,
  `service` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=217122 DEFAULT CHARSET=utf8;

#span
CREATE TABLE `span` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `traceId` bigint(20) DEFAULT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  `spanId` bigint(20) DEFAULT NULL,
  `service` int(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53365 DEFAULT CHARSET=utf8;

#trace
CREATE TABLE `trace` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `traceId` bigint(128) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `service` int(11),
  `time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16924 DEFAULT CHARSET=utf8;

#annotation_web
CREATE TABLE `annotation_web` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `k` varchar(128) DEFAULT NULL,
  `value` varchar(6000) DEFAULT NULL,
  `ip` varchar(45) DEFAULT NULL,
  `port` varchar(11) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `traceId` bigint(128) DEFAULT NULL,
  `appId` int(128) DEFAULT NULL,
  `serviceId` int(11),
  `appName` varchar(128) DEFAULT NULL,
  `serviceName` varchar(128) DEFAULT NULL,
  `spanId` bigint(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



##############log 3.0############
#打点日志，异常表
CREATE TABLE `bus_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addr` varchar(30) DEFAULT NULL,
  `spanId` bigint(128) DEFAULT NULL,
  `traceId` bigint(128) DEFAULT NULL,
  `appId` int(11) DEFAULT NULL,
  `serviceId` int(11) DEFAULT NULL,
  `logInfo` varchar(6000) DEFAULT NULL,
  `logType` int(11) DEFAULT NULL,
  `errorType` int(11) DEFAULT NULL,
  `logTime` bigint(128),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

#报警配置表
CREATE TABLE `alarm_conf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appId` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `email` varchar(2048) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `errorType` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

#报警记录
CREATE TABLE `alarm_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `budLogId` int(11) DEFAULT NULL,
  `lockStatus` int(11) DEFAULT NULL,
  `alarmTime` bigint(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

#请求数据方法汇总表
CREATE TABLE `req_stat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appId` int(11) DEFAULT NULL,
  `appType` int(11) DEFAULT NULL,
  `serviceId` int(11) DEFAULT NULL,
  `method` varchar(50) DEFAULT NULL,
  `host` varchar(50) DEFAULT NULL,
  `logTime` bigint(128),
  `reqNum` bigint(128),
  `timeoutNum` bigint(128),
  `errorNum` bigint(128),
  `duration` int(11),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

#请求数据汇总表
CREATE TABLE `req_stat_service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appId` int(11) DEFAULT NULL,
  `appType` int(11) DEFAULT NULL,
  `serviceId` int(11) DEFAULT NULL,
  `reqNum` bigint(128),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

#索引
CREATE INDEX index_timestamp  on annotation_web(timestamp);
CREATE INDEX index_span  on annotation_web(spanId);

CREATE INDEX index_timestamp  on annotation(timestamp);
CREATE INDEX index_span  on annotation(spanId);

CREATE INDEX index_name  on TB_PARA_SERVICE(name);
CREATE FULLTEXT INDEX fulltext_addr_logInfo ON bus_log(addr,logInfo);
CREATE INDEX index_spanId  on bus_log(spanId);
CREATE INDEX index_logTime on bus_log(logTime);

CREATE unique INDEX aid_sid_method_host_logtime  ON `req_stat`  (`appId`,`serviceId`,`method`,`host`,`logTime`);
CREATE INDEX index_logTime on req_stat(logTime);
CREATE unique INDEX aid_sid  ON `req_stat_service`  (`appId`,`serviceId`);



