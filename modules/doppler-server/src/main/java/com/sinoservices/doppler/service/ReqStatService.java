package com.sinoservices.doppler.service;

import com.sinoservices.doppler.bo.*;
import com.sinoservices.doppler.entity.AppDB;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/1/21.
 */
public interface ReqStatService {

    DashboardBo getDashboard();

    List<AppStatBo> getAppDetailList(Date date);

    List<ServiceStatBo> getMethodStatByAppId(Date date, int serviceId);

    List<SpanViewBo> getReqNumByHour(String date, int serviceIdInt, String method);
}
