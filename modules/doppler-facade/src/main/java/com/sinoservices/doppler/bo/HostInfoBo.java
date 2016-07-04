package com.sinoservices.doppler.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/17.
 */
public class HostInfoBo implements Serializable {

    private List<String> appNameList = new ArrayList<String>();
    private String hostName;

    public HostInfoBo(){}

    public HostInfoBo(String appName,String hostName){
        appNameList.add(appName);
        this.hostName = hostName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public List<String> getAppNameList() {
        return appNameList;
    }

    public void setAppNameList(List<String> appNameList) {
        this.appNameList = appNameList;
    }
}
