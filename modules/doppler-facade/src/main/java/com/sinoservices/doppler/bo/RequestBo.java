package com.sinoservices.doppler.bo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class RequestBo implements Serializable {

    private int id;
    private String appName;
    private String serviceName;
    private String addr;
    private String errorLevelDisplay;
    private String reqTime;
    private String logInfo;
    private long spanId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getErrorLevelDisplay() {
        return errorLevelDisplay;
    }

    public void setErrorLevelDisplay(String errorLevelDisplay) {
        this.errorLevelDisplay = errorLevelDisplay;
    }



    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    public long getSpanId() {
        return spanId;
    }

    public void setSpanId(long spanId) {
        this.spanId = spanId;
    }
}
