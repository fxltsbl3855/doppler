package com.sinoservices.doppler.assimble;

import com.sinoservices.doppler.bo.AnnoServiceBo;
import com.sinoservices.doppler.bo.AppStatBo;
import com.sinoservices.doppler.bo.ServiceBo;
import com.sinoservices.doppler.bo.ServiceStatBo;
import com.sinoservices.doppler.entity.*;
import com.sinoservices.util.NumberUtil;
import com.sinoservices.util.StringUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/1/22.
 */
public class ServiceAssimble {

    public static void main(String[] a){
        System.out.print(brifeServiceName("com.sinoservices.doppler.facade.AppFacade"));
    }

    public static void mergeMap(Map<Integer, AppStatBo> map1,Map<Integer, AppStatBo> map2) {
        if(map1 == null){
            map1 = new HashMap<Integer, AppStatBo>();
        }
        if(map2 == null){
            map2 = new HashMap<Integer, AppStatBo>();
        }
        for(Map.Entry<Integer, AppStatBo> entry: map2.entrySet()){
           if(!map1.containsKey(entry.getKey())){
               map1.put(entry.getKey(),entry.getValue());
           }
        }
    }

    public static Map<Integer, AppStatBo> annoAppWebToMap(List<AnnoWebAppDB> listWebSr) {
        if(listWebSr == null || listWebSr.size()==0){
            return new HashMap<Integer, AppStatBo>();
        }
        Map<Integer,AppStatBo> resMap = new HashMap<Integer, AppStatBo>(listWebSr.size());
        for(AnnoWebAppDB annoWebAppDB : listWebSr){
            AppStatBo appStatBo = new AppStatBo();
            appStatBo.setAppName(annoWebAppDB.getAppName());
            appStatBo.setAppId(annoWebAppDB.getAppId());
            appStatBo.setReqNum(annoWebAppDB.getNum());
            resMap.put(annoWebAppDB.getAppId(),appStatBo);
        }
        return resMap;
    }

    public static Map<Integer, AppStatBo> annoServToMap(List<AnnoWebServiceDB> listWebSr) {
        if(listWebSr == null || listWebSr.size()==0){
            return new HashMap<Integer, AppStatBo>();
        }
        Map<Integer,AppStatBo> resMap = new HashMap<Integer, AppStatBo>(listWebSr.size());
        for(AnnoWebServiceDB annoWebServiceDB : listWebSr){
            AppStatBo appStatBo = new AppStatBo();
            appStatBo.setAppName(annoWebServiceDB.getAppName());
            appStatBo.setAppId(annoWebServiceDB.getAppId());
            appStatBo.setReqNum(annoWebServiceDB.getNum());
            resMap.put(annoWebServiceDB.getAppId(),appStatBo);
        }
        return resMap;
    }

    /**
     *
     * @param listSr
     * @param type 1 sr; 2 timeout 3 error
     * @return
     */
    public static Map<Integer,AppStatBo> annoAppToMap(List<AnnoAppDB> listSr,int type) {
        if(listSr == null || listSr.size()==0){
            return new HashMap<Integer, AppStatBo>(0);
        }
        Map<Integer,AppStatBo> resMap = new HashMap<Integer, AppStatBo>(listSr.size());
        for(AnnoAppDB annoAppDB:listSr){
            AppStatBo appStatBo = new AppStatBo();
            appStatBo.setAppName(annoAppDB.getAppName());
            appStatBo.setAppId(annoAppDB.getAppId());
            switch(type){
                case 1:
                    appStatBo.setReqNum(annoAppDB.getNum());
                    break;
                case 2:
                    appStatBo.setTimeoutNum(annoAppDB.getNum());
                    break;
                case 3:
                    appStatBo.setErrorNum(annoAppDB.getNum());
                    break;
            }
            resMap.put(annoAppDB.getAppId(),appStatBo);
        }
        return resMap;
    }


    public static Map<Integer,AppStatBo> annoAppToMap(List<AnnoAppDB> listSr) {
        if(listSr == null || listSr.size()==0){
            return new HashMap<Integer, AppStatBo>(0);
        }
        Map<Integer,AppStatBo> resMap = new HashMap<Integer, AppStatBo>(listSr.size());
        for(AnnoAppDB annoAppDB:listSr){
            AppStatBo appStatBo = new AppStatBo();
            appStatBo.setAppName(annoAppDB.getAppName());
            appStatBo.setAppId(annoAppDB.getAppId());
            appStatBo.setReqNum(annoAppDB.getNum());
            resMap.put(appStatBo.getAppId(),appStatBo);
        }
        return resMap;
    }

    public static void timeoutToSr(Map<Integer,AppStatBo> sourceMap,Map<Integer,AppStatBo> timeoutMap){
        if(sourceMap == null) {
            sourceMap = new HashMap<Integer, AppStatBo>(0);
        }
        if(timeoutMap == null ) {
            timeoutMap = new HashMap<Integer, AppStatBo>(0);
        }

        for(Map.Entry<Integer,AppStatBo> entry :sourceMap.entrySet()){
            AppStatBo appStatBo = entry.getValue();
            AppStatBo timeout = timeoutMap.get(entry.getKey());
            if(timeout!=null) {
                appStatBo.setTimeoutNum(timeout.getTimeoutNum());
                appStatBo.setTimeoutPercent(NumberUtil.devide(timeout.getTimeoutNum(), appStatBo.getReqNum(), 2));
            }
        }
        for(Map.Entry<Integer,AppStatBo> entry :timeoutMap.entrySet()){
            AppStatBo appStatBo = entry.getValue();
            AppStatBo source = sourceMap.get(entry.getKey());
            if(source==null) {
                sourceMap.put(entry.getKey(),appStatBo);
            }
        }
    }

