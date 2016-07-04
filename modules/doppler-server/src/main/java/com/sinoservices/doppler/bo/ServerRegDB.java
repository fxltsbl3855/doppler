package com.sinoservices.doppler.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/17.
 */
public class ServerRegDB {

    /**
     *服务对应的host列表
     * host包含端口
     */
    private List<String> hostList = new ArrayList<String>();
    /**
     * 服务对应的应用名称
     */
    private String app="";


    public void addHost(String host){
        if(hostList == null ){
            init();
        }
        //if(!hostList.contains(host))
            hostList.add(host);
    }


    public List<String> getHostList() {
        return hostList;
    }

    public void setHostList(List<String> hostList) {
        this.hostList = hostList;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public void init(){
        hostList = new ArrayList<String>();
    }


}
