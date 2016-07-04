package com.sinoservices.doppler.bo;

import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class DisplayServiceBo implements Serializable {


    /**
     * <sericeId,appName>
     */
    private Map<Integer,String> serviceMap;


    public Map<Integer, String> getServiceMap() {
        return serviceMap;
    }

    public void setServiceMap(Map<Integer, String> serviceMap) {
        this.serviceMap = serviceMap;
    }


}
