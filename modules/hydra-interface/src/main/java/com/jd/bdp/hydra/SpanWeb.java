package com.jd.bdp.hydra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 13-3-18
 * Time: 下午3:29
 */
public class SpanWeb implements Serializable {
    private Long traceId;
    private Long parentId;
    private Long id;

    private String appName;
    private String serviceName;
    private List<AnnotationWeb> annotationWebs;
    private List<BinaryAnnotationWeb> binaryAnnotationWebs;

    private String serviceId;



    public SpanWeb(){
        annotationWebs = new ArrayList<AnnotationWeb>();
        binaryAnnotationWebs = new ArrayList<BinaryAnnotationWeb>();
    }
    public SpanWeb(Long traceId,Long parentId ,Long id){
        this.traceId = traceId;
        this.parentId = parentId;
        this.id = id;
        annotationWebs = new ArrayList<AnnotationWeb>();
        binaryAnnotationWebs = new ArrayList<BinaryAnnotationWeb>();
    }

    public Long getTraceId() {
        return traceId;
    }

    public void setTraceId(Long traceId) {
        this.traceId = traceId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addAnnotationWeb(AnnotationWeb a){
        annotationWebs.add(a);
    }
    public void addBinaryAnnotation(BinaryAnnotationWeb a){
        binaryAnnotationWebs.add(a);
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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

    public List<AnnotationWeb> getAnnotationWebs() {
        return annotationWebs;
    }

    public void setAnnotationWebs(List<AnnotationWeb> annotationWebs) {
        this.annotationWebs = annotationWebs;
    }

    public List<BinaryAnnotationWeb> getBinaryAnnotationWebs() {
        return binaryAnnotationWebs;
    }

    public void setBinaryAnnotationWebs(List<BinaryAnnotationWeb> binaryAnnotationWebs) {
        this.binaryAnnotationWebs = binaryAnnotationWebs;
    }

}
