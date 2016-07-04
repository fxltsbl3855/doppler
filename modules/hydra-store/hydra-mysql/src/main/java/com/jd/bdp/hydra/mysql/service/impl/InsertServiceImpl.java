package com.jd.bdp.hydra.mysql.service.impl;

import com.alibaba.fastjson.JSON;
import com.jd.bdp.hydra.*;
import com.jd.bdp.hydra.mysql.persistent.dao.AnnotationMapper;
import com.jd.bdp.hydra.mysql.persistent.dao.SpanMapper;
import com.jd.bdp.hydra.mysql.persistent.dao.TraceMapper;
import com.jd.bdp.hydra.mysql.persistent.entity.Absannotation;
import com.jd.bdp.hydra.mysql.persistent.entity.BusLog;
import com.jd.bdp.hydra.mysql.persistent.entity.ReqStat;
import com.jd.bdp.hydra.mysql.persistent.entity.Trace;
import com.jd.bdp.hydra.store.inter.InsertService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: biandi
 * Date: 13-5-9
 * Time: 下午4:13
 */
public class InsertServiceImpl implements InsertService {
    private static final Logger logger = LoggerFactory.getLogger(InsertServiceImpl.class);
    @Override
    public void addReqStatWeb(List<SpanWeb> spanWebList) {
        List<ReqStat> reqStatList = new ArrayList<ReqStat>(spanWebList.size());
        if(logger.isDebugEnabled()) {
            logger.debug("addReqStatWeb spanWebList = " + JSON.toJSONString(spanWebList));
        }
        for(SpanWeb spanWeb :spanWebList ) {
            if (spanWeb.getServiceId() != null) {
                ReqStat reqStat = Utils.spanToReqStat(spanWeb);
                reqStatList.add(reqStat);
            }
        }
        if(logger.isDebugEnabled()) {
            logger.debug("addReqStatWeb reqStatList = " + JSON.toJSONString(reqStatList));
        }
        if(reqStatList.size() > 0) {
            spanMapper.addReqStatBatch(reqStatList);
            spanMapper.addReqStatServiceBatch(reqStatList);
        }
    }

    @Override
    public void addReqStat(List<Span> spanList) {
        List<ReqStat> reqStatList = new ArrayList<ReqStat>(spanList.size());
        if(logger.isDebugEnabled()) {
            logger.debug("addReqStat spanList = " + JSON.toJSONString(spanList));
        }
        for(Span span :spanList ) {
            if (span.getServiceId() != null && Utils.checkStat(span)) {
                ReqStat reqStat = Utils.spanToReqStat(span);
                reqStatList.add(reqStat);
            }
        }
        if(logger.isDebugEnabled()) {
            logger.debug("addReqStatWeb reqStatList = " + JSON.toJSONString(reqStatList));
        }
        if(reqStatList.size() > 0) {
            spanMapper.addReqStatBatch(reqStatList);
            spanMapper.addReqStatServiceBatch(reqStatList);
        }
    }

    @Override
    public void addSpanBatch(List<Span> spanList) {
        List<Span> spans = new ArrayList<Span>(1);
        for(Span span :spanList ) {
            if(logger.isDebugEnabled()) {
                logger.debug("addSpanBatch span = " + JSON.toJSONString(span));
            }
            if (span.getServiceId() != null) {
                if (!Utils.isRoot(span) || Utils.isRoot(span) && Utils.isTopAnntation(span)) {
                    spans.add(span);
                }
            }
        }
        if(logger.isDebugEnabled()) {
            logger.debug("addSpanBatch insert spans = " + JSON.toJSONString(spans));
        }
        if(spans.size() > 0) {
            spanMapper.addSpanBatch(spans);
        }
    }


    @Transactional(rollbackForClassName = "Exception")
    @Override
    public void txAnnoReqstat(List<Span> span) {
        addAnnotationBatch(span);
        addReqStat(span);
    }

    @Transactional(rollbackForClassName = "Exception")
    @Override
    public void txAnnoReqstatWeb(List<SpanWeb> spanWeb) {
        addAnnotationWebBatch(spanWeb);
        addReqStatWeb(spanWeb);
    }

