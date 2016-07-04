package com.sinoservices.doppler;

import com.alibaba.dubbo.rpc.RpcContext;
import com.sinoservices.doppler.bo.BusLogBo;
import com.sinoservices.doppler.facade.TraceFacade;
import com.sinoservices.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class TraceUtil {
    private static final Logger logger = LoggerFactory.getLogger(TraceUtil.class);

    public static void sendLog(TraceFacade traceFacade, int errorType, String str){
        if(logger.isDebugEnabled()) {
            logger.debug("TraceUtil.sendLog,errorType=" + errorType + ",str=" + str + ",traceFacade=" + traceFacade);
        }
        if(traceFacade == null){
            logger.error("TraceFacade is null,please config TraceFacade");
            return;
        }
        try {
            switch (errorType) {
                case TraceContants.ERROR_TYPE_ERROR:
                    traceFacade.error((get(str)));
                    break;
                case TraceContants.ERROR_TYPE_FETAL:
                    traceFacade.fetal((get(str)));
                    break;
                case TraceContants.ERROR_TYPE_WARN:
                    traceFacade.warn((get(str)));
                    break;
                default:
                    traceFacade.info((get(str)));
            }
        }catch (Exception e){
            logger.error("TraceFacade invoke error,e="+e.getMessage(),e);
        }
    }

    public static BusLogBo get(String str){
        BusLogBo busLogBo = new BusLogBo();

        RpcContext context = RpcContext.getContext();
        String ip = context.getLocalAddressString();
        String host = context.getLocalHost();
        int port = context.getLocalPort();
        int remote_port = context.getRemotePort();
        Long traceId = NumberUtil.formatLong(context.getAttachment("TRACE_ID"),-1);
        Long spanId = NumberUtil.formatLong(context.getAttachment("PARENT_SPAN_ID"),-1);
        int servceId =NumberUtil.formatNumber(context.getAttachment("SERVICE_ID"),-1);
        String servceName =context.getAttachment("SERVICE_NAME");
        if(logger.isInfoEnabled()) {
            logger.info( "TraceUtil generate param,ip="+ip +",traceId=" + traceId + ",spanId=" + spanId + ",host="
                    + host + ",port=" + port + ",servceId=" + servceId + ",servceName=" + servceName + ",remote_port=" + remote_port);
        }
        busLogBo.setAddr(ip);
        busLogBo.setTraceId(traceId);
        busLogBo.setSpanId(spanId);
        busLogBo.setServiceId(servceId);
        busLogBo.setServiceName(servceName);
        busLogBo.setLogTime(System.currentTimeMillis());
        busLogBo.setLogType(TraceContants.LOG_TYPE_BUSLOG);
        busLogBo.setLogInfo(str);
        return busLogBo;
    }


}
