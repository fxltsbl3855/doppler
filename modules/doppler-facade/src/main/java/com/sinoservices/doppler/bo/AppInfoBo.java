package com.sinoservices.doppler.bo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/2/17.
 */
public class AppInfoBo implements Serializable{

    public AppInfoBo(){}

    public AppInfoBo(String appName){
        this.appName = appName;
    }

    private String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
