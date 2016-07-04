package com.sinoservices.doppler.bo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/20.
 */
public class ServiceStatBo implements Serializable {

    private String spanViewId;//服务ID+方法名
    private String spanView; //span名称
    private int reqNum; //总请求量
    private double qps;
    private int maxTime; //ms
    private int timeoutNum;
    private double timeoutPercent;
    private int errorNum;
    private double errorPercent;

    public String getSpanViewId() {
        return spanViewId;
    }

    public void setSpanViewId(String spanViewId) {
        this.spanViewId = spanViewId;
    }

    public String getSpanView() {
        return spanView;
    }

    public void setSpanView(String spanView) {
        this.spanView = spanView;
    }

    public int getReqNum() {
        return reqNum;
    }

    public void setReqNum(int reqNum) {
        this.reqNum = reqNum;
    }

    public double getQps() {
        return qps;
    }

    public void setQps(double qps) {
        this.qps = qps;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
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
