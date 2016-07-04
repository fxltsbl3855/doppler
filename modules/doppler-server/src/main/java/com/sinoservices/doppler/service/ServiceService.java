package com.sinoservices.doppler.service;

import com.sinoservices.doppler.bo.*;
import com.sinoservices.doppler.entity.AppDB;
import com.sinoservices.doppler.entity.ServiceDB;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/21.
 */
public interface ServiceService {

    Map<Integer,AppStatBo> getAnnoApp(String date);

    Map<Integer,AppStatBo> getAnnoWebApp(String date);

    List<ServiceDB> getServiceByAppId(int appId);

    List<ServiceStatBo> getAnnoSpan(String date, int appId, String serviceId);

    List<ServiceStatBo> getAnnoSpanWeb(String date, int appId, String serviceId);

    AppDB getAppByServiceId(String serviceId);

    List<SpanViewBo> getReqNumByHour(String date,int appId, String serviceId, String method);

}
