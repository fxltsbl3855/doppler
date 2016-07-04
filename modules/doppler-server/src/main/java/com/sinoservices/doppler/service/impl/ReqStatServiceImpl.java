package com.sinoservices.doppler.service.impl;

import com.sinoservices.doppler.Constant;
import com.sinoservices.doppler.assimble.ServiceAssimble;
import com.sinoservices.doppler.bo.*;
import com.sinoservices.doppler.dao.AnnotationMapper;
import com.sinoservices.doppler.dao.AppMapper;
import com.sinoservices.doppler.dao.ReqStatMapper;
import com.sinoservices.doppler.dao.ServiceMapper;
import com.sinoservices.doppler.entity.AppDB;
import com.sinoservices.doppler.entity.ReqStatDB;
import com.sinoservices.doppler.entity.ServiceDB;
import com.sinoservices.doppler.service.AppService;
import com.sinoservices.doppler.service.ReqStatService;
import com.sinoservices.doppler.service.ServiceService;
import com.sinoservices.doppler.service.assimble.ReqStatAssimble;
import com.sinoservices.doppler.zk.RegisterInfo;
import com.sinoservices.util.DateUtil;
import com.sinoservices.util.JsonUtils;
import com.sinoservices.util.ST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2016/1/21.
 */
@Service("reqStatService")
public class ReqStatServiceImpl implements ReqStatService {
    private static final Logger logger = LoggerFactory.getLogger(ReqStatServiceImpl.class);

    @Autowired
    private ReqStatMapper reqStatMapper;
    @Autowired
    private ServiceMapper serviceMapper;

    @Override
    public List<SpanViewBo> getReqNumByHour(String date, int serviceIdInt, String method) {
        logger.debug("getReqNumByHour invked,date="+date+",serviceIdInt="+serviceIdInt+",method="+method);
        Date startDate = DateUtil.stirng2Date(date,"yyyy-MM-dd");
        Date endDate = DateUtil.toTommorow(startDate);
        List<ReqStatDB> hourList = reqStatMapper.getReqNumByHour(startDate.getTime(),endDate.getTime(),serviceIdInt,method);
        List<SpanViewBo> resList = ReqStatAssimble.transferToSpanViewBoList(date,hourList);
        logger.debug("getReqNumByHour res = "+ JsonUtils.object2Json(resList));
        return resList;
    }

    @Override
    public List<ServiceStatBo> getMethodStatByAppId(Date startDate,int serviceId) {
        logger.debug("getMethodStatByAppId invked,startDate="+startDate+",serviceId="+serviceId);
        Date endDate = DateUtil.toTommorow(startDate);
        List<ReqStatDB> methodStatList = reqStatMapper.getMethodStat(startDate.getTime(),endDate.getTime(),serviceId);
        List<ServiceStatBo> resList = ReqStatAssimble.transferToServiceStatBoList(startDate,methodStatList);
        logger.debug("getMethodStatByAppId res = "+ JsonUtils.object2Json(resList));
        return resList;
    }

    @Override
    public List<AppStatBo> getAppDetailList(Date startDate) {
        logger.debug("getAppDetailList invked,startDate="+startDate);
        List<AppStatBo> resultList = new ArrayList<AppStatBo>(8);
        Date endDate = DateUtil.toTommorow(startDate);

        List<ReqStatDB> webStat = reqStatMapper.getWebAppReqStat(startDate.getTime(),endDate.getTime());
        ReqStatAssimble.addToAppStatBoList(resultList,webStat);

        List<ReqStatDB> serverStat = reqStatMapper.getServerAppReqStat(startDate.getTime(),endDate.getTime());
        List<ServiceDB> appServiceInfoList = serviceMapper.getAppServiceByServiceId(ReqStatAssimble.getServiceIdStr(serverStat));
        List<ReqStatDB> newServerList = ReqStatAssimble.groupByAppIdName(serverStat,appServiceInfoList);
        ReqStatAssimble.addToAppStatBoList(resultList,newServerList);
        logger.debug("getAppDetailList res = "+ JsonUtils.object2Json(resultList));
        return resultList;
    }


    @Override
    public DashboardBo getDashboard() {
        logger.debug("getDashboard invked");
        int webNum = 0;
        int serverNum = 0;
        int webReqNum = 0;
        int serverReqNum = 0;
        List<AppReq> webReqList = new ArrayList<AppReq>();
        List<AppReq> serverReqList = new ArrayList<AppReq>();

        List<ReqStatDB> webStat = reqStatMapper.getWebAppReqStatService();
        webNum = webStat==null?0:webStat.size();
        webReqNum = ReqStatAssimble.addToAppReqList(webReqList,webStat);

        List<ReqStatDB> serverStat = reqStatMapper.getServerAppReqStatService();
        List<ServiceDB> appServiceInfoList = serviceMapper.getAppServiceByServiceId(ReqStatAssimble.getServiceIdStr(serverStat));
        List<ReqStatDB> newServerList = ReqStatAssimble.groupByAppIdName(serverStat,appServiceInfoList);
        serverNum = newServerList.size();
        serverReqNum = ReqStatAssimble.addToAppReqList(serverReqList,newServerList);

        DashboardBo dashboardBo = new DashboardBo();
        dashboardBo.setWebNum(webNum);
        dashboardBo.setServerNum(serverNum);
        dashboardBo.setWebReqNum(webReqNum);
        dashboardBo.setServerReqNum(serverReqNum);
        dashboardBo.setWebReqList(webReqList);
        dashboardBo.setServerReqList(serverReqList);
        logger.debug("getDashboard res = "+ JsonUtils.object2Json(dashboardBo));
        return dashboardBo;
    }


}
