package com.jd.bdp.hydra.mysql.persistent.entity;

/**
 * Date: 13-5-7
 * Time: 上午10:54
 */
public class ReqStat {
    private int appId;
    private int appType;  //web app 还是server  1:webapp; 2:server
    private int serviceId;
    private String method;
    private String host;
    private Long logTime;

    private Integer reqNum;
    private Integer timeoutNum;
    private Integer errorNum;
    private Integer duration;

    public ReqStat(int appType){
        this.appType = appType;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getLogTime() {
        return logTime;
    }

    public void setLogTime(Long logTime) {
        this.logTime = logTime;
    }

    public Integer getReqNum() {
        return reqNum;
    }

    public void setReqNum(Integer reqNum) {
        this.reqNum = reqNum;
    }

    public Integer getTimeoutNum() {
        return timeoutNum;
    }

    public void setTimeoutNum(Integer timeoutNum) {
        this.timeoutNum = timeoutNum;
    }

    public Integer getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(Integer errorNum) {
        this.errorNum = errorNum;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
