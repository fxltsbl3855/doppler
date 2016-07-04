package com.sinoservices.doppler.bo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/24.
 */
public class DashboardBo implements Serializable {

    private int webNum;
    private int serverNum;
    private int webReqNum;
    private int serverReqNum;

    public DashboardBo(){

    }
    public DashboardBo(int webNum,int serverNum){

    }
    List<AppReq> webReqList;
    List<AppReq> serverReqList;

    public int getWebNum() {
        return webNum;
    }

    public void setWebNum(int webNum) {
        this.webNum = webNum;
    }

    public int getServerNum() {
        return serverNum;
    }

    public void setServerNum(int serverNum) {
        this.serverNum = serverNum;
    }

    public int getWebReqNum() {
        return webReqNum;
    }

    public void setWebReqNum(int webReqNum) {
        this.webReqNum = webReqNum;
    }

    public int getServerReqNum() {
        return serverReqNum;
    }

    public void setServerReqNum(int serverReqNum) {
        this.serverReqNum = serverReqNum;
    }

    public List<AppReq> getWebReqList() {
        return webReqList;
    }

    public void setWebReqList(List<AppReq> webReqList) {
        this.webReqList = webReqList;
    }

    public List<AppReq> getServerReqList() {
        return serverReqList;
    }

    public void setServerReqList(List<AppReq> serverReqList) {
        this.serverReqList = serverReqList;
    }
}