    public static void errorToSr(Map<Integer,AppStatBo> sourceMap,Map<Integer,AppStatBo> errorMap){
        if(sourceMap == null) {
            sourceMap = new HashMap<Integer, AppStatBo>(0);
        }
        if(errorMap == null ) {
            errorMap = new HashMap<Integer, AppStatBo>(0);
        }

        for(Map.Entry<Integer,AppStatBo> entry :sourceMap.entrySet()){
            AppStatBo appStatBo = entry.getValue();
            AppStatBo error = errorMap.get(entry.getKey());
            if(error!=null) {
                appStatBo.setErrorNum(error.getErrorNum());
                appStatBo.setErrorPercent(NumberUtil.devide(error.getErrorNum(), appStatBo.getReqNum(), 2));
            }
        }
        for(Map.Entry<Integer,AppStatBo> entry :errorMap.entrySet()){
            AppStatBo appStatBo = entry.getValue();
            AppStatBo source = sourceMap.get(entry.getKey());
            if(source==null) {
                sourceMap.put(entry.getKey(),appStatBo);
            }
        }
    }

    public static List<ServiceBo> serviceDB2Bo(List<ServiceDB> list) {
        if(list == null || list.size() == 0){
            return new ArrayList<ServiceBo>();
        }

        List<ServiceBo> resList = new ArrayList<ServiceBo>(list.size());
        for(ServiceDB ss : list){
            ServiceBo vv =  new ServiceBo();
            vv.setServiceId(ss.getId());
            vv.setName(ss.getName());
            resList.add(vv);
        }
        return resList;
    }

    public static List<ServiceStatBo> assimbleServiceStat(List<AnnoSpanDB> list,boolean isServer) {
        if(list == null || list.size()==0){
            return new ArrayList<ServiceStatBo>();
        }
        List<ServiceStatBo> resList = new ArrayList<ServiceStatBo>(list.size());
        for(AnnoSpanDB a : list){
            ServiceStatBo s = new ServiceStatBo();
            s.setSpanViewId(a.getServiceId()+"#"+ StringUtil.null2Trim(a.getSpanName()));
            String spanView = a.getServiceName();
            if(isServer){
                spanView = brifeServiceName(a.getServiceName())+"."+ StringUtil.null2Trim(a.getSpanName());
            }
            s.setSpanView(spanView);
            s.setReqNum(a.getNum()==null?0:a.getNum().intValue());
            s.setQps(NumberUtil.devide(a.getNum(),24*3600,3));
            s.setMaxTime(a.getMaxDuration()==null?-1:a.getMaxDuration());
            resList.add(s);
        }
        return resList;
    }

    /**
     * 服务名转换
     *com.sinoservices.doppler.facade.AppFacade转换为c.s.d.f.AppFacade
     * @param serviceFullName
     * @return
     */
    public static String brifeServiceName(String serviceFullName) {
        if(serviceFullName== null || "".equals(serviceFullName.trim())){
            return "";
        }
        String[] ss = serviceFullName.split("\\.");
        if(ss.length <= 1){
            return serviceFullName;
        }
        StringBuilder sb = new StringBuilder();
//        for(int i=0;i<ss.length-1;i++){
//            sb.append(ss[i].charAt(0));
//            sb.append(".");
//        }
        sb.append(ss[ss.length-1]);
        return sb.toString();
    }

    public static void addServiceStat(List<ServiceStatBo> list, List<AnnoSpanDB> timeoutList, List<AnnoSpanDB> errorList) {
        if(list == null || list.size()==0){
            return;
        }
        Map<String,AnnoSpanDB> timeoutMap = toServiceMap(timeoutList);
        Map<String,AnnoSpanDB> errorMap = toServiceMap(errorList);
        for(ServiceStatBo a : list){
            AnnoSpanDB timeout = timeoutMap.get(a.getSpanViewId());
            AnnoSpanDB error = errorMap.get(a.getSpanViewId());
            a.setTimeoutNum((timeout==null||timeout.getNum()==null)?0:timeout.getNum());
            a.setErrorNum((error==null||error.getNum()==null)?0:error.getNum());
            a.setTimeoutPercent(NumberUtil.devide(a.getTimeoutNum(),a.getReqNum(),2));
            a.setErrorPercent(NumberUtil.devide(a.getErrorNum(),a.getReqNum(),2));
        }
    }

    public static Map<String,AnnoSpanDB> toServiceMap(List<AnnoSpanDB> list) {
        if(list == null || list.size()==0){
            return new HashMap<String, AnnoSpanDB>(0);
        }
        Map<String,AnnoSpanDB> map = new HashMap<String,AnnoSpanDB>(list.size());
        for(AnnoSpanDB annoSpanDB:list){
            map.put(annoSpanDB.getServiceId()+"#"+annoSpanDB.getSpanName(),annoSpanDB);
        }
        return map;
    }


    public static void filterByName(List<AppDB> appAll, List<AppDB> appDBWebList, List<AppDB> appDBServerList) {
        if(appAll == null || appAll.size()==0){
            return ;
        }
        for(AppDB appDB:appAll){
            if(appDB.getName().toLowerCase().indexOf("-web")>=0 ||appDB.getName().toLowerCase().indexOf("_web")>=0 ){
                appDBWebList.add(appDB);
            }else{
                appDBServerList.add(appDB);
            }
        }
    }
}
