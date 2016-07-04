package com.sinoservices.doppler.bo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/20.
 */
public class SpanViewBo implements Serializable {

    private int reqNum; //当前小时的请求量
    private String date; //日期，到小时 yyyy-MM-dd HH

    public int getReqNum() {
        return reqNum;
    }

    public void setReqNum(int reqNum) {
        this.reqNum = reqNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
