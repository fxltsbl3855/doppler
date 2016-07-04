package com.sinoservices.doppler.facade;

import com.sinoservices.doppler.bo.DashboardBo;
import com.sinoservices.doppler.bo.ServiceBo;
import com.sinoservices.doppler.bo.ServiceStatBo;
import com.sinoservices.doppler.bo.SpanViewBo;

import java.util.List;

/**
 * Created by Administrator on 2016/1/20.
 */
public interface ServiceFacade {

    /**
     * 获取服务接口列表
     * @param appId 应用名称
     * @return
     */
    List<ServiceBo> getServiceList(int appId);

    /**
     * 获取每个服务接口方法的统计信息
     * @param serviceId 服务ID
     * @return
     */
    List<ServiceStatBo> getServiceDetailList(String date,String serviceId);

    /**
     * 画出服务接口方法的走势图
     * @param spanViewId    span ID
     * @param date 日期
     * @return
     */
    List<SpanViewBo> getServiceChart(String spanViewId, String date);


}