    @Override
    public void addAnnotationBatch(List<Span> spanList) {
        if(spanList == null || spanList.size()==0){
            return;
        }
        List<Absannotation> insertList = new ArrayList<Absannotation>(1);
        List<BusLog> busLogList = new ArrayList<BusLog>(1);
        for(Span span : spanList){
            for(Annotation a : span.getAnnotations()){
                Absannotation aa = new Absannotation(a, span);
                insertList.add(aa);
            }

            for(BinaryAnnotation baw : span.getBinaryAnnotations()){
                Absannotation bb = new Absannotation(baw, span);
                insertList.add(bb);

                BusLog busLog = new BusLog();
                busLog.setAddr(baw.getHost().getIp());
                busLog.setSpanId(span.getId());
                busLog.setTraceId(span.getTraceId());
//            busLog.setAppId(baw.getAppId());
                int serviceId = -1;
                try {
                    serviceId = Integer.parseInt(span.getServiceId());
                }catch (Exception e){
                    e.printStackTrace();
                }
                busLog.setServiceId(serviceId);
                busLog.setLogInfo(baw.getValue());
                busLog.setLogType(BusLog.LOG_TYPE_TRACE);
                busLog.setErrorType(BusLog.ERROR_TYPE_ERROR);
                busLog.setLogTime(baw.getTimestamp());
                busLogList.add(busLog);

            }
        }
        if(logger.isDebugEnabled()) {
            logger.debug("addAnnotationBatch busLogList=" + busLogList.size());
        }
        if(busLogList.size()>0) {
            annotationMapper.addBuslogBatch(busLogList);
        }
        if(logger.isDebugEnabled()) {
            logger.debug("addAnnotationBatch insertList=" + insertList.size());
        }
        if(insertList.size()>0) {
            annotationMapper.addAnnotationBatch(insertList);
        }


    }


    @Override
    public void addAnnotationWebBatch(List<SpanWeb> spanWebList) {
        List<AnnotationWeb> insertList = new ArrayList<AnnotationWeb>();
        List<BusLog> busLogList = new ArrayList<BusLog>();

        for(SpanWeb spanWeb : spanWebList){
            for(AnnotationWeb a : spanWeb.getAnnotationWebs()){
                insertList.add(a);
            }

            for(BinaryAnnotationWeb baw : spanWeb.getBinaryAnnotationWebs()){
                AnnotationWeb aw = new AnnotationWeb(baw.getKey(),baw.getTimestamp(),baw.getHost().getIp(),baw.getHost().getPort());
                aw.setValue(baw.getValue());
                aw.setTraceId(baw.getTraceId());
                aw.setSpanId(baw.getSpanId());
                aw.setAppId(baw.getAppId());
                aw.setServiceId(baw.getServiceId());
                aw.setServiceName(spanWeb.getServiceName());
                aw.setAppName(spanWeb.getAppName());
                aw.setDuration(baw.getDuration());
                insertList.add(aw);

                BusLog busLog = new BusLog();
                busLog.setAddr(baw.getHost().getIp()+":"+baw.getHost().getPort());
                busLog.setSpanId(baw.getSpanId());
                busLog.setTraceId(baw.getTraceId());
//            busLog.setAppId(baw.getAppId());
                int serviceId = -1;
                try {
                    serviceId = Integer.parseInt(baw.getServiceId());
                }catch (Exception e){
                    e.printStackTrace();
                }
                busLog.setServiceId(serviceId);
                busLog.setLogInfo(baw.getValue());
                busLog.setLogType(BusLog.LOG_TYPE_TRACE);
                busLog.setErrorType(BusLog.ERROR_TYPE_ERROR);
                busLog.setLogTime(baw.getTimestamp());
                busLogList.add(busLog);
            }
        }
        if(logger.isDebugEnabled()) {
            logger.debug("addAnnotationWebBatch busLogList = " + JSON.toJSONString(busLogList));
        }
        if(busLogList.size()>0) {
            annotationMapper.addBuslogBatch(busLogList);
        }
        if(logger.isDebugEnabled()) {
            logger.debug("addAnnotationWebBatch insertList = " + JSON.toJSONString(insertList));
        }
        if(insertList.size()>0) {
            annotationMapper.addAnnotationWebBatch(insertList);
        }



    }

    @Override
    public void addSpan(Span span) {
        if (span.getServiceId() != null){
            if (!Utils.isRoot(span) || Utils.isRoot(span) && Utils.isTopAnntation(span)){
                spanMapper.addSpan(span);
            }
        }
    }

