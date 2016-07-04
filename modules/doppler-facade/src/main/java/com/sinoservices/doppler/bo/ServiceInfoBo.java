package com.sinoservices.doppler.bo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/2/17.
 */
public class ServiceInfoBo implements Serializable {

    private String appName;
    private String serviceName;

    public ServiceInfoBo(){}

    public ServiceInfoBo(String appName,String serviceName){
        this.appName = appName;
        this.serviceName = serviceName;
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
}
