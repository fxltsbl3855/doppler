package com.sinoservices.doppler.bo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class ReqDetailBo implements Serializable {

    private Long   spanId;
    private String sourceAppName;
    private String sourceServiceName;
    private String sourceAddrName;
    private String targetAppName;
    private String targetServiceName;
    private String targetAddrName;
    private String errorAddr;
    private String errorTime;
    private String errorInfo;

    public ReqDetailBo(){

    }

    public ReqDetailBo(long spanId){
        this.spanId = spanId;
    }


    public Long getSpanId() {
        return spanId;
    }

    public void setSpanId(Long spanId) {
        this.spanId = spanId;
    }

    public String getSourceAppName() {
        return sourceAppName;
    }

    public void setSourceAppName(String sourceAppName) {
        this.sourceAppName = sourceAppName;
    }

    public String getSourceServiceName() {
        return sourceServiceName;
    }

    public void setSourceServiceName(String sourceServiceName) {
        this.sourceServiceName = sourceServiceName;
    }

    public String getSourceAddrName() {
        return sourceAddrName;
    }

    public void setSourceAddrName(String sourceAddrName) {
        this.sourceAddrName = sourceAddrName;
    }

    public String getTargetAppName() {
        return targetAppName;
    }

    public void setTargetAppName(String targetAppName) {
        this.targetAppName = targetAppName;
    }

    public String getTargetServiceName() {
        return targetServiceName;
    }

    public void setTargetServiceName(String targetServiceName) {
        this.targetServiceName = targetServiceName;
    }

    public String getTargetAddrName() {
        return targetAddrName;
    }

    public void setTargetAddrName(String targetAddrName) {
        this.targetAddrName = targetAddrName;
    }

    public String getErrorAddr() {
        return errorAddr;
    }

    public void setErrorAddr(String errorAddr) {
        this.errorAddr = errorAddr;
    }

    public String getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(String errorTime) {
        this.errorTime = errorTime;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
