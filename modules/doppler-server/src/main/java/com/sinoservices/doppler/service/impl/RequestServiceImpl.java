package com.sinoservices.doppler.service.impl;

import com.sinoservices.doppler.Constant;
import com.sinoservices.doppler.bo.*;
import com.sinoservices.doppler.dao.AnnotationMapper;
import com.sinoservices.doppler.dao.AppMapper;
import com.sinoservices.doppler.dao.BusLogMapper;
import com.sinoservices.doppler.dao.ServiceMapper;
import com.sinoservices.doppler.entity.*;
import com.sinoservices.doppler.service.RequestService;
import com.sinoservices.doppler.service.ServiceService;
import com.sinoservices.doppler.service.assimble.RequestAssimble;
import com.sinoservices.util.DateUtil;
import com.sinoservices.util.JsonUtils;
import com.sinoservices.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
@Service("requestService")
public class RequestServiceImpl implements RequestService {
    private static final Logger logger = LoggerFactory.getLogger(RequestServiceImpl.class);

    @Autowired
    private AppMapper appMapper;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private BusLogMapper busLogMapper;
    @Autowired
    private AnnotationMapper annotationMapper;
    @Autowired
    private ServiceMapper serviceMapper;


    @Override
    public DisplayItemBo getDisplayItem() {
        DisplayItemBo displayItemBo = new DisplayItemBo();
        List<AppDB> appDBs = appMapper.getAppAll();
        logger.info("getDisplayItem.getAppAll="+JsonUtils.object2Json(appDBs));
        if(appDBs == null){
            return displayItemBo;
        }

        Map<Integer,String> appMap = new HashMap<Integer,String>(appDBs.size());
        displayItemBo.setAppMap(appMap);
        for(AppDB appDB : appDBs){
            appMap.put(appDB.getId(),appDB.getName());
        }
        return displayItemBo;
    }

    @Override
    public DisplayServiceBo getServicesByAppId(int appId) {
        DisplayServiceBo displayServiceBo = new DisplayServiceBo();
        List<ServiceDB> serviceDBs = serviceService.getServiceByAppId(appId);
        logger.info("getServicesByAppId.getServiceByAppId="+JsonUtils.object2Json(serviceDBs));
        if(serviceDBs == null){
            return displayServiceBo;
        }

        Map<Integer, String> serviceMap = new HashMap<Integer, String>(serviceDBs.size());
        displayServiceBo.setServiceMap(serviceMap);
        for(ServiceDB serviceDB : serviceDBs){
            serviceMap.put(NumberUtil.formatNumber(serviceDB.getId(),-1),serviceDB.getName());
        }
        return displayServiceBo;
    }

    @Override
    public List<RequestBo> getReqInfo(String startDate, String endDate, int appId, int serviceId, int errorLevel, String addr, String key) {
        BusLogDB busLogDB = new BusLogDB();
        busLogDB.setAddr(addr);
        busLogDB.setLogInfo(key);
        busLogDB.setAppId(appId);
        busLogDB.setServiceId(serviceId);
        busLogDB.setErrorType(errorLevel);
        List<BusLogDB> dblist = busLogMapper.get(busLogDB, DateUtil.stirng2Date(startDate, Constant.yyyyMMddHHmmss).getTime(), DateUtil.stirng2Date(endDate, Constant.yyyyMMddHHmmss).getTime());
        logger.info("getReqInfo.get="+JsonUtils.object2Json(dblist));
        return RequestAssimble.toRequestBoList(dblist);
    }

    @Override
    public ReqDetailBo getAppDetailById(int id) {
        if(id <= 0 ){
            return new ReqDetailBo();
        }
        BusLogDB busLogDB = busLogMapper.getById(id);
        if(busLogDB == null ){
            return new ReqDetailBo();
        }

        long spanId =busLogDB.getSpanId();
        logger.info("getAppDetailById ,id="+id+",spanId="+spanId);

        List<AnnotationDB> annoList = annotationMapper.getAnnotationsBySpanId(spanId);
        logger.info("getAppDetailBySpanId.getAnnotationsBySpanId="+JsonUtils.object2Json(annoList));
        //server项目
        if(annoList !=null && annoList.size() >0){
            return getServerAppDetailBySpanId(busLogDB,annoList,spanId);
        }

        //web项目
        List<AnnotationWebDB> annoWebList = annotationMapper.getAnnotationWebsBySpanId(spanId);
        logger.info("getAppDetailBySpanId.getAnnotationWebsBySpanId="+JsonUtils.object2Json(annoWebList));
        return getWebAppDetailBySpanId(busLogDB,annoWebList,spanId);
    }

