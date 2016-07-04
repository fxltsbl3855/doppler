package com.sinoservices.trace;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.jd.bdp.hydra.Annotation;
import com.jd.bdp.hydra.AnnotationWeb;
import com.jd.bdp.hydra.BinaryAnnotationWeb;
import com.jd.bdp.hydra.SpanWeb;
import com.jd.bdp.hydra.dubbomonitor.HydraService;
import com.jd.bdp.hydra.dubbomonitor.LeaderService;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Date: 13-3-19
 * Time: 下午6:26
 * 异步发送实现类
 */
public class DefaultSyncTransferWeb  {

    private static Logger logger = LoggerFactory.getLogger(DefaultSyncTransferWeb.class);

    private ArrayBlockingQueue<SpanWeb> queue;
    private ScheduledExecutorService executors = null;
    private List<SpanWeb> spansCache;
    private volatile long lastSendTime = System.currentTimeMillis();

    private HydraService hydraService;
    private LeaderService leaderService;
    private TransferTask task;

    private ConcurrentHashMap<String, String> isAppServiceReady = new ConcurrentHashMap<String, String>();

    public DefaultSyncTransferWeb() {
        this.queue = new ArrayBlockingQueue<SpanWeb>(1024);
        this.spansCache = new ArrayList<SpanWeb>();
        this.executors = Executors.newSingleThreadScheduledExecutor();
        this.task = new TransferTask();
    }

    private class TransferTask extends Thread {
        TransferTask() {
            this.setName("TransferTaskWeb-Thread");
        }

        @Override
        public void run() {
            for (; ; ) {
                try {
                    while (!task.isInterrupted()) {
                        if(queue.size() > 10 || (System.currentTimeMillis() -lastSendTime) > 2000) {
                            SpanWeb first = queue.take();
                            spansCache.add(first);
                            queue.drainTo(spansCache);
                            fillAppServInfo(spansCache);
                            if (logger.isDebugEnabled()) {
                                logger.debug("span json=" + JSON.json(spansCache));
                            }
                            hydraService.pushWeb(spansCache);
                            spansCache.clear();
                            lastSendTime = System.currentTimeMillis();
                        }
                    }
                } catch (RpcException re) {
                    logger.error("access rpc host error,e="+re.getMessage());
                }catch (Throwable e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
        }

    }

    public void fillAppServInfo(List<SpanWeb> spanWebList) {
        for(SpanWeb sw : spanWebList){
           String regKey = sw.getAppName()+"#"+sw.getServiceName();

            String appId ;
            String serviceId ;
            if(isAppServiceReady.containsKey(regKey)){
                String ids = isAppServiceReady.get(regKey);
                if(ids == null){
                    logger.error("web filter fillAppServInfo error,ids==null,regKey="+regKey);
                    continue;
                }
                String[] idsArray = ids.split("#");
                if(idsArray.length !=2){
                    logger.error("web filter fillAppServInfo error,idsArray.length!=2,regKey="+regKey+",ids="+ids);
                    continue;
                }
                appId = idsArray[0];
                serviceId = idsArray[1];
            }else{
                List<String> services = new ArrayList<String>(1);
                services.add(sw.getServiceName());
                Map<String, Integer> sRoleMap = new HashMap<String, Integer>();
                sRoleMap.put(sw.getAppName(),2);
                Map<String, String> mm  = leaderService.registerClient(sw.getAppName(),services ,sRoleMap);

                serviceId = mm.get(sw.getServiceName());
                appId = mm.get(sw.getAppName());
                if (serviceId != null) {
                    isAppServiceReady.put(regKey, appId+"#"+serviceId); //更新本地注册信息
                }
            }
            sw.setServiceId(serviceId);
            List<AnnotationWeb> aList =  sw.getAnnotationWebs();
            for(int i=aList.size()-1;i>=0;i--){
                //去掉ss
                if(Annotation.SERVER_SEND.equals(aList.get(i).getK())) {
                    aList.remove(i);
                    continue;
                }
                aList.get(i).setAppId(Integer.valueOf(appId));
                aList.get(i).setServiceId(serviceId);
            }
            List<BinaryAnnotationWeb> baList =  sw.getBinaryAnnotationWebs();
            for(BinaryAnnotationWeb baw : baList){
                baw.setAppId(Integer.valueOf(appId));
                baw.setServiceId(serviceId);
            }
        }

    }

    public void syncSend(SpanWeb spanWeb) {
        try {
            start();
            queue.add(spanWeb);
        } catch (Exception e) {
            logger.error("syncSend error,span web : ignore,e="+e.getMessage());
        }
    }

    public void start() throws Exception {
        if(task.isAlive()){
            return;
        }
        if (hydraService != null) {
            task.start();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    cancel();
                }
            });
        } else if (hydraService == null) {
            logger.error("hydraService is null, start DefaultSyncTransferWeb thread fail.");
            throw new Exception("HydraService is null.can't starting DefaultSyncTransferWeb");
        }
    }

    public void cancel() {
        logger.info("DefaultSyncTransferWeb thread gracefully shutdown.");
        task.interrupt();
    }

    public LeaderService getLeaderService() {
        return leaderService;
    }

    public void setLeaderService(LeaderService leaderService) {
        this.leaderService = leaderService;
    }

    public HydraService getHydraService() {
        return hydraService;
    }

    public void setHydraService(HydraService hydraService) {
        this.hydraService = hydraService;
    }
}
