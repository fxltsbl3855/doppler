package com.jd.bdp.hydra.agent.support;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.rpc.RpcContext;
import com.jd.bdp.hydra.*;
import com.jd.bdp.hydra.agent.SyncTransfer;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Date: 13-3-19
 * Time: 下午6:26
 * 异步发送实现类
 */
public class DefaultSyncTransfer implements SyncTransfer {

    private static Logger logger = LoggerFactory.getLogger(DefaultSyncTransfer.class);

    private ArrayBlockingQueue<Span> queue;

    private ScheduledExecutorService executors = null;
    private List<Span> spansCache;
    private String appName;

    //serviceName isReady
    private volatile boolean isReady = false; //是否获得种子等全局注册信息
    private volatile long lastSendTime = System.currentTimeMillis();

    private ConcurrentHashMap<String, Boolean> isServiceReady = new ConcurrentHashMap<String, Boolean>();
    private ConcurrentHashMap<String,Integer> serviceInvokeRoleMap = new ConcurrentHashMap<String, Integer>();

    private GenerateTraceId generateTraceId = new GenerateTraceId(0L);
    private TraceService traceService;
    private Long flushSize;
    private Long waitTime;
    private TransferTask task;


    @Override
    public void setTraceService(TraceService traceService) {
        this.traceService = traceService;
    }

    public DefaultSyncTransfer(Configuration c) {
        this.flushSize = c.getFlushSize() == null ? 1024L : c.getFlushSize();
        this.waitTime = c.getDelayTime() == null ? 60000L : c.getDelayTime();
        this.queue = new ArrayBlockingQueue<Span>(c.getQueueSize());
        this.spansCache = new ArrayList<Span>();
        this.executors = Executors.newSingleThreadScheduledExecutor();
        this.task = new TransferTask();
    }

    @Override
    public String appName() {
        return appName;
    }

    private class TransferTask extends Thread {
        TransferTask() {
            this.setName("TransferTask-Thread");
        }

        @Override
        public void run() {
            for (; ; ) {
                try {
                    if (!isReady()) {//重试直到注册成功
                        //全局信息网络注册，输入流：应用名 @ 输出流：包含种子的Map对象
                        boolean r = traceService.registerService(appName(), new ArrayList<String>(),serviceInvokeRoleMap);
                        if (r) {
                            generateTraceId = new GenerateTraceId(traceService.getSeed());
                            isReady = true;
                        } else {
                            synchronized (this) {
                                this.wait(waitTime);
                            }
                        }
                    } else {
                        while (!task.isInterrupted()) {
                            //检查是否有未注册服务，先注册
                            for (Map.Entry<String, Boolean> entry : isServiceReady.entrySet()) {
                                if (false == entry.getValue()) {//没有注册，先注册
                                    Integer sR = serviceInvokeRoleMap.get(entry.getKey());
                                    boolean r = traceService.registerService(appName(), entry.getKey(), sR == null? -1:sR.intValue());
                                    if (r) {
                                        entry.setValue(true);
                                    }
                                }
                            }
                            //-----------------------------
                            //批量发，避免单条发送多次
                            if(queue.size() > 10 || (System.currentTimeMillis() -lastSendTime) > 2000) {
                                Span first = queue.take();
                                spansCache.add(first);
                                queue.drainTo(spansCache);
                                fillAppServInfo(spansCache);
                                traceService.sendSpan(spansCache);
                                if (logger.isDebugEnabled()) {
                                    logger.debug("[hydra] loop queue data end....spansCache.size=" + spansCache.size());
                                }
                                spansCache.clear();
                                lastSendTime = System.currentTimeMillis();
                            }
                        }
                    }

                } catch (Throwable e) {
                    e.printStackTrace();
                    logger.error(e);
                }
            }
        }
    }

    public void fillAppServInfo(List<Span> spanList) {
        for(Span ss : spanList){
            if(logger.isDebugEnabled()) {
                logger.debug("span=" + ss.getId() + "_traceId=" + ss.getTraceId());
            }
            List<Annotation> aList =  ss.getAnnotations();
            Long startTime = 0L;
            Long endTime = 0L;
            for(Annotation aw : aList){
                if(Annotation.SERVER_SEND.equals(aw.getValue()) || Annotation.CLIENT_RECEIVE.equals(aw.getValue())){
                    endTime = aw.getTimestamp();
                }else if(Annotation.SERVER_RECEIVE.equals(aw.getValue()) || Annotation.CLIENT_SEND.equals(aw.getValue())){
                    startTime = aw.getTimestamp();
                }
            }
            Long du = endTime - startTime;
            for(int i=aList.size()-1;i>=0;i--){
                //去掉 cs,ss
                if(Annotation.CLIENT_SEND.equals(aList.get(i).getValue()) ||
                        Annotation.SERVER_SEND.equals(aList.get(i).getValue())) {
                    aList.remove(i);
                    continue;
                }
                aList.get(i).setDuration(du.intValue());
            }
        }
    }

//    public static void main(String[] a){
//        List<Span> spanList = new ArrayList<Span>();
//        Span ss = new Span();
//        Annotation ass = new Annotation();
//        ass.setTimestamp(10L);
//        ass.setValue("sr");
//
//        Annotation ass1 = new Annotation();
//        ass1.setTimestamp(23L);
//        ass1.setValue("ss");
//
//        ss.addAnnotation(ass1);
//        ss.addAnnotation(ass);
//        spanList.add(ss);
//
//        fillAppServInfo(spanList);
//
//        System.out.print(ass1);
//    }

    @Override
    public boolean isReady() {
        return isReady;
    }

    @Override
    public boolean isServiceReady(String serviceName) {
        if (serviceName != null && isServiceReady.containsKey(serviceName))
            return isServiceReady.get(serviceName);
        else
            return false;
    }

    @Override
    public void syncSend(Span span) {
        try {
            queue.add(span);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void start() throws Exception {
        logger.info("[hydra] trace begin start..traceService="+traceService+",task.isAlive()="+task.isAlive());
        if (traceService != null && !task.isAlive()) {
            task.start();
            logger.info("[hydra] trace started.....start record......");
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    cancel();
                }
            });
        } else if (traceService == null) {
            throw new Exception("TraceServie is null.can't starting SyncTransfer");
        }
        logger.info("[hydra] trace started with no error,start record");
    }

    public void cancel() {
        task.interrupt();
    }

    @Override
    public String getServiceId(String name,int serviceInvokeRole) {
        String serviceId = null;
        serviceId = traceService.getServiceId(name);
        if(serviceId == null){
            logger.warn("[hydra] service register fail,name="+name+",registerInfo="+traceService.registerInfo);
        }
        //可能是未注册的服务
        if (null == serviceId) {
            isServiceReady.putIfAbsent(name, false);//设置未注册标志，交给task去注册
            serviceInvokeRoleMap.put(name, Integer.valueOf(serviceInvokeRole));
        }
        return serviceId;
    }

    @Override
    public Long getTraceId() {
        return generateTraceId.getTraceId();
    }
    @Override
    public Long getSpanId() {
        return generateTraceId.getTraceId();
    }

    @Override
    public void setInvokeInfo(String appName){
        this.appName = appName;
    }
}
