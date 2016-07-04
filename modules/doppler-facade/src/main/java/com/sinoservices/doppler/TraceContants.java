package com.sinoservices.doppler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class TraceContants {

    /**
     * 数字越大,级别越高
     * 10:DEBUG,输出日志 ，用于输出有用信息
     20:WARN 警告日志，用于提示，需要注意
     30:ERROR错误日志，需要修改
     40:FETAL致命错误
     */
    public static final int ERROR_TYPE_INFO = 10;
    public static final int ERROR_TYPE_WARN = 20;
    public static final int ERROR_TYPE_ERROR = 30;
    public static final int ERROR_TYPE_FETAL = 40;

    /**
     * 日志类型（请求跟踪日志(1)/打点日志(2)）
     */
    public static final int LOG_TYPE_TRACE = 1;
    public static final int LOG_TYPE_BUSLOG = 2;


    public static final Map<Integer,String> ERROR_TYPE_MAP = new HashMap<Integer,String>(4);

    public static String getErrorTypeStr(Integer errorType){
        if(ERROR_TYPE_MAP.containsKey(errorType)){
            return ERROR_TYPE_MAP.get(errorType);
        }
        return "unknown";
    }

    static {
        ERROR_TYPE_MAP.put(10,"INFO");
        ERROR_TYPE_MAP.put(20,"WARN");
        ERROR_TYPE_MAP.put(30,"ERROR");
        ERROR_TYPE_MAP.put(40,"FETAL");
    }

}
