package com.sinoservices.doppler.facade.impl;

import com.sinoservices.doppler.assimble.ServiceAssimble;
import com.sinoservices.doppler.bo.DashboardBo;
import com.sinoservices.doppler.bo.ServiceBo;
import com.sinoservices.doppler.bo.ServiceStatBo;
import com.sinoservices.doppler.bo.SpanViewBo;
import com.sinoservices.doppler.check.ParamCheck;
import com.sinoservices.doppler.entity.AppDB;
import com.sinoservices.doppler.entity.ServiceDB;
import com.sinoservices.doppler.exception.RunTaskException;
import com.sinoservices.doppler.facade.ServiceFacade;
import com.sinoservices.doppler.service.ReqStatService;
import com.sinoservices.doppler.service.ServiceService;
import com.sinoservices.util.DateUtil;
import com.sinoservices.util.JsonUtils;
import com.sinoservices.util.NumberUtil;
import com.sinoservices.util.ST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/1/20.
 */
@Service("serviceFacade")
public class ServiceFacadeImpl implements ServiceFacade {
    private static Logger logger = LoggerFactory.getLogger(ServiceFacadeImpl.class);

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private ReqStatService reqStatService;

    public List<ServiceBo> getServiceList(int appId) {
        ST.start();
        logger.debug("getServiceList invoke .appId={}", appId);
        if(appId <= 0){
            return Collections.emptyList();
        }
        List<ServiceDB> list = serviceService.getServiceByAppId(appId);
        List<ServiceBo> res = ServiceAssimble.serviceDB2Bo(list);
        logger.debug("getServiceList={}",JsonUtils.object2Json(res));
        ST.stop();
        return res;
    }

    public List<ServiceStatBo> getServiceDetailList(String dateStr , String serviceId) {
        ST.start();
        logger.debug("getServiceDetailList invoke .date={},serviceId={}", dateStr,serviceId);
        if(ParamCheck.isEmpty(dateStr)||ParamCheck.isEmpty(serviceId)){
            return Collections.emptyList();
        }
        Date date = DateUtil.stirng2Date(dateStr,"yyyy-MM-dd");
        if(date == null){
            logger.error("getServiceDetailList param date is invalid");
            return Collections.emptyList();
        }
        int serviceIdInt = NumberUtil.formatNumber(serviceId,-1);
        if(serviceIdInt <= 0){
            logger.error("getServiceDetailList param serviceId is invalid");
            return Collections.emptyList();
        }
        List<ServiceStatBo> resList = reqStatService.getMethodStatByAppId(date, serviceIdInt);
        logger.debug("getServiceDetailList result = {}", JsonUtils.object2Json(resList));
        ST.stop();
        return resList;
    }

    public List<SpanViewBo> getServiceChart(String spanViewId, String date) {
        ST.start();
        logger.debug("getServiceChart invoke .spanViewId={},date={}", spanViewId,date);
        if(ParamCheck.isEmpty(spanViewId)||ParamCheck.isEmpty(date)){
            return Collections.emptyList();
        }
        String[] strs = (spanViewId+" ").split("#");
        String serviceId = strs[0].trim();
        String method = strs[1].trim();
        int serviceIdInt = NumberUtil.formatNumber(serviceId,-1);
        if(serviceIdInt == -1){
            logger.error("getServiceChart spanViewId.serviceId is not number");
            return Collections.emptyList();
        }


        List<SpanViewBo> resList = reqStatService.getReqNumByHour(date,serviceIdInt,method);
        logger.debug("getServiceChart={}",JsonUtils.object2Json(resList));
        ST.stop();
        return resList;
    }

    public static void main(String[] a){

    }


}
