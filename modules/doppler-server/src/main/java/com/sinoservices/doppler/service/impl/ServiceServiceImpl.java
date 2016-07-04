package com.sinoservices.doppler.service.impl;



import com.sinoservices.doppler.Constant;
import com.sinoservices.doppler.assimble.ServiceAssimble;
import com.sinoservices.doppler.bo.*;
import com.sinoservices.doppler.entity.*;
import com.sinoservices.doppler.dao.*;
import com.sinoservices.doppler.service.ServiceService;
import com.sinoservices.util.DateUtil;
import com.sinoservices.util.JsonUtils;
import com.sinoservices.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2016/1/21.
 */
@Service("serviceService")
public class ServiceServiceImpl implements ServiceService {

    private static Logger logger = LoggerFactory.getLogger(ServiceServiceImpl.class);

    @Autowired
    private AppMapper appMapper;
    @Autowired
    private ServiceMapper serviceMapper;
    @Autowired
    private TraceMapper traceMapper;
    @Autowired
    private AnnotationMapper annotationMapper;
    @Autowired
    private SpanMapper spanMapper;

    @Override
    public Map<Integer,AppStatBo> getAnnoApp(String date) {
        logger.debug("getAnnoApp invoke.............");
        Date startDate = DateUtil.stirng2Date(date,"yyyy-MM-dd");
        Date endDate = DateUtil.toTommorow(startDate);

        //server
        List<AnnoAppDB> listSr = annotationMapper.getAnnoAppByTimeK(startDate.getTime(),endDate.getTime(), Constant.ANNOTATION_K_SR);
        List<AnnoAppDB> listTimeout = annotationMapper.getReverseAnnoAppByTimeK(startDate.getTime(),endDate.getTime(), Constant.ANNOTATION_K_TIMEOUT);
        List<AnnoAppDB> listError = annotationMapper.getReverseAnnoAppByTimeK(startDate.getTime(),endDate.getTime(), Constant.ANNOTATION_K_ERROR);
        logger.debug("getAnnoApp listSr="+ JsonUtils.object2Json(listSr)+"_listTimeout="+JsonUtils.object2Json(listTimeout)+",listError="+JsonUtils.object2Json(listError));

        Map<Integer,AppStatBo> srMap = ServiceAssimble.annoAppToMap(listSr,1);
        Map<Integer,AppStatBo> timeoutMap = ServiceAssimble.annoAppToMap(listTimeout,2);
        Map<Integer,AppStatBo> errorMap = ServiceAssimble.annoAppToMap(listError,3);

        ServiceAssimble.timeoutToSr(srMap,timeoutMap);
        ServiceAssimble.errorToSr(srMap,errorMap);

        return srMap;
    }

    @Override
    public Map<Integer,AppStatBo> getAnnoWebApp(String date){
        Date startDate = DateUtil.stirng2Date(date,"yyyy-MM-dd");
        Date endDate = DateUtil.toTommorow(startDate);

        //web
        List<AnnoWebAppDB> listWebSr = annotationMapper.getAnnoWebAppByTime(startDate.getTime(),endDate.getTime());
        Map<Integer,AppStatBo> srMap = ServiceAssimble.annoAppWebToMap(listWebSr);
//        List<AppStatBo> resList = ServiceAssimble.assimbleWeb(listWebSr);
        return srMap;
    }

    @Override
    public List<ServiceDB> getServiceByAppId(int appId) {
        return serviceMapper.getServiceByAppId(appId,2);
    }

