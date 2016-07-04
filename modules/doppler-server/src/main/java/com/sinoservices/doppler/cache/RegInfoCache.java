package com.sinoservices.doppler.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class RegInfoCache {
    public static int MAX_NUM = 1000;

    private static Map<String,RegInfo> regInfoNameMap = new HashMap<String, RegInfo>();
    private static Map<Integer,RegInfo> regInfoIdMap = new HashMap<Integer, RegInfo>();

    public static RegInfo getByServiceId(Integer serviceId){
        return regInfoIdMap.get(serviceId);
    }

    public static RegInfo getByServiceName(String serviceName){
        return regInfoNameMap.get(serviceName);
    }

    public static void putByServiceId(RegInfo regInfo){
        if(regInfoNameMap.size() > MAX_NUM || regInfoIdMap.size() > MAX_NUM){
            return ;
        }
        regInfoNameMap.put(regInfo.getServiceName(),regInfo);
        regInfoIdMap.put(regInfo.getServiceId(),regInfo);
    }
}
