package com.sinoservices.doppler.service.impl;

import com.sinoservices.doppler.bo.BusLogBo;
import com.sinoservices.doppler.cache.RegInfo;
import com.sinoservices.doppler.cache.RegInfoCache;
import com.sinoservices.doppler.dao.AppMapper;
import com.sinoservices.doppler.dao.BusLogMapper;
import com.sinoservices.doppler.dao.ServiceMapper;
import com.sinoservices.doppler.entity.AppDB;
import com.sinoservices.doppler.entity.BusLogDB;
import com.sinoservices.doppler.entity.ServiceDB;
import com.sinoservices.doppler.service.BusLogService;
import com.sinoservices.util.JsonUtils;
import com.sinoservices.util.NumberUtil;
import com.sinoservices.util.ObjectUtil;
import com.sinoservices.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
@Service("busLogService")
public class BusLogServiceImpl implements BusLogService {
    private static Logger logger = LoggerFactory.getLogger(BusLogServiceImpl.class);

    @Autowired
    BusLogMapper busLogMapper;
    @Autowired
    AppMapper appMapper;
    @Autowired
    ServiceMapper serviceMapper;


    @Override
    public void log(BusLogBo busLogBo) {
        if(busLogBo.getServiceId() <= 0 && StringUtil.isBlank(busLogBo.getServiceName())){
            logger.error("Both of BusLog.serviceId and serviceName is empty,busLogBo="+ JsonUtils.object2Json(busLogBo));
            return;
        }

        BusLogDB busLogDB = new BusLogDB();
        ObjectUtil.transferAll(busLogBo,busLogDB);
        logger.info("busLogDB="+JsonUtils.object2Json(busLogDB));

        //serviceId，serviceName两者其中一个有值， 根据有值字段获取（serviceId）,appId
        RegInfo regInfo ;
        if(busLogBo.getServiceId() > 0){
            regInfo = RegInfoCache.getByServiceId(busLogBo.getServiceId());
        }else if(!StringUtil.isBlank(busLogBo.getServiceName())){
            regInfo = RegInfoCache.getByServiceName(busLogBo.getServiceName());
        }else{
            logger.error("Both of BusLog.serviceId and serviceName is empty,busLogBo2="+ JsonUtils.object2Json(busLogBo));
            return;
        }

        ServiceDB serviceDB =null;
        if(regInfo == null){//缓存不命中，查库
            logger.info("cache not hit,query db...");
            if(busLogBo.getServiceId() > 0){
                serviceDB = serviceMapper.getByServiceId(busLogBo.getServiceId());

            }else if(!StringUtil.isBlank(busLogBo.getServiceName())){
                serviceDB = serviceMapper.getServiceByName(busLogBo.getServiceName(),2);
            }
            if(serviceDB == null){
                logger.error("serviceId and serviceName query ServiceDB  is null,"+JsonUtils.object2Json(busLogBo));
                return;
            }
            regInfo = new RegInfo();
            regInfo.setAppId(serviceDB.getAppId());
            regInfo.setServiceId(NumberUtil.formatNumber(serviceDB.getId(),-1));
            regInfo.setServiceName(serviceDB.getName());
            RegInfoCache.putByServiceId(regInfo);
        }else{
            logger.info("cache hit");
        }

        busLogDB.setServiceId(regInfo.getServiceId());
        busLogDB.setAppId(regInfo.getAppId());
        busLogMapper.save(busLogDB);
    }
}
