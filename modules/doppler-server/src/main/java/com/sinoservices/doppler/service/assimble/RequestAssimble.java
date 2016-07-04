package com.sinoservices.doppler.service.assimble;

import com.sinoservices.doppler.TraceContants;
import com.sinoservices.doppler.bo.ErrorMsgBo;
import com.sinoservices.doppler.bo.ReqDetailBo;
import com.sinoservices.doppler.bo.RequestBo;
import com.sinoservices.doppler.entity.AnnotationDB;
import com.sinoservices.doppler.entity.AnnotationWebDB;
import com.sinoservices.doppler.entity.BusLogDB;
import com.sinoservices.doppler.entity.ServiceDB;
import com.sinoservices.util.DateUtil;
import com.sinoservices.util.NumberUtil;
import com.sinoservices.util.ObjectUtil;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class RequestAssimble {

    public static void main(String[] a){
        String aa = "123,5678";
        System.out.print(trimComma(aa));
    }
    public static String trimComma(String str){
        if(str == null || str.length() == 0){
            return "";
        }
        if(str.length() > 0 && str.charAt(0)==','){
            str = str.substring(1);
        }
        if(str.length() > 0 && str.charAt(str.length()-1)==','){
            str = str.substring(0,str.length()-1);
        }
        return str;
    }

    public static List<RequestBo> toRequestBoList(List<BusLogDB> dblist) {
        List<RequestBo> resList = new ArrayList<RequestBo>(dblist.size());
        if(dblist==null || dblist.size()==0){
            return resList;
        }
        for(BusLogDB busLogDB:dblist){
            RequestBo requestBo = new RequestBo();
            requestBo.setId(busLogDB.getId());
            requestBo.setAppName(busLogDB.getAppName());
            requestBo.setServiceName(busLogDB.getServiceName());
            requestBo.setAddr(busLogDB.getAddr());
            requestBo.setErrorLevelDisplay(TraceContants.getErrorTypeStr(busLogDB.getErrorType()));
            requestBo.setReqTime(DateUtil.Date2String(new Date(busLogDB.getLogTime()),"yyyy-MM-dd HH:mm:ss"));
            requestBo.setLogInfo(busLogDB.getLogInfo());
            requestBo.setSpanId(busLogDB.getSpanId());
            resList.add(requestBo);
        }
        return resList;
    }

    /**
     * fill sourceServiceName，sourceAppName
     */
    public static void fillAppService(ErrorMsgBo errorMsgBo, ServiceDB sourceAppService, ServiceDB targetAppService) {
        if(targetAppService != null) {
            errorMsgBo.setTargetServiceName(targetAppService.getName());
            errorMsgBo.setTargetAppName(targetAppService.getAppName());
        }
        if(sourceAppService != null) {
            errorMsgBo.setSourceServiceName(sourceAppService.getName());
            errorMsgBo.setSourceAppName(sourceAppService.getAppName());
        }
    }

    /**
     */
    public static Map<Integer,ServiceDB> listToMapAppService(List<ServiceDB> serviceDBList) {
        Map<Integer,ServiceDB> map = new HashMap<Integer, ServiceDB>();
        if(serviceDBList == null || serviceDBList.size() ==0){
            return map;
        }
        for(ServiceDB ss : serviceDBList){
            map.put(NumberUtil.formatNumber(ss.getId(),-1),ss);
        }
        return map;
    }


    /**
     * ErrorMsgBo gen  ReqDetailBo
     * @param errorMsgBo
     * @return
     */
    public static ReqDetailBo transferToReqDetailBo(ErrorMsgBo errorMsgBo) {
        ReqDetailBo reqDetailBo = new ReqDetailBo();
        reqDetailBo.setSpanId(errorMsgBo.getSpanId());
        ObjectUtil.transferAll(errorMsgBo,reqDetailBo);

        reqDetailBo.setErrorAddr(errorMsgBo.getErrorAddr());
        reqDetailBo.setErrorInfo(errorMsgBo.getErrorInfo());
        reqDetailBo.setErrorTime(errorMsgBo.getErrorTime());
        return reqDetailBo;
    }

    public static void fillErrorInfo(ErrorMsgBo errorMsgBo, BusLogDB busLogDB) {
        errorMsgBo.setErrorAddr(busLogDB.getAddr());
        errorMsgBo.setErrorInfo(busLogDB.getLogInfo());
        errorMsgBo.setErrorTime(DateUtil.Date2String(new Date(busLogDB.getLogTime()), "yyyy-MM-dd HH:mm:ss"));
    }
}
