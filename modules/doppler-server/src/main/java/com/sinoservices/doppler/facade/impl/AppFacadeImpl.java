package com.sinoservices.doppler.facade.impl;


import com.sinoservices.doppler.assimble.ServiceAssimble;
import com.sinoservices.doppler.bo.*;
import com.sinoservices.doppler.entity.AppDB;
import com.sinoservices.doppler.facade.AppFacade;
import com.sinoservices.doppler.service.AppService;
import com.sinoservices.doppler.service.ReqStatService;
import com.sinoservices.doppler.service.ServiceService;
import com.sinoservices.util.DateUtil;
import com.sinoservices.util.JsonUtils;
import com.sinoservices.util.ST;
import com.sinoservices.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2016/1/20.
 */
@Service("appFacade")
public class AppFacadeImpl implements AppFacade {

    private static Logger logger = LoggerFactory.getLogger(AppFacadeImpl.class);
    @Autowired
    private AppService appService;
    @Autowired
    private ReqStatService reqStatService;

    public List<AppStatBo> getAppDetailList(String dateStr) {
        ST.start();
        logger.debug(" getAppDetailList invoke .date = {}", dateStr);
        if(StringUtil.isBlank(dateStr)){
            logger.error("getAppDetailList invoke param error date is empty");
            return Collections.emptyList();
        }
        Date date = DateUtil.stirng2Date(dateStr,"yyyy-MM-dd");
        if(date == null ){
            logger.error("getAppDetailList invoke param error date is invalid");
            return Collections.emptyList();
        }
        List<AppStatBo> list = reqStatService.getAppDetailList(date);
        logger.debug("AppFacade.getAppDetailList.resList={}", JsonUtils.object2Json(list));
        ST.stop();
        return list;
    }

    @Override
    public DashboardBo getDashboard() {
        ST.start();
        logger.debug("AppFacade.getDashboard");
        DashboardBo s= reqStatService.getDashboard();
        logger.debug("getDashboard={}",JsonUtils.object2Json(s));
        ST.stop();
        return s;
    }

    @Override
    public List<AppInfoBo> getAppInfo() {
        return appService.getAppInfoBo();
    }

    @Override
    public List<ServiceInfoBo> getServiceInfo() {
        return appService.getServiceInfo();
    }

    @Override
    public List<HostInfoBo> getHostInfo() {
        return appService.getHostInfo();
    }
}