    /**
     * //2 获取，调用方，被调用方（服务ID，地址）数据，ErrorMsgBo数据，及 错误时间errorTime，错误信息errorInfo
     //3 根据（调用方，被调用方的）服务ID获取 应用名，服务名，并回填ReqDetailBo
     * @param annoList
     * @return
     */
    private ReqDetailBo getServerAppDetailBySpanId(BusLogDB busLogDB,List<AnnotationDB> annoList,long spanId) {
        if(annoList ==null || annoList.size() ==0){
            return new ReqDetailBo(spanId);
        }
        ErrorMsgBo errorMsgBo = new ErrorMsgBo();
        errorMsgBo.setServer(true);
        errorMsgBo.setSpanId(spanId);
        for(AnnotationDB annotationDB:annoList){
            if("cr".endsWith(annotationDB.getK())){ //source
                errorMsgBo.setSourceServiceId(NumberUtil.formatNumber(annotationDB.getService(),-1));
                errorMsgBo.setSourceAddrName(annotationDB.getIp());
            }else if("sr".endsWith(annotationDB.getK())){ //target
                errorMsgBo.setTargetServiceId(NumberUtil.formatNumber(annotationDB.getService(),-1));
                errorMsgBo.setTargetAddrName(annotationDB.getIp());
            }
        }

        RequestAssimble.fillErrorInfo(errorMsgBo,busLogDB);
        logger.info("getServerAppDetailBySpanId,="+JsonUtils.object2Json(errorMsgBo));

        StringBuilder serviceIds = new StringBuilder(errorMsgBo.getTargetServiceId()==0?"":errorMsgBo.getTargetServiceId()+"");
        serviceIds.append(errorMsgBo.getSourceServiceId()==0?"":","+errorMsgBo.getSourceServiceId());
        List<ServiceDB> serviceDBList = serviceMapper.getAppServiceByServiceId(RequestAssimble.trimComma(serviceIds.toString()));
        logger.info("getServerAppDetailBySpanId.serviceDBList="+JsonUtils.object2Json(serviceDBList));

        Map<Integer,ServiceDB> appServiceMap = RequestAssimble.listToMapAppService(serviceDBList);
        RequestAssimble.fillAppService(errorMsgBo,appServiceMap.get(errorMsgBo.getSourceServiceId()),appServiceMap.get(errorMsgBo.getTargetServiceId()));
        logger.info("getServerAppDetailBySpanId2,="+JsonUtils.object2Json(errorMsgBo));

        ReqDetailBo rbd = RequestAssimble.transferToReqDetailBo(errorMsgBo);
        logger.info("getServerAppDetailBySpanId3,="+JsonUtils.object2Json(rbd));
        return rbd;
    }

    private ReqDetailBo getWebAppDetailBySpanId(BusLogDB busLogDB,List<AnnotationWebDB> annoWebList,long spanId) {
        if(annoWebList ==null || annoWebList.size() ==0){
            return new ReqDetailBo(spanId);
        }
        ErrorMsgBo errorMsgBo = new ErrorMsgBo();
        errorMsgBo.setServer(false);
        errorMsgBo.setSpanId(spanId);
        for(AnnotationWebDB annotationWebDB:annoWebList){
            if("cr".endsWith(annotationWebDB.getK())){ //source
                errorMsgBo.setSourceServiceId(annotationWebDB.getServiceId());
                errorMsgBo.setSourceServiceName(annotationWebDB.getServiceName());
                errorMsgBo.setSourceAppName(annotationWebDB.getAppName());
                errorMsgBo.setSourceAppId(annotationWebDB.getAppId());
                errorMsgBo.setSourceAddrName(annotationWebDB.getIp());
            }else if("sr".endsWith(annotationWebDB.getK())){ //target
                errorMsgBo.setTargetServiceId(annotationWebDB.getServiceId());
                errorMsgBo.setTargetServiceName(annotationWebDB.getServiceName());
                errorMsgBo.setTargetAppName(annotationWebDB.getAppName());
                errorMsgBo.setTargetAppId(annotationWebDB.getAppId());
                errorMsgBo.setTargetAddrName(annotationWebDB.getIp());
            }
        }
        RequestAssimble.fillErrorInfo(errorMsgBo,busLogDB);
        logger.info("getWebAppDetailBySpanId,="+JsonUtils.object2Json(errorMsgBo));

        ReqDetailBo reqDetailBo = RequestAssimble.transferToReqDetailBo(errorMsgBo);
        logger.info("getWebAppDetailBySpanId2,="+JsonUtils.object2Json(reqDetailBo));
        return reqDetailBo;
    }




}
