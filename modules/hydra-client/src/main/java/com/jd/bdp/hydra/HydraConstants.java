package com.jd.bdp.hydra;

/**
 * Created by Administrator on 2016/1/18.
 */
public class HydraConstants {

    /** 对service调用的上游（1） 或 下游（2） **/
    public static final int SERVICE_INVOKE_ROLE_UP = 1;
    public static final int SERVICE_INVOKE_ROLE_DOWN = 2;

    /**
     * 跟hydra-client-web中HydraWebConstant的值相同
     */
    public static final String TRACE_ID = "TRACE_ID";
    public static final String PARENT_SPAN_ID = "PARENT_SPAN_ID";
    public static final String SERVICE_ID = "SERVICE_ID";

}
