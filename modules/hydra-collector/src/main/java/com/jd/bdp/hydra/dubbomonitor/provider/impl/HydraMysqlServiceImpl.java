package com.jd.bdp.hydra.dubbomonitor.provider.impl;

import com.alibaba.fastjson.JSON;
import com.jd.bdp.hydra.Span;
import com.jd.bdp.hydra.SpanWeb;
import com.jd.bdp.hydra.dubbomonitor.HydraService;
import com.jd.bdp.hydra.dubbomonitor.provider.impl.support.Configuration;
import com.jd.bdp.hydra.store.inter.InsertService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HydraMysqlServiceImpl implements HydraService {
    private static final Logger logger = LoggerFactory.getLogger(HydraMysqlServiceImpl.class);

    private int sleepMillSecsWhenError = 500;
    private ArrayBlockingQueue<List<Span>> queue;
    private int taskCount=3;
    private ExecutorService executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private ArrayBlockingQueue<List<SpanWeb>> queueWeb;
    private int taskCountWeb=3;
    private ExecutorService executorsWeb = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    public HydraMysqlServiceImpl() {
        queue = new ArrayBlockingQueue<List<Span>>(2048);
        this.taskCount=3;
        for (int i = 0; i < taskCount; i++) {
            executors.execute(new InsertTask());
        }

        queueWeb = new ArrayBlockingQueue<List<SpanWeb>>(1024);
        this.taskCountWeb=3;
        for (int i = 0; i < taskCountWeb; i++) {
            executorsWeb.execute(new InsertTaskWeb());
        }
    }

    public HydraMysqlServiceImpl(Configuration c) {
        int queueSize= c.getTaskCount() == null ? 2048 : c.getQueueSize();
        this.taskCount = c.getTaskCount() == null ? 3 : c.getTaskCount();
        queue = new ArrayBlockingQueue<List<Span>>(queueSize);
        for (int i = 0; i < taskCount; i++) {
            executors.execute(new InsertTask());
        }

        int queueSizeWeb= c.getTaskCount() == null ? 2048 : c.getQueueSize();
        this.taskCountWeb = c.getTaskCount() == null ? 3 : c.getTaskCount();
        queueWeb = new ArrayBlockingQueue<List<SpanWeb>>(queueSizeWeb);
        for (int i = 0; i < taskCountWeb; i++) {
            executorsWeb.execute(new InsertTaskWeb());
        }
    }

    class InsertTaskWeb implements Runnable {
        @Override
        public void run() {
            while (true) {
                List<SpanWeb> spanWeb = null;
                try {
                    spanWeb = queueWeb.take();
                } catch (InterruptedException e) {
                    logger.error("InsertTaskWeb queueWeb.take InterruptedException",e);
                    continue;
                }
                try {
                    if(logger.isInfoEnabled()) {
                        logger.info("InsertTaskWeb take from queue ,spanWeb(list).size = " + spanWeb.size());
                    }
                    if(logger.isDebugEnabled()) {
                        logger.debug("InsertTaskWeb take from queue ,spanWeb(list) = " + JSON.toJSONString(spanWeb));
                    }
                    if (spanWeb != null && spanWeb.size()>0) {
                        insertService.txAnnoReqstatWeb(spanWeb);
                    }
                } catch (Exception e) {
                    logger.warn("InsertTaskWeb save db exception,and start put to queue,spanWeb="+JSON.toJSONString(spanWeb));
                    try {
                        queueWeb.put(spanWeb);
                        logger.info("InsertTaskWeb save db exception,and put to queue successful,and sleep,spanWeb=" + JSON.toJSONString(spanWeb));
                        Thread.currentThread().sleep(sleepMillSecsWhenError);
                        logger.info("InsertTaskWeb save db exception,and put to queue successful,sleep end");
                    } catch (InterruptedException e1) {
                        logger.error("InsertTaskWeb insert db fail, then put data to queue error",e1);
                        logger.info("InsertTaskWeb lost spanWeb = "+JSON.toJSONString(spanWeb));
                    }
                }
            }
        }
    }

    class InsertTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                List<Span> span = null;
                try {
                    span = queue.take();
                } catch (InterruptedException e) {
                    logger.error("InsertTask queue.take InterruptedException",e);
                    continue;
                }
                try {
                    if(logger.isInfoEnabled()) {
                        logger.info("InsertTask take from queue ,span(list).size = " + span.size());
                    }
                    if(logger.isDebugEnabled()) {
                        logger.debug("InsertTask take from queue ,span(list)=" + JSON.toJSONString(span));
                    }
                    if (span != null && span.size()>0) {
                        insertService.txAnnoReqstat(span);
                    }
                } catch (Exception e) {
                    logger.warn("InsertTask save db exception,and start put to queue,span="+JSON.toJSONString(span));
                    try {
                        queue.put(span);
                        logger.info("InsertTask save db exception,and put to queue successful,and sleep,span=" + JSON.toJSONString(span));
                        Thread.currentThread().sleep(sleepMillSecsWhenError);
                        logger.info("InsertTask save db exception,and put to queue successful,sleep end");
                    } catch (InterruptedException e1) {
                        logger.error("InsertTask insert db fail, then put data to queue error",e1);
                        logger.info("InsertTask lost span = "+JSON.toJSONString(span));
                    }
                }
            }
        }
    }

    @Override
    public boolean push(List<Span> span) throws IOException {
        return queue.add(span);
    }

    @Override
    public boolean pushWeb(List<SpanWeb> spanWeb) throws IOException {
        return queueWeb.add(spanWeb);
    }

    private InsertService insertService;

    public void setInsertService(InsertService insertService) {
        this.insertService = insertService;
    }

    public Span spanWebToSpan(SpanWeb spanWeb) {
        Span span = new Span();
        span.setId(spanWeb.getId());
        span.setParentId(spanWeb.getParentId());
        span.setTraceId(spanWeb.getTraceId());
        span.setSpanName(spanWeb.getServiceName());
        span.setServiceId(spanWeb.getServiceId());
        return span;
    }

    public List<Span> spanWebToSpanBatch(List<SpanWeb> spanWebs) {
        List<Span> spanList = new ArrayList<Span>();
        for(SpanWeb ss : spanWebs){
            spanList.add(spanWebToSpan(ss));
        }
        return spanList;
    }

}