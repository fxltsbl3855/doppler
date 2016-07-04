package com.sinoservices.doppler.facade.impl;


import com.sinoservices.doppler.bo.*;
import com.sinoservices.doppler.exception.RunTaskException;
import com.sinoservices.doppler.facade.RequestFacade;
import com.sinoservices.doppler.service.RequestService;
import com.sinoservices.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2016/1/20.
 */
@Service("requestFacade")
public class RequestFacadeImpl implements RequestFacade {
    private static Logger logger = LoggerFactory.getLogger(RequestFacadeImpl.class);

    @Autowired
    RequestService requestService;

    @Override
    public DisplayItemBo getDisplayItem() {
        logger.info("getDisplayItem invoked..................");
        DisplayItemBo displayItemBo = requestService.getDisplayItem();
        logger.info("getDisplayItem result={}", JsonUtils.object2Json(displayItemBo));
        return displayItemBo;
    }

    @Override
    public DisplayServiceBo getServicesByAppId(int appId) {
        logger.info("getServicesByAppId invoked..................appId={}",appId);
        DisplayServiceBo displayServiceBo =  requestService.getServicesByAppId(appId);
        logger.info("getServicesByAppId result={}", JsonUtils.object2Json(displayServiceBo));
        return displayServiceBo;
    }

    @Override
    public List<RequestBo> getReqInfo(String startDate, String endDate, int appId, int serviceId, int errorLevel, String addr, String key) {
        logger.info("getReqInfo invoked..................startDate="+startDate+",endDate="+endDate+",appId="+appId+",serviceId="+serviceId+",errorLevel="+errorLevel+",addr="+addr+",key="+key);
        try{
            List<RequestBo> requestBoList = requestService.getReqInfo(startDate,endDate,appId,serviceId,errorLevel,addr,key);

            logger.info("getReqInfo result={}", JsonUtils.object2Json(requestBoList));
            return requestBoList;
        }catch (RunTaskException e){
            throw e;
        }catch (RuntimeException e){
            throw e;
        }
    }

    @Override
    public ReqDetailBo getAppDetailById(int id) {
        logger.info("getAppDetailById invoked..................id={}",id);
        ReqDetailBo reqDetailBo = requestService.getAppDetailById(id);
        logger.info("getAppDetailById result={}", JsonUtils.object2Json(reqDetailBo));
        return reqDetailBo;

    }
}
