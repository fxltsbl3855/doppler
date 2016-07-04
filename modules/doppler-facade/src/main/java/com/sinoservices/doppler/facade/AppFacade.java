package com.sinoservices.doppler.facade;

import com.sinoservices.doppler.bo.*;

import java.util.List;

/**
 * Created by Administrator on 2016/1/20.
 */
public interface AppFacade {
    /**
     *获取每个项目的统计信息（请求量，超时，错误）
     * @param date
     * @return
     */
    List<AppStatBo> getAppDetailList(String date);

    /**
     * dashboard数据，web，server应用数量，请求总量
     * @return
     */
    DashboardBo getDashboard();

    List<AppInfoBo> getAppInfo();

    List<ServiceInfoBo> getServiceInfo();

    List<HostInfoBo> getHostInfo();
}
