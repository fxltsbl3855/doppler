package com.sinoservices.doppler.service.assimble;

import com.sinoservices.doppler.bo.AppReq;
import com.sinoservices.doppler.bo.AppStatBo;
import com.sinoservices.doppler.bo.ServiceStatBo;
import com.sinoservices.doppler.bo.SpanViewBo;
import com.sinoservices.doppler.entity.ReqStatDB;
import com.sinoservices.doppler.entity.ServiceDB;
import com.sinoservices.util.DateUtil;
import com.sinoservices.util.JsonUtils;
import com.sinoservices.util.NumberUtil;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class ReqStatAssimble {

    public static List<ServiceStatBo> transferToServiceStatBoList(Date date,List<ReqStatDB> methodStatList) {
        if(methodStatList == null || methodStatList.size() == 0){
            return Collections.emptyList();
        }
        List<ServiceStatBo> resList = new ArrayList<ServiceStatBo>(methodStatList.size());
        for(ReqStatDB reqStatDB : methodStatList){
            ServiceStatBo serviceStatBo = new ServiceStatBo();
            serviceStatBo.setSpanViewId(reqStatDB.getServiceId()+"#"+reqStatDB.getMethod());
            serviceStatBo.setSpanView(reqStatDB.getMethod());
            serviceStatBo.setReqNum(reqStatDB.getReqNum());
            serviceStatBo.setTimeoutNum(reqStatDB.getTimeoutNum());
            serviceStatBo.setErrorNum(reqStatDB.getErrorNum());
            serviceStatBo.setMaxTime(reqStatDB.getDuration());

            serviceStatBo.setQps(NumberUtil.devide(reqStatDB.getReqNum(),todayPassedSeconds(date),2));
            serviceStatBo.setTimeoutPercent(NumberUtil.devide(reqStatDB.getTimeoutNum(),reqStatDB.getReqNum(),2));
            serviceStatBo.setErrorPercent(NumberUtil.devide(reqStatDB.getErrorNum(),reqStatDB.getReqNum(),2));
            resList.add(serviceStatBo);
        }

        return resList;
    }

    /**
     * 计算出dataDate到现在（dataDate当天内）间隔的时长
     * @param paramDate
     * @return
     */
    public static int todayPassedSeconds(Date paramDate){
        Date todatToMills = new Date();
        Date paramDateToDays = DateUtil.stirng2Date(DateUtil.Date2String(paramDate,"yyyy-MM-dd"),"yyyy-MM-dd");
        Date todayToDays = DateUtil.stirng2Date(DateUtil.Date2String(todatToMills,"yyyy-MM-dd"),"yyyy-MM-dd");

        int intervalSesc =  24 * 3600;
        if(paramDateToDays.compareTo(todayToDays) == 0){
            intervalSesc =  NumberUtil.doubleToInt((todatToMills.getTime()-todayToDays.getTime())/1000);
        }
        return intervalSesc;
    }

    public static AppStatBo transferToAppStatBo(ReqStatDB reqStatDB) {
        AppStatBo appStatBo = new AppStatBo();
        if(reqStatDB == null){
            return appStatBo;
        }
        appStatBo.setAppId(reqStatDB.getAppId());
        appStatBo.setAppName(reqStatDB.getAppName());

        appStatBo.setReqNum(reqStatDB.getReqNum());
        appStatBo.setTimeoutNum(reqStatDB.getTimeoutNum());
        appStatBo.setErrorNum(reqStatDB.getErrorNum());

        appStatBo.setTimeoutPercent(NumberUtil.devide(reqStatDB.getTimeoutNum(),reqStatDB.getReqNum(),2));
        appStatBo.setErrorPercent(NumberUtil.devide(reqStatDB.getErrorNum(),reqStatDB.getReqNum(),2));
        return appStatBo;
    }

    public static Map<Integer,ServiceDB> toMap(List<ServiceDB> appServiceInfoList){
        Map<Integer,ServiceDB> map = new HashMap<Integer, ServiceDB>();
        if(appServiceInfoList==null || appServiceInfoList.size()==0){
            return map;
        }

        for(ServiceDB serviceDB : appServiceInfoList){
            map.put(NumberUtil.formatNumber(serviceDB.getId(),-1),serviceDB);
        }
        return map;
    }

    public static String getServiceIdStr(List<ReqStatDB> serverStat){
        if(serverStat==null || serverStat.size()==0){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(ReqStatDB reqStatDB : serverStat){
            sb.append(",");
            sb.append(reqStatDB.getServiceId());
        }
        sb = sb.deleteCharAt(0);
        return sb.toString();
    }

    public static List<ReqStatDB> groupByAppIdName(List<ReqStatDB> serverStat, List<ServiceDB> appServiceInfoList) {
        Map<Integer,ServiceDB> appInfoMap = toMap(appServiceInfoList);

        Map<Integer,ReqStatDB> result = new HashMap<Integer, ReqStatDB>();
        if(serverStat == null){
            return Collections.emptyList();
        }
        for(ReqStatDB reqStatDB : serverStat){
            ServiceDB serviceDB = appInfoMap.get(reqStatDB.getServiceId());
            reqStatDB.setAppId(serviceDB==null?0:serviceDB.getAppId());
            reqStatDB.setAppName(serviceDB==null?"":serviceDB.getAppName());

            ReqStatDB tmp =  result.get(serviceDB==null?0:serviceDB.getAppId()) ;
            if(tmp == null){
                result.put(serviceDB==null?0:serviceDB.getAppId(),reqStatDB);
            }else{
                tmp.setReqNum(tmp.getReqNum()+reqStatDB.getReqNum());
                tmp.setTimeoutNum(tmp.getTimeoutNum()+reqStatDB.getTimeoutNum());
                tmp.setErrorNum(tmp.getErrorNum()+reqStatDB.getErrorNum());
                tmp.setDuration(tmp.getDuration()>reqStatDB.getDuration()?tmp.getDuration():reqStatDB.getDuration());
            }
        }
        return new ArrayList<ReqStatDB>(result.values());
    }


    public static void addToAppStatBoList(List<AppStatBo> resultList, List<ReqStatDB> reqStatDBList) {
        if(reqStatDBList == null || reqStatDBList.size() == 0){
            return ;
        }
        for(ReqStatDB reqStatDB : reqStatDBList){
            resultList.add(ReqStatAssimble.transferToAppStatBo(reqStatDB));
        }
    }

    public static int addToAppReqList(List<AppReq> webReqList, List<ReqStatDB> reqStatDBListtat) {
        int reqNumStat = 0;
        if(reqStatDBListtat == null || reqStatDBListtat.size() == 0){
            return reqNumStat;
        }
        for(ReqStatDB reqStatDB : reqStatDBListtat){
            reqNumStat += reqStatDB.getReqNum();
            webReqList.add(new AppReq(reqStatDB.getAppName(),reqStatDB.getReqNum()));
        }
        return reqNumStat;
    }


    public static List<SpanViewBo> transferToSpanViewBoList(String date,List<ReqStatDB> hourList) {
        if(hourList == null || hourList.size() == 0){
            return Collections.emptyList();
        }

        List<SpanViewBo> resList = new ArrayList<SpanViewBo>(24);
        Map<String,ReqStatDB> resMap = new HashMap<String, ReqStatDB>(hourList.size());
        for(ReqStatDB reqStatDB : hourList){
            resMap.put(DateUtil.Date2String(new Date(reqStatDB.getLogTime()),"yyyy-MM-dd HH"),reqStatDB);
        }

        String dateStr = DateUtil.Date2String(DateUtil.stirng2Date(date,"yyyy-MM-dd"));
        for(int i=0;i<24;i++){
            String hourStr = i<10?"0"+i:i+"";
            int reqNum = 0;
            String dateH = dateStr+" "+hourStr;
            ReqStatDB reqStatDB= resMap.get(dateH);
            if(reqStatDB != null){
                reqNum = reqStatDB.getReqNum();
            }
            SpanViewBo spanViewBo = new SpanViewBo();
            spanViewBo.setReqNum(reqNum);
            spanViewBo.setDate(dateH);
            resList.add(spanViewBo);
        }
        return resList;
    }

    public static void main(String[] args) throws Exception {
        List<ReqStatDB> hourList = new ArrayList<ReqStatDB>();
        ReqStatDB reqStatDB = new ReqStatDB();
        reqStatDB.setReqNum(10);
        reqStatDB.setLogTime(1464771600000L);
        hourList.add(reqStatDB);


        System.out.println(todayPassedSeconds( DateUtil.stirng2Date("2016-6-7","yyyy-MM-dd HH")));
    }
}
