package com.sinoservices.doppler.facade.impl;


import com.sinoservices.doppler.TraceContants;
import com.sinoservices.doppler.assimble.ServiceAssimble;
import com.sinoservices.doppler.bo.*;
import com.sinoservices.doppler.entity.AppDB;
import com.sinoservices.doppler.facade.AppFacade;
import com.sinoservices.doppler.facade.TraceFacade;
import com.sinoservices.doppler.service.AppService;
import com.sinoservices.doppler.service.BusLogService;
import com.sinoservices.doppler.service.ServiceService;
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
@Service("traceFacade")
public class TraceFacadeImpl implements TraceFacade {

    @Autowired
    BusLogService busLogService;

    private static Logger logger = LoggerFactory.getLogger(TraceFacadeImpl.class);


    @Override
    public void info(BusLogBo busLogBo) {
        logger.info("info invoked busLogBo="+ JsonUtils.object2Json(busLogBo));
        log(busLogBo,TraceContants.ERROR_TYPE_INFO);
    }

    @Override
    public void warn(BusLogBo busLogBo) {
        log(busLogBo,TraceContants.ERROR_TYPE_WARN);
    }

    @Override
    public void error(BusLogBo busLogBo) {
        log(busLogBo,TraceContants.ERROR_TYPE_ERROR);
    }

    @Override
    public void fetal(BusLogBo busLogBo) {
        log(busLogBo,TraceContants.ERROR_TYPE_FETAL);
    }

    public void log(BusLogBo busLogBo,Integer errorType) {
        if(errorType == null){
            errorType = TraceContants.ERROR_TYPE_INFO;
        }
        busLogBo.setErrorType(errorType);
        busLogService.log(busLogBo);

    }
}
