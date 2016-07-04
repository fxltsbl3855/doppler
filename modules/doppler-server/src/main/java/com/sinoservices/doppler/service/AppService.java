package com.sinoservices.doppler.service;

import com.sinoservices.doppler.bo.*;
import com.sinoservices.doppler.entity.AppDB;

import java.util.List;

/**
 * Created by Administrator on 2016/1/21.
 */
public interface AppService {
    List<AppStatBo> getAppDetailList(String date);

    List<AppDB> getAppAll();

    DashboardBo getDashboard();

    List<AppInfoBo> getAppInfoBo();

    List<ServiceInfoBo> getServiceInfo();

    List<HostInfoBo> getHostInfo();


}
