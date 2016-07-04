package com.sinoservices.trace;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.jd.bdp.hydra.*;

/**
 * Date: 13-3-19
 * Time: 下午4:14
 * 系统跟踪类(单例)
  */

public class TracerWeb {

    private static final Logger logger = LoggerFactory.getLogger(TracerWeb.class);

    private DefaultSyncTransferWeb transfer = null;
    private TracerWeb() {
    }


    private static class  TraceHolder{
        static TracerWeb instance=new TracerWeb();
    }
    public static TracerWeb getTracer() {
       return TraceHolder.instance;
    }

    public void start() throws Exception {
        transfer.start();
    }

    public void syncSend(SpanWeb sList){
        transfer.syncSend(sList);
    }

    public DefaultSyncTransferWeb getTransfer() {
        return transfer;
    }

    public void setTransfer(DefaultSyncTransferWeb transfer) {
        this.transfer = transfer;
    }
}

