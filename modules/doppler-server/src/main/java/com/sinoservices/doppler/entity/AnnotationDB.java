package com.sinoservices.doppler.entity;

/**
 * Date: 13-5-7
 * Time: 上午10:54
 */
public class AnnotationDB {
    private Integer id;
    private String k;
    private String value;
    private String ip;
    private Integer port;
    private Long timestamp;
    private Integer duration;
    private Long spanId;
    private Long traceId;
    private String service;

    public AnnotationDB(){

    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getSpanId() {
        return spanId;
    }

    public void setSpanId(Long spanId) {
        this.spanId = spanId;
    }

    public Long getTraceId(){
        return this.traceId;
    }

    public void setTraceId(Long traceId) {
        this.traceId = traceId;
    }


}
