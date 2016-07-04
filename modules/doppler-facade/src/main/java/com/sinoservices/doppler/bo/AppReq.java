package com.sinoservices.doppler.bo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/24.
 */
public class AppReq implements Serializable {

    private String appName;
    private int num;

    public AppReq(){ }

    public AppReq(String appName,int num){
        this.appName = appName;
        this.num = num;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
