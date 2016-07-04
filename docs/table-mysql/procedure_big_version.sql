#######################备份历史数据脚本，每天备份一次，数据库只保留当天数据，适合大数据量情况下
#######################建议数据库每天产生的记录数在700万之上用当前备份版本
########################annotation备份存储过程脚本
CREATE PROCEDURE `bak_history_annotation`()
BEGIN
DECLARE NEWNAME_table VARCHAR(40);
DECLARE sql_text VARCHAR(2000);
DECLARE sql_index_timestamp VARCHAR(2000);
DECLARE sql_index_span VARCHAR(2000);

SET NEWNAME_table=CONCAT('annotation_',DATE_FORMAT(DATE_SUB(CURDATE(),INTERVAL 1 DAY),'%Y_%m_%d'));   
SET @SQLSTR = CONCAT('ALTER TABLE `annotation` RENAME TO ', NEWNAME_table);
PREPARE STMT_rename FROM @SQLSTR;  

SET sql_text='CREATE TABLE `annotation` (
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
) ENGINE=InnoDB AUTO_INCREMENT=217122 DEFAULT CHARSET=utf8' ;
SET @sql_text=sql_text;
PREPARE stmt_create FROM @sql_text;

SET sql_index_timestamp='CREATE INDEX index_timestamp  on annotation(`timestamp`)';
SET @sql_index_timestamp=sql_index_timestamp;
PREPARE stmt_index_timestamp FROM @sql_index_timestamp;
SET sql_index_span='CREATE INDEX index_span  on annotation(`spanId`)';
SET @sql_index_span=sql_index_span;
PREPARE stmt_sql_index_span FROM @sql_index_span;

EXECUTE STMT_rename ;
EXECUTE stmt_create ;
EXECUTE stmt_index_timestamp ;
EXECUTE stmt_sql_index_span ;
END;

########################annotation_web备份存储过程脚本
CREATE   PROCEDURE `bak_history_annotation_web`()
BEGIN
DECLARE NEWNAME_table VARCHAR(40);
DECLARE sql_text VARCHAR(2000);
DECLARE sql_index_timestamp VARCHAR(2000);
DECLARE sql_index_spanId VARCHAR(2000);

SET NEWNAME_table=CONCAT('annotation_web_',DATE_FORMAT(DATE_SUB(CURDATE(),INTERVAL 1 DAY),'%Y_%m_%d'));   
SET @SQLSTR = CONCAT('ALTER TABLE `annotation_web` RENAME TO ', NEWNAME_table);
PREPARE STMT_rename FROM @SQLSTR;  

SET sql_text='CREATE TABLE `annotation_web` (
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8' ;
SET @sql_text=sql_text;
PREPARE stmt_create FROM @sql_text;
SET sql_index_timestamp='CREATE INDEX index_timestamp  on annotation_web(`timestamp`)';
SET @sql_index_timestamp=sql_index_timestamp;
PREPARE stmt_index_timestamp FROM @sql_index_timestamp;
SET sql_index_spanId='CREATE INDEX index_span  on annotation_web(spanId)';
SET @sql_index_spanId=sql_index_spanId;
PREPARE stmt_sql_index_spanId FROM @sql_index_spanId;

EXECUTE STMT_rename ;
EXECUTE stmt_create ;
EXECUTE stmt_index_timestamp;
EXECUTE stmt_sql_index_spanId;
END;


########################bus_log备份存储过程脚本
CREATE   PROCEDURE `bak_history_bus_log`()
BEGIN
DECLARE NEWNAME_table VARCHAR(40);
DECLARE sql_text VARCHAR(2000);
DECLARE sql_fulltext_addr_logInfo VARCHAR(2000);
DECLARE sql_index_spanId VARCHAR(2000);
DECLARE sql_index_logTime VARCHAR(2000);

