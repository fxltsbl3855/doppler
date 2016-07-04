package com.jd.bdp.hydra.bo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class TraceBo {
    private Long traceId;
    private Long parentSpanId;

    public TraceBo(){

    }

    public TraceBo(Long traceId,Long parentSpanId){
        this.traceId = traceId;
        this.parentSpanId = parentSpanId;
    }

    public Long getTraceId() {
        return traceId;
    }

    public void setTraceId(Long traceId) {
        this.traceId = traceId;
    }

    public Long getParentSpanId() {
        return parentSpanId;
    }

    public void setParentSpanId(Long parentSpanId) {
        this.parentSpanId = parentSpanId;
    }
}
