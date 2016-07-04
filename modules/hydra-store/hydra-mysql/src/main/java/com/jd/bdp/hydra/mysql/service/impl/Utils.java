package com.jd.bdp.hydra.mysql.service.impl;

import com.jd.bdp.hydra.*;
import com.jd.bdp.hydra.mysql.persistent.entity.Absannotation;
import com.jd.bdp.hydra.mysql.persistent.entity.ReqStat;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User: yfliuyu
 * Date: 13-5-10
 * Time: 上午11:02
 */
public class Utils {

    private static String yyyy_MM_dd_HH = "yyyy-MM-dd HH";

    public static boolean isTopAnntation(Span span){
        List<Annotation> alist = span.getAnnotations();
        boolean isfirst = false;
        for(Annotation a : alist){
            if(StringUtils.endsWithIgnoreCase("cs", a.getValue())){
                isfirst = true;
            }
        }
        return isfirst;
    }

    public static Annotation getCsAnnotation(List<Annotation> alist){
        for(Annotation a : alist){
            if(StringUtils.endsWithIgnoreCase("cs", a.getValue())){
                return a;
            }
        }
        return null;
    }

    public static Annotation getCrAnnotation(List<Annotation> alist){
        for(Annotation a : alist){
            if(StringUtils.endsWithIgnoreCase("cr",a.getValue())){
                return a;
            }
        }
        return null;
    }

    public static boolean checkStat(Span span){
        for(Annotation a : span.getAnnotations()){
            if("sr".equals(a.getValue())){
               return true;
            }
        }
        return false;
    }
    public static ReqStat spanToReqStat(Span span){
        DateFormat format = new SimpleDateFormat(yyyy_MM_dd_HH);
        int serviceid = -1;
        try {
            serviceid = Integer.parseInt(span.getServiceId());
        }catch (Exception e){  }

        String host = "0.0.0.0:0";
        Long logTime = 0L;
        int duration = 0;
        for(Annotation a : span.getAnnotations()){
            if(Annotation.SERVER_RECEIVE.equals(a.getValue())){
                host = a.getHost().getIp();
                try {
                    logTime = format.parse(format.format(new Date(a.getTimestamp()))).getTime();
                } catch (ParseException e) {  }
                duration = a.getDuration();
                break;
            }
        }

        int timeoutNum = 0;
        int errorNum = 0;
        for(BinaryAnnotation b : span.getBinaryAnnotations()){
            if("timeout.exception.dubbo".equals(b.getKey())){
                timeoutNum ++;
            }else if("exception.dubbo".equals(b.getKey()) || "exception.web".equals(b.getKey())){
                errorNum ++;
            }
        }

        ReqStat reqStat = new ReqStat(2);
        reqStat.setAppId(-1);
        reqStat.setServiceId(serviceid);
        reqStat.setMethod(span.getSpanName());
        reqStat.setHost(host);
        reqStat.setDuration(duration);
        reqStat.setLogTime(logTime);

        reqStat.setReqNum(1);
        reqStat.setTimeoutNum(timeoutNum);
        reqStat.setErrorNum(errorNum);
        return reqStat;
    }

    public static ReqStat spanToReqStat(SpanWeb spanWeb) {
        DateFormat format = new SimpleDateFormat(yyyy_MM_dd_HH);
        int serviceid = -1;
        try {
            serviceid = Integer.parseInt(spanWeb.getServiceId());
        }catch (Exception e){  }

        String host = "0.0.0.0:0";
        Long logTime = 0L;
        int duration = 0;
        int appId = -1;
        for(AnnotationWeb a : spanWeb.getAnnotationWebs()){
            if("sr".equals(a.getK())){
                host = a.getIp()+":"+a.getPort();
                try {
                    logTime = format.parse(format.format(new Date(a.getTimestamp()))).getTime();
                } catch (ParseException e) {  }
                duration = a.getDuration();
                appId = a.getAppId();
                break;
            }
        }

        int timeoutNum = 0;
        int errorNum = 0;
        for(BinaryAnnotationWeb b : spanWeb.getBinaryAnnotationWebs()){
            if("timeout.exception.dubbo".equals(b.getKey())){
                timeoutNum ++;
            }else if("exception.dubbo".equals(b.getKey()) || "exception.web".equals(b.getKey())){
                errorNum ++;
            }
        }

        ReqStat reqStat = new ReqStat(1);
        reqStat.setAppId(appId);
        reqStat.setServiceId(serviceid);
        reqStat.setMethod(spanWeb.getServiceName());
        reqStat.setHost(host);
        reqStat.setDuration(duration);
        reqStat.setLogTime(logTime);

        reqStat.setReqNum(1);
        reqStat.setTimeoutNum(timeoutNum);
        reqStat.setErrorNum(errorNum);
        return reqStat;
    }

    public static boolean isRoot(Span span) {
        return span.getParentId() == null;
    }


}
