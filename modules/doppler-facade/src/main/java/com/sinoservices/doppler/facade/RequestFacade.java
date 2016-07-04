package com.sinoservices.doppler.facade;

import com.sinoservices.doppler.bo.*;

import java.util.List;

/**
 * Created by Administrator on 2016/1/20.
 */
public interface RequestFacade {

    /**
     * 应用名称，服务名称，错误级别选项内容查询接口
     * @return
     */
    DisplayItemBo getDisplayItem();

    /**
     * 服务名称选项内容查询接口
     * @param appId
     * @return
     */
    DisplayServiceBo getServicesByAppId(int appId);

    /**
     * 监控查询，包括（高级）查询；根据查询条件查询异常上下文（请求源，请求目标）信息；一条记录一次请求
     * @return
     */
    List<RequestBo> getReqInfo(String startDate, String endDate, int appId, int serviceId, int errorLevel , String Addr, String key);

    /**
     * 获取次请求的上下文信息
     * @param id
     * @return
     */
    ReqDetailBo getAppDetailById(int id);


}
