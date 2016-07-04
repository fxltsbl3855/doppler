package com.sinoservices.doppler.bo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class DisplayItemBo implements Serializable {
    /**
     * <appId,appName>
     */
    private Map<Integer,String> appMap;
    /**
     * <errorId,errorName>
     */
    private Map<Integer,String> errorLevelMap;

    public Map<Integer, String> getAppMap() {
        return appMap;
    }

    public void setAppMap(Map<Integer, String> appMap) {
        this.appMap = appMap;
    }


    public Map<Integer, String> getErrorLevelMap() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(10,"INFO");
        map.put(20,"WARN");
        map.put(30,"ERROR");
        map.put(40,"FETAL");
        return map;
    }

    public void setErrorLevelMap(Map<Integer, String> errorLevelMap) {
        this.errorLevelMap = errorLevelMap;
    }
}
