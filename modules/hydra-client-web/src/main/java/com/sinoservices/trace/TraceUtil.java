package com.sinoservices.trace;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.jd.bdp.hydra.AnnotationWeb;
import com.jd.bdp.hydra.SpanWeb;
import com.sinoservices.trace.register.Register;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/1/19.
 */
public class TraceUtil {

    private static final Logger logger = LoggerFactory.getLogger(TraceUtil.class);

    public static void main(String[] a){
        String appNameWithVersion = getBrifePath("/ruite-web-2.8.1/test/go.shtml?a=nn&s=c","ruite-web-2.8.1");
        System.out.println(appNameWithVersion);
    }

    /**
     * 获取路径的app name
     * //D:\bak\study_bak\Tomcat-6.0.37-32bit\Tomcat-6.0.37-32bit\webapps\WebRoot\ 为   WebRoot
     * D:\bak\study_bak\Tomcat-6.0.37-32bit\Tomcat-6.0.37-32bit\webapps\doppler-web-1.0 为    doppler-web-1.0
     *
     * @param str
     * @return
     */
    public static String getDeployPath(String str){
        if(str == null || "".equals(str)){
            return "";
        }
        if(str.endsWith("\\") ||str.endsWith("/")  ){
            str = str.substring(0,str.length()-1);
        }
        if(str.indexOf("\\") >= 0) {
            int from = str.lastIndexOf("\\");
            str = str.substring(from + 1);
        }else if(str.indexOf("/") >= 0){
            int from = str.lastIndexOf("/");
            str = str.substring(from + 1);
        }
        return str;
    }



    /**
     * 从dubbo.properties中获取应用名称
     * @return
     */
    public static String getAppNameByDubboProp(){
        return ConfigUtils.getProperty("dubbo.application.name");
    }

    public static void assimble(ServletRequest servletRequest,SpanWeb spanWeb, String reqPath, Long duration,String appName,long traceId,long spanId){
        if(spanWeb == null || spanWeb.getAnnotationWebs() == null || spanWeb.getAnnotationWebs().size()==0){
            return;
        }
        if(logger.isDebugEnabled()) {
            logger.debug("[hydra-web] gen  serviceName ,reqPath=" + reqPath + ",appName=" + appName);
        }
        List<AnnotationWeb> aList =  spanWeb.getAnnotationWebs();
        String deployPath = getDeployPath(servletRequest.getRealPath("/"));
        if(!Register.started){
            Register res = new Register(appName,servletRequest.getLocalPort());
            res.start();
        }
        reqPath = getBrifePath(reqPath,deployPath);
        if(logger.isDebugEnabled()) {
            logger.debug("[hydra-web] after gen  serviceName ,serviceName=" + reqPath + ",appName=" + appName);
        }
        spanWeb.setAppName(appName);
        spanWeb.setServiceName(reqPath);
        for(AnnotationWeb aw : aList){
            aw.setDuration(duration.intValue());
            aw.setAppName(appName);
            aw.setServiceName(reqPath);
            aw.setTraceId(traceId);
            aw.setSpanId(spanId);
        }
    }

    /**
     * 去掉相对url后的/，去掉应用名
     * @param reqPath
     * @param appName
     * @return
     */
    public static String getBrifePath(String reqPath,String appName){
        if(reqPath == null || "".equals(reqPath.trim())){
            return "";
        }
        if(reqPath.indexOf("？") > 0){
            reqPath = reqPath.substring(0,reqPath.indexOf("？"));
        }
        if(reqPath.indexOf("?") > 0){
            reqPath = reqPath.substring(0,reqPath.indexOf("?"));
        }
        if(reqPath.startsWith(appName)){
            reqPath = reqPath.replaceFirst(appName,"");
        }
        if(reqPath.startsWith("/"+appName)){
            reqPath = reqPath.replaceFirst("/"+appName,"");
        }
        if(reqPath.endsWith("/")&&reqPath.length()>1){
            reqPath = reqPath.substring(0,reqPath.length()-1);
        }
        return reqPath;
    }
    public static Long getTraceId(){
        double dd = Math.random()*100000000000D;
        Long id = Double.doubleToLongBits(dd);
        return id;
    }





}