    @Override
    public void addTrace(Span span) {
        if (Utils.isTopAnntation(span) && Utils.isRoot(span)) {
            Annotation annotation = Utils.getCrAnnotation(span.getAnnotations());
            Annotation annotation1 = Utils.getCsAnnotation(span.getAnnotations());
            Trace t = new Trace();
            t.setTraceId(span.getTraceId());
            t.setDuration((int) (annotation.getTimestamp() - annotation1.getTimestamp()));
            t.setService(span.getServiceId());
            t.setTime(annotation1.getTimestamp());
            traceMapper.addTrace(t);
        }
    }

    @Override
    public void addAnnotation(Span span){
        List<Absannotation> insertList = new ArrayList<Absannotation>(2);
        for(Annotation a : span.getAnnotations()){
            Absannotation aa = new Absannotation(a, span);
            insertList.add(aa);
        }
        annotationMapper.addAnnotationBatch(insertList);

        for(BinaryAnnotation baw : span.getBinaryAnnotations()){
            Absannotation bb = new Absannotation(baw, span);
            annotationMapper.addAnnotation(bb);

            BusLog busLog = new BusLog();
            busLog.setAddr(baw.getHost().getIp());
            busLog.setSpanId(span.getId());
            busLog.setTraceId(span.getTraceId());
//            busLog.setAppId(baw.getAppId());
            int serviceId = -1;
            try {
                serviceId = Integer.parseInt(span.getServiceId());
            }catch (Exception e){
                e.printStackTrace();
            }
            busLog.setServiceId(serviceId);
            busLog.setLogInfo(baw.getValue());
            busLog.setLogType(BusLog.LOG_TYPE_TRACE);
            busLog.setErrorType(BusLog.ERROR_TYPE_ERROR);
            busLog.setLogTime(baw.getTimestamp());
            annotationMapper.addBuslog(busLog);
        }
    }

    @Override
    public void addAnnotationWeb(SpanWeb spanWeb){
        List<AnnotationWeb> insertList = new ArrayList<AnnotationWeb>(2);
        for(AnnotationWeb a : spanWeb.getAnnotationWebs()){
            insertList.add(a);
        }
        annotationMapper.addAnnotationWebBatch(insertList);

        for(BinaryAnnotationWeb baw : spanWeb.getBinaryAnnotationWebs()){
            AnnotationWeb aw = new AnnotationWeb(baw.getKey(),baw.getTimestamp(),baw.getHost().getIp(),baw.getHost().getPort());
            aw.setValue(baw.getValue());
            aw.setTraceId(baw.getTraceId());
            aw.setSpanId(baw.getSpanId());
            aw.setAppId(baw.getAppId());
            aw.setServiceId(baw.getServiceId());
            aw.setServiceName(spanWeb.getServiceName());
            aw.setAppName(spanWeb.getAppName());
            aw.setDuration(baw.getDuration());
            annotationMapper.addAnnotationWeb(aw);


            BusLog busLog = new BusLog();
            busLog.setAddr(baw.getHost().getIp()+":"+baw.getHost().getPort());
            busLog.setSpanId(baw.getSpanId());
            busLog.setTraceId(baw.getTraceId());
//            busLog.setAppId(baw.getAppId());
            int serviceId = -1;
            try {
                serviceId = Integer.parseInt(baw.getServiceId());
            }catch (Exception e){
                e.printStackTrace();
            }
            busLog.setServiceId(serviceId);
            busLog.setLogInfo(baw.getValue());
            busLog.setLogType(BusLog.LOG_TYPE_TRACE);
            busLog.setErrorType(BusLog.ERROR_TYPE_ERROR);
            busLog.setLogTime(baw.getTimestamp());
            annotationMapper.addBuslog(busLog);
        }
    }



//    public void addAppServWeb(SpanWeb spanWeb){
//        for(AnnotationWeb a : spanWeb.getAnnotationWebs()){
//            annotationMapper.addAnnotationWeb(a);
//        }
//    }

    private AnnotationMapper annotationMapper;
    private SpanMapper spanMapper;
    private TraceMapper traceMapper;

    public void setAnnotationMapper(AnnotationMapper annotationMapper) {
        this.annotationMapper = annotationMapper;
    }

    public void setSpanMapper(SpanMapper spanMapper) {
        this.spanMapper = spanMapper;
    }

    public void setTraceMapper(TraceMapper traceMapper) {
        this.traceMapper = traceMapper;
    }
}
