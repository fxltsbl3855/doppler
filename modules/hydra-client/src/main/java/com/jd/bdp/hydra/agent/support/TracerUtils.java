package com.jd.bdp.hydra.agent.support;

import com.alibaba.dubbo.common.utils.StringUtils;

/**

 * Date: 13-3-26
 * Time: 下午7:06
  */
public class TracerUtils {
    public static final String TID = "traceId";
    public static final String SID = "spanId";
    public static final String PID = "parentId";
    public static final String SAMPLE = "isSample";

    public static final String EXCEPTION = "exception.dubbo";
    public static final String EXCEPTION_TIMEOUT = "timeout.exception.dubbo";
    public static final String EXCEPTION_TIMEOUT_VALUE = "TimeoutException";


    public static Long getAttachmentLong(String value){
        if(StringUtils.isBlank(value)){
            return null;
        }
        return Long.valueOf(value);
    }

    public static Boolean getAttachmentBoolean(String value){
        if(value == null){
            return false;
        }
        return Boolean.valueOf(value);
    }
}
