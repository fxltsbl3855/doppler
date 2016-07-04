package com.sinoservices.doppler.entity;

/**
 * Created by Administrator on 2016/1/22.
 */
public class AnnoServiceDB {

    private int serviceId;
    private String serviceName;
    private int appId;
    private int invokeRole;
    private int num;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getInvokeRole() {
        return invokeRole;
    }

    public void setInvokeRole(int invokeRole) {
        this.invokeRole = invokeRole;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
