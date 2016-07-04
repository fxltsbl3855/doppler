package com.sinoservices.doppler.bo;

import com.sinoservices.doppler.entity.BusLogDB;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class ErrorMsgBo {

    private long spanId;
    private boolean isServer;

    private int sourceServiceId;
    private String sourceServiceName;
    private int sourceAppId;
    private String sourceAppName;
    private String sourceAddrName;
    Object sourceError;

    private int targetServiceId;
    private String targetServiceName;
    private int targetAppId;
    private String targetAppName;
    private String targetAddrName;

    private String errorAddr;
    private String errorTime;
    private String errorInfo;

    public String getSourceAddrName() {
        return sourceAddrName;
    }

    public void setSourceAddrName(String sourceAddrName) {
        this.sourceAddrName = sourceAddrName;
    }

    public String getTargetAddrName() {
        return targetAddrName;
    }

    public void setTargetAddrName(String targetAddrName) {
        this.targetAddrName = targetAddrName;
    }

    public long getSpanId() {
        return spanId;
    }

    public void setSpanId(long spanId) {
        this.spanId = spanId;
    }

    public boolean isServer() {
        return isServer;
    }

    public void setServer(boolean server) {
        isServer = server;
    }

    public int getSourceServiceId() {
        return sourceServiceId;
    }

    public void setSourceServiceId(int sourceServiceId) {
        this.sourceServiceId = sourceServiceId;
    }

    public String getSourceServiceName() {
        return sourceServiceName;
    }

    public void setSourceServiceName(String sourceServiceName) {
        this.sourceServiceName = sourceServiceName;
    }

    public int getSourceAppId() {
        return sourceAppId;
    }

    public void setSourceAppId(int sourceAppId) {
        this.sourceAppId = sourceAppId;
    }

    public String getSourceAppName() {
        return sourceAppName;
    }

    public void setSourceAppName(String sourceAppName) {
        this.sourceAppName = sourceAppName;
    }

    public Object getSourceError() {
        return sourceError;
    }

    public void setSourceError(Object sourceError) {
        this.sourceError = sourceError;
    }

    public int getTargetServiceId() {
        return targetServiceId;
    }

    public void setTargetServiceId(int targetServiceId) {
        this.targetServiceId = targetServiceId;
    }

    public String getTargetServiceName() {
        return targetServiceName;
    }

    public void setTargetServiceName(String targetServiceName) {
        this.targetServiceName = targetServiceName;
    }

    public int getTargetAppId() {
        return targetAppId;
    }

    public void setTargetAppId(int targetAppId) {
        this.targetAppId = targetAppId;
    }

    public String getTargetAppName() {
        return targetAppName;
    }

    public void setTargetAppName(String targetAppName) {
        this.targetAppName = targetAppName;
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
