package com.sinoservices.doppler.entity;

/**
 * Created by Administrator on 2016/1/22.
 */
public class ReqStatDB {

    private int appId;
    private int serviceId;
    private String appName;
    private String method;
    private long logTime;

    private int reqNum;
    private int timeoutNum;
    private int errorNum;
    private int duration ;

    public long getLogTime() {
        return logTime;
    }

    public void setLogTime(long logTime) {
        this.logTime = logTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getReqNum() {
        return reqNum;
    }

    public void setReqNum(int reqNum) {
        this.reqNum = reqNum;
    }

    public int getTimeoutNum() {
        return timeoutNum;
    }

    public void setTimeoutNum(int timeoutNum) {
        this.timeoutNum = timeoutNum;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
