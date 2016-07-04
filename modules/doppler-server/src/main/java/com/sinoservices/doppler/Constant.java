package com.sinoservices.doppler;

/**
 * Created by Administrator on 2016/1/22.
 */
public class Constant {

    /** req_stat.appType, 1:web应用，2:server应用**/
    public static final int REQ_STAT_APPTYPE_WEB = 1;
    public static final int REQ_STAT_APPTYPE_SERVER = 2;

    /** 监控mysql数据库anootation表中字段k值定义 **/
    public static final String ANNOTATION_K_SR = "sr";
    public static final String ANNOTATION_K_TIMEOUT = "timeout.exception.dubbo";
    public static final String ANNOTATION_K_ERROR = "exception.dubbo";

    /** zk链接常量 **/
    public static final String ROOT_WEB = "webapp";
    public static final String ROOT_SERVER = "dubbo";
    public static final String ZK_PATH_DELIMITER = "/";
    public static final String PROVIDERS = "providers";

    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
}
