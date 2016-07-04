#######################备份历史数据脚本，每30（30可以在event中设置）天执行一次，数据库只保留当天数据，适合中小数据量情况下
#######################建议数据库每天产生的记录数在700万之下用当前备份版本
################annotation备份#################
CREATE  PROCEDURE `bak_interval_annotation`(IN intervalDays INT)
BEGIN

DECLARE source_table VARCHAR(40);
DECLARE source_column VARCHAR(500);
DECLARE sql_create_table_data VARCHAR(2000);
DECLARE sql_delete_data VARCHAR(2000);
DECLARE NEWNAME_table VARCHAR(40);
DECLARE intervalDaysMillSecs bigint(20);

SET source_table='annotation';
SET source_column='id,k,value,ip,port,timestamp,duration,spanId,traceId,service';
SET NEWNAME_table=CONCAT(source_table,'_',DATE_FORMAT(DATE_SUB(CURDATE(),INTERVAL intervalDays DAY),'%Y_%m_%d'));
SET intervalDaysMillSecs=unix_timestamp(DATE_SUB(CURDATE(),INTERVAL intervalDays DAY))*1000;

SET sql_create_table_data=CONCAT('create table ',NEWNAME_table,' (select ',source_column,' from ',source_table,' where timestamp < ',intervalDaysMillSecs,')');
SET sql_delete_data=CONCAT('delete from ',source_table,' where timestamp < ',intervalDaysMillSecs);

SET @sql_create_table_data=sql_create_table_data;
SET @sql_delete_data=sql_delete_data;

PREPARE stmt_sql_create_table_data FROM @sql_create_table_data;
PREPARE stmt_sql_delete_data FROM @sql_delete_data;

EXECUTE stmt_sql_create_table_data;
EXECUTE stmt_sql_delete_data;

END;

################annotation_web备份#################
CREATE  PROCEDURE `bak_interval_annotation_web`(IN intervalDays INT)
BEGIN

DECLARE source_table VARCHAR(40);
DECLARE source_column VARCHAR(500);
DECLARE sql_create_table_data VARCHAR(2000);
DECLARE sql_delete_data VARCHAR(2000);
DECLARE NEWNAME_table VARCHAR(40);
DECLARE intervalDaysMillSecs bigint(20);

SET source_table='annotation_web';
SET source_column='id,k,value,ip,port,timestamp,duration,traceId,appId,serviceId,appName,serviceName,spanId';
SET NEWNAME_table=CONCAT(source_table,'_',DATE_FORMAT(DATE_SUB(CURDATE(),INTERVAL intervalDays DAY),'%Y_%m_%d'));
SET intervalDaysMillSecs=unix_timestamp(DATE_SUB(CURDATE(),INTERVAL intervalDays DAY))*1000;

SET sql_create_table_data=CONCAT('create table ',NEWNAME_table,' (select ',source_column,' from ',source_table,' where timestamp < ',intervalDaysMillSecs,')');
SET sql_delete_data=CONCAT('delete from ',source_table,' where timestamp < ',intervalDaysMillSecs);

SET @sql_create_table_data=sql_create_table_data;
SET @sql_delete_data=sql_delete_data;

PREPARE stmt_sql_create_table_data FROM @sql_create_table_data;
PREPARE stmt_sql_delete_data FROM @sql_delete_data;

EXECUTE stmt_sql_create_table_data;
EXECUTE stmt_sql_delete_data;

END;

################bus_log备份#################
CREATE  PROCEDURE `bak_interval_bus_log`(IN intervalDays INT)
BEGIN

DECLARE source_table VARCHAR(40);
DECLARE source_column VARCHAR(500);
DECLARE sql_create_table_data VARCHAR(2000);
DECLARE sql_delete_data VARCHAR(2000);
DECLARE NEWNAME_table VARCHAR(40);
DECLARE intervalDaysMillSecs bigint(20);

SET source_table='bus_log';
SET source_column='id,addr,spanId,traceId,appId,serviceId,logInfo,logType,errorType,logTime';
SET NEWNAME_table=CONCAT(source_table,'_',DATE_FORMAT(DATE_SUB(CURDATE(),INTERVAL intervalDays DAY),'%Y_%m_%d'));
SET intervalDaysMillSecs=unix_timestamp(DATE_SUB(CURDATE(),INTERVAL intervalDays DAY))*1000;

SET sql_create_table_data=CONCAT('create table ',NEWNAME_table,' (select ',source_column,' from ',source_table,' where logTime < ',intervalDaysMillSecs,')');
SET sql_delete_data=CONCAT('delete from ',source_table,' where logTime < ',intervalDaysMillSecs);

SET @sql_create_table_data=sql_create_table_data;
SET @sql_delete_data=sql_delete_data;

PREPARE stmt_sql_create_table_data FROM @sql_create_table_data;
PREPARE stmt_sql_delete_data FROM @sql_delete_data;

EXECUTE stmt_sql_create_table_data;
EXECUTE stmt_sql_delete_data;

END;

################req_stat备份#################
CREATE  PROCEDURE `bak_interval_req_stat`(IN intervalDays INT)
BEGIN

DECLARE source_table VARCHAR(40);
DECLARE source_column VARCHAR(500);
DECLARE sql_create_table_data VARCHAR(2000);
DECLARE sql_delete_data VARCHAR(2000);
DECLARE NEWNAME_table VARCHAR(40);
DECLARE intervalDaysMillSecs bigint(20);

SET source_table='req_stat';
SET source_column='id,appId,appType,serviceId,method,host,logTime,reqNum,timeoutNum,errorNum,duration';
SET NEWNAME_table=CONCAT(source_table,'_',DATE_FORMAT(DATE_SUB(CURDATE(),INTERVAL intervalDays DAY),'%Y_%m_%d'));
SET intervalDaysMillSecs=unix_timestamp(DATE_SUB(CURDATE(),INTERVAL intervalDays DAY))*1000;

SET sql_create_table_data=CONCAT('create table ',NEWNAME_table,' (select ',source_column,' from ',source_table,' where logTime < ',intervalDaysMillSecs,')');
SET sql_delete_data=CONCAT('delete from ',source_table,' where logTime < ',intervalDaysMillSecs);

SET @sql_create_table_data=sql_create_table_data;
SET @sql_delete_data=sql_delete_data;

PREPARE stmt_sql_create_table_data FROM @sql_create_table_data;
PREPARE stmt_sql_delete_data FROM @sql_delete_data;

EXECUTE stmt_sql_create_table_data;
EXECUTE stmt_sql_delete_data;

END;

########################总入口存储过程
CREATE  PROCEDURE `bak_tables_interval`(IN intervalDays INT)
BEGIN
DECLARE ss DATETIME DEFAULT now();
DECLARE ee DATETIME;
CALL bak_interval_annotation(intervalDays);
CALL bak_interval_annotation_web(intervalDays);
CALL bak_interval_bus_log(intervalDays);
CALL bak_interval_req_stat(intervalDays);
set ee=now();
select ee,ss,ee-ss from dual;
END;

########################定时任务，每天(或周)凌晨1点执行，调用入口存储过程(bak_tables_interval)
########################保留30天之内的数据，30（可根据数据大小来修改）天之外的数据会定期备份到数据表中，并且从原表中删除
CREATE EVENT event_bak_interval
on schedule every 30 day starts '2016-06-14 00:01:00' ON COMPLETION PRESERVE
DO CALL bak_tables_interval(30);

