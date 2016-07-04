package com.sinoservices.doppler.service;

import com.sinoservices.doppler.bo.*;
import com.sinoservices.doppler.entity.AnnotationDB;
import com.sinoservices.doppler.entity.AppDB;

import java.util.List;

/**
 * Created by Administrator on 2016/1/21.
 */
public interface RequestService {


    DisplayItemBo getDisplayItem();

    DisplayServiceBo getServicesByAppId(int appId);

    List<RequestBo> getReqInfo(String startDate, String endDate, int appId, int serviceId, int errorLevel, String addr, String key);

    ReqDetailBo getAppDetailById(int id);
}
