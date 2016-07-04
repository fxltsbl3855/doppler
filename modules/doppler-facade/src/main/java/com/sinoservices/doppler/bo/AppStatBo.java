package com.sinoservices.doppler.bo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/20.
 */
public class AppStatBo implements Serializable {
    private int appId;
    private String appName;
    private int reqNum;
    private int timeoutNum;
    private double timeoutPercent;
    private int errorNum;
    private double errorPercent;

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
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

    public double getTimeoutPercent() {
        return timeoutPercent;
    }

    public void setTimeoutPercent(double timeoutPercent) {
        this.timeoutPercent = timeoutPercent;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    public double getErrorPercent() {
        return errorPercent;
    }

    public void setErrorPercent(double errorPercent) {
        this.errorPercent = errorPercent;
    }
}
