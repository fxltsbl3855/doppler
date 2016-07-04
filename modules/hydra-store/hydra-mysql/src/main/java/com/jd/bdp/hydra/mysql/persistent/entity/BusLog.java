package com.jd.bdp.hydra.mysql.persistent.entity;

import com.jd.bdp.hydra.Annotation;
import com.jd.bdp.hydra.BinaryAnnotation;
import com.jd.bdp.hydra.Span;

/**
 * Date: 13-5-7
 * Time: 上午10:54
 */
public class BusLog {

    /**
     * 请求跟踪日志(1)/打点日志(2)
     */
    public static final int LOG_TYPE_TRACE = 1;
    public static final int LOG_TYPE_OP = 2;

    /**
     * 数字越大,级别越高
     * 10:DEBUG,输出日志 ，用于输出有用信息
     20:WARN 警告日志，用于提示，需要注意
     30:ERROR错误日志，需要修改
     40:FETAL致命错误
     */
    public static final int ERROR_TYPE_DEBUG = 10;
    public static final int ERROR_TYPE_WARN = 20;
    public static final int ERROR_TYPE_ERROR = 30;
    public static final int ERROR_TYPE_FETAL = 40;

    private String addr;
    private Long spanId;
    private Long traceId;
    private Integer appId;
    private Integer serviceId;
    private String logInfo;
    private Integer logType;
    private Integer errorType;
    private Long logTime;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Long getSpanId() {
        return spanId;
    }

    public void setSpanId(Long spanId) {
        this.spanId = spanId;
    }

    public Long getTraceId() {
        return traceId;
    }

    public void setTraceId(Long traceId) {
        this.traceId = traceId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public Integer getErrorType() {
        return errorType;
    }

    public void setErrorType(Integer errorType) {
        this.errorType = errorType;
    }

    public Long getLogTime() {
        return logTime;
    }

    public void setLogTime(Long logTime) {
        this.logTime = logTime;
    }
}
