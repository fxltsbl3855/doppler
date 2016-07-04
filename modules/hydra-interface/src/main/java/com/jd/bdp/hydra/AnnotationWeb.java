package com.jd.bdp.hydra;

import java.io.Serializable;

/**
 * Date: 13-3-18
 * Time: 下午3:36
 */
public class AnnotationWeb implements Serializable {
    private String k;
    private Long timestamp;
    private String ip;
    private int port;
    private Long traceId;
    private Long spanId;
    private String appName;
    private String serviceName;

    private Integer duration;

    private int appId;
    private String serviceId;
    private String value;

    public AnnotationWeb(){
    }
    public AnnotationWeb(String k,Long timestamp, String ip,int port) {
        this.timestamp = timestamp;
        this.k = k;
        this.ip = ip;
        this.port = port;
    }

    public Long getSpanId() {
        return spanId;
    }

    public void setSpanId(Long spanId) {
        this.spanId = spanId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Long getTraceId() {
        return traceId;
    }

    public void setTraceId(Long traceId) {
        this.traceId = traceId;
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

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