    private List<AnnoSpanDB> getAnnoNum(long startTime, long endTime, String k,String serviceId){
        List<ServiceDB> slist = serviceMapper.getServicesByCascade(NumberUtil.formatNumber(serviceId,-1));
        if(slist == null || slist.size()==0){
            return new ArrayList<AnnoSpanDB>(0);
        }
        StringBuilder sb = new StringBuilder();
        for(ServiceDB serviceDB : slist){
            sb.append(",");
            sb.append(serviceDB.getId());
        }
        sb = sb.deleteCharAt(0);
        List<AnnoSpanDB> asList = annotationMapper.getAnnoNumByServiceTimeK(startTime,endTime,k,sb.toString());
        if(asList == null || asList.size()==0){
            return new ArrayList<AnnoSpanDB>(0);
        }

        List<AnnoSpanDB> resList = new ArrayList<AnnoSpanDB>(asList.size());
        for(AnnoSpanDB annoSpanDB : asList){
            AnnoSpanDB asdb = new AnnoSpanDB();
            asdb.setServiceId(serviceId);
            asdb.setSpanName(annoSpanDB.getSpanName());
            asdb.setNum(annoSpanDB.getNum());
            resList.add(asdb);
        }

        return resList;
    }

    @Override
    public List<ServiceStatBo> getAnnoSpan(String date, int appId, String serviceId){
        Date startDate = DateUtil.stirng2Date(date,"yyyy-MM-dd");
        Date endDate = DateUtil.toTommorow(startDate);

        List<AnnoSpanDB> numList = annotationMapper.getAnnoSpan(startDate.getTime(),endDate.getTime(),Constant.ANNOTATION_K_SR,appId,serviceId,"");
        List<AnnoSpanDB> timeoutList = getAnnoNum(startDate.getTime(),endDate.getTime(),Constant.ANNOTATION_K_TIMEOUT,serviceId);
        List<AnnoSpanDB> errorList = getAnnoNum(startDate.getTime(),endDate.getTime(),Constant.ANNOTATION_K_ERROR,serviceId);
        List<ServiceStatBo> resList = ServiceAssimble.assimbleServiceStat(numList,true);
        ServiceAssimble.addServiceStat(resList,timeoutList,errorList);
        return resList;
    }
    @Override
    public List<ServiceStatBo> getAnnoSpanWeb(String date, int appId, String serviceId){
        Date startDate = DateUtil.stirng2Date(date,"yyyy-MM-dd");
        Date endDate = DateUtil.toTommorow(startDate);

        List<AnnoSpanDB> numWebList = annotationMapper.getAnnoSpanWeb(startDate.getTime(),endDate.getTime(),Constant.ANNOTATION_K_SR,appId,serviceId);
        List<ServiceStatBo> resList = ServiceAssimble.assimbleServiceStat(numWebList,false);
        return resList;
    }

    @Override
    public AppDB getAppByServiceId(String serviceId) {
        return serviceMapper.getAppByServiceId(serviceId);
    }

    public List<SpanViewBo> getReqNumByHour(String date,int appId, String serviceId, String method){
        Date startDate = DateUtil.stirng2Date(date,"yyyy-MM-dd");
        List<SpanViewBo> resList = new ArrayList<SpanViewBo>(24);
        boolean isServer = method != null && !"".equals(method);
        for(int i=0;i<24;i++){
            Date sDate = new Date(startDate.getTime()+i*3600*1000);
            Date endDate = new Date(startDate.getTime()+(i+1)*3600*1000);
            List<AnnoSpanDB> numList = null;
            if(isServer) {
                numList = annotationMapper.getAnnoSpan(sDate.getTime(), endDate.getTime(), Constant.ANNOTATION_K_SR, appId, serviceId, method);
            }else{
                numList = annotationMapper.getAnnoSpanWeb(sDate.getTime(), endDate.getTime(), Constant.ANNOTATION_K_SR, appId, serviceId);
            }
            List<ServiceStatBo> cList = ServiceAssimble.assimbleServiceStat(numList,isServer);

            int num = (cList == null || cList.size()==0 || cList.get(0)==null)?0:cList.get(0).getReqNum();
            SpanViewBo spanViewBo  =new SpanViewBo();
            spanViewBo.setDate(DateUtil.Date2String(sDate,"yyyy-MM-dd HH"));
            spanViewBo.setReqNum(num);
            resList.add(spanViewBo);
        }
        return resList;
    }




}