SET NEWNAME_table=CONCAT('bus_log_',DATE_FORMAT(DATE_SUB(CURDATE(),INTERVAL 1 DAY),'%Y_%m_%d'));   
SET @SQLSTR = CONCAT('ALTER TABLE `bus_log` RENAME TO ', NEWNAME_table);
PREPARE STMT_rename FROM @SQLSTR;  

SET sql_text='CREATE TABLE `bus_log` (
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8' ;
SET @sql_text=sql_text;
PREPARE stmt_create FROM @sql_text;
SET sql_fulltext_addr_logInfo='CREATE FULLTEXT INDEX fulltext_addr_logInfo ON bus_log(`addr`,`logInfo`)';
SET @sql_fulltext_addr_logInfo=sql_fulltext_addr_logInfo;
PREPARE stmt_sql_fulltext_addr_logInfo FROM @sql_fulltext_addr_logInfo;
SET sql_index_spanId='CREATE INDEX index_spanId  on bus_log(spanId)';
SET @sql_index_spanId=sql_index_spanId;
PREPARE stmt_sql_index_spanId FROM @sql_index_spanId;
SET sql_index_logTime='CREATE INDEX index_logTime on bus_log(logTime)';
SET @sql_index_logTime=sql_index_logTime;
PREPARE stmt_sql_index_logTime FROM @sql_index_logTime;

EXECUTE STMT_rename ;
EXECUTE stmt_create ;
EXECUTE stmt_sql_fulltext_addr_logInfo ;
EXECUTE stmt_sql_index_spanId ;
EXECUTE stmt_sql_index_logTime ;
END;


########################req_stat备份存储过程脚本
CREATE   PROCEDURE `bak_history_req_stat`()
BEGIN
DECLARE NEWNAME_table VARCHAR(40);
DECLARE sql_text VARCHAR(2000);
DECLARE sql_aid_sid_method_host_logtime VARCHAR(2000);
DECLARE sql_index_logTime VARCHAR(2000);

SET NEWNAME_table=CONCAT('req_stat_',DATE_FORMAT(DATE_SUB(CURDATE(),INTERVAL 1 DAY),'%Y_%m_%d'));   
SET @SQLSTR = CONCAT('ALTER TABLE `req_stat` RENAME TO ', NEWNAME_table);
PREPARE STMT_rename FROM @SQLSTR;  

SET sql_text='CREATE TABLE `req_stat` (
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8' ;
SET @sql_text=sql_text;
PREPARE stmt_create FROM @sql_text;
SET sql_aid_sid_method_host_logtime='CREATE unique INDEX aid_sid_method_host_logtime  ON `req_stat`  (`appId`,`serviceId`,`method`,`host`,`logTime`)';
SET @sql_aid_sid_method_host_logtime=sql_aid_sid_method_host_logtime;
PREPARE stmt_sql_aid_sid_method_host_logtime FROM @sql_aid_sid_method_host_logtime;
SET sql_index_logTime='CREATE INDEX index_logTime on req_stat(logTime)';
SET @sql_index_logTime=sql_index_logTime;
PREPARE stmt_sql_index_logTime FROM @sql_index_logTime;

EXECUTE STMT_rename ;
EXECUTE stmt_create ;
EXECUTE stmt_sql_aid_sid_method_host_logtime ;
EXECUTE stmt_sql_index_logTime ;
END;

########################总入口存储过程
CREATE   PROCEDURE `bak_tables`()
BEGIN
DECLARE ss DATETIME DEFAULT now();
DECLARE ee DATETIME;
CALL bak_history_annotation();
CALL bak_history_annotation_web();
CALL bak_history_bus_log();
CALL bak_history_req_stat();
set ee=now();
select ee,ss,ee-ss from dual;
END;

########################定时任务，每月1号凌晨0点2分执行，调用入口存储过程(bak_tables)
########################定时任务执行时间间隔决定保留多长时间的数据
CREATE EVENT event_bak
on schedule every 1 month starts '2016-06-01 00:02:00' ON COMPLETION PRESERVE
DO CALL bak_tables();

