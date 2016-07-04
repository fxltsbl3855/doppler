package com.sinoservices.doppler.bo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/20.
 */
public class ServiceBo implements Serializable {

    private String serviceId; //服务ID
    private String name; //服务名称

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
