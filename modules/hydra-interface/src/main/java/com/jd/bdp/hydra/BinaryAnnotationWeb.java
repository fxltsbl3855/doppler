package com.jd.bdp.hydra;

import java.io.Serializable;

/**
 * Date: 13-3-18
 * Time: 下午3:36
 */
public class BinaryAnnotationWeb implements Serializable {
    private String key;
    private String value;
    private String type;
    private Integer duration;
    private Endpoint host;
    private long traceId;
    private long spanId;

    private Long timestamp;

    private int appId;
    private String serviceId;


    public Endpoint getHost() {
        return host;
    }

    public void setHost(Endpoint endpoint) {
        this.host = endpoint;
    }

    @Override
    public String toString() {
        return "BinaryAnnotation{" +
                "key='" + key + '\'' +
                ", value=" + value +
                ", type='" + type + '\'' +
                ", duration=" + duration +
                ", endpoint=" + host +
                '}';
    }

    public long getSpanId() {
        return spanId;
    }

    public void setSpanId(long spanId) {
        this.spanId = spanId;
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

    public long getTraceId() {
        return traceId;
    }

    public void setTraceId(long traceId) {
        this.traceId = traceId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
