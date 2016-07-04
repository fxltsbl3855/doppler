/*
 * Copyright 1999-2011 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jd.bdp.hydra.dubbo;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.container.Container;
import com.alibaba.dubbo.container.spring.SpringContainer;
import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.remoting.TimeoutException;
import com.alibaba.dubbo.rpc.*;
import com.jd.bdp.hydra.BinaryAnnotation;
import com.jd.bdp.hydra.Endpoint;
import com.jd.bdp.hydra.HydraConstants;
import com.jd.bdp.hydra.Span;
import com.jd.bdp.hydra.agent.Tracer;
import com.jd.bdp.hydra.agent.support.TracerUtils;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.jd.bdp.hydra.bo.TraceBo;
import com.jd.bdp.hydra.exception.HydraException;
import com.jd.bdp.hydra.util.HydraUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Activate(group = {Constants.PROVIDER, Constants.CONSUMER})
public class HydraFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(HydraFilter.class);
    private static List<String> list = new ArrayList<String>();

    private Tracer tracer = null;

    // 调用过程拦截
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String serviceName = RpcContext.getContext().getUrl().getServiceInterface();
        if(list.contains(serviceName) ){
            if(logger.isDebugEnabled()) {
                logger.debug("[hydra] drop serviceName=" + serviceName);
            }
            return invoker.invoke(invocation);
        }
        if(logger.isDebugEnabled()) {
            logger.debug("[hydra] hydra-client invoke...");
        }
        RpcContext context = RpcContext.getContext();
        boolean isConsumerSide = context.isConsumerSide();

        String serviceId = tracer.getServiceId(serviceName,isConsumerSide?HydraConstants.SERVICE_INVOKE_ROLE_UP:HydraConstants.SERVICE_INVOKE_ROLE_DOWN);
        String appName = RpcContext.getContext().getUrl().getParameters().get("application");
        tracer.setInvokeInfo(appName);

        if(logger.isDebugEnabled()) {
            logger.debug("[hydra] serviceId=" + serviceId + ",url=" + RpcContext.getContext().getUrl().toString() + ",appName=" + appName + ",isConsumerSide=" + isConsumerSide);
        }
        if (serviceId == null) {
            Tracer.startTraceWork();
            return invoker.invoke(invocation);
        }
        long start = System.currentTimeMillis();
        Span span = null;
        Endpoint endpoint = null;
        try {
            endpoint = tracer.newEndPoint();
//            endpoint.setServiceName(serviceId);
            endpoint.setIp(context.getLocalAddressString());
            endpoint.setPort(context.getLocalPort());
            if (context.isConsumerSide()) { //是否是消费者
                if(logger.isDebugEnabled()) {
                    logger.debug("[hydra] consumer serviceId=" + serviceId);
                }
                Span span1 = tracer.getParentSpan();
                if(span1 == null){ //检验是否parent是web,或同级请求，是的话,web的span为父span
                    if(logger.isDebugEnabled()) {
                        logger.debug("[hydra] attempt get web span");
                    }
                    span1 = getCheckWebSpan();
                }
                if (span1 == null) { //为rootSpan
                    span = tracer.newSpan(context.getMethodName(), endpoint, serviceId,RpcContext.getContext().getAttachment(HydraConstants.TRACE_ID));//生成root Span
                    if(logger.isDebugEnabled()) {
                        logger.debug("[hydra] span created ,spanId=" + span.getId() + ",pid=" + span.getParentId() + ",tid=" + span.getTraceId());
                    }
                } else {
                    span = tracer.genSpan(span1.getTraceId(), span1.getId(), tracer.genSpanId(), context.getMethodName(), span1.isSample(), serviceId);
                    if(logger.isDebugEnabled()) {
                        logger.debug("[hydra] span passed ,spanId=" + span.getId() + ",pid=" + span.getParentId() + ",tid=" + span.getTraceId());
                    }
                }
            } else if (context.isProviderSide()) {
                if(logger.isDebugEnabled()) {
                    logger.debug("[hydra] provider serviceId=" + serviceId);
                }
                Long traceId, parentId, spanId;
                traceId = TracerUtils.getAttachmentLong(invocation.getAttachment(TracerUtils.TID));
                parentId = TracerUtils.getAttachmentLong(invocation.getAttachment(TracerUtils.PID));
                spanId = TracerUtils.getAttachmentLong(invocation.getAttachment(TracerUtils.SID));
                boolean isSample = (traceId != null);
                if(logger.isDebugEnabled()) {
                    logger.debug("[hydra] provider traceId=" + traceId + ",isSample=" + isSample);
                }
                span = tracer.genSpan(traceId, parentId, spanId, context.getMethodName(), isSample, serviceId);
            }
            if(logger.isDebugEnabled()) {
                logger.debug("[hydra] hydra-client invokerBefore");
            }
            invokerBefore(invocation, span, endpoint, start);//记录annotation
            RpcInvocation invocation1 = (RpcInvocation) invocation;
            setAttachment(span, invocation1);//设置需要向下游传递的参数
            setAttachmentToContext(context,span.getTraceId(),span.getId(),serviceId);
            setAttachmentToLevel(span.getTraceId(),span.getParentId());

            Result result = invoker.invoke(invocation);
            if (result.getException() != null){
                logger.info("[hydra] hydra-client catch exception of result,and save it");
                catchException(result.getException(), endpoint,span);
            }
            return result;
        }catch (RpcException e) {
            logger.info("RpcException occured,e="+e.getMessage());
            logger.error(e);
            if (e.getCause() != null && e.getCause() instanceof TimeoutException){
                catchTimeoutException(e, endpoint,span);
            }else {
                catchException(e, endpoint,span);
            }
            throw e;
        }catch (Exception ee) {
            logger.error(ee);
            catchException(ee, endpoint,span);
            logger.info("[hydra] hydra-client exception occured,size="+(span.getBinaryAnnotations()==null?0:span.getBinaryAnnotations().size()));
            throw new HydraException(ee);
        } finally{
            if(logger.isDebugEnabled()) {
                logger.debug("[hydra] hydra-client to db...span=" + span);
            }
            if (span != null) {
                long end = System.currentTimeMillis();
                if(logger.isDebugEnabled()) {
                    logger.debug("[hydra] hydra-client invokerAfter");
                }
                invokerAfter(invocation, endpoint, span, end, isConsumerSide);//调用后记录annotation
            }
        }
    }

    private Span getCheckWebSpan(){
        Long webTraceId = TracerUtils.getAttachmentLong(RpcContext.getContext().getAttachment(HydraConstants.TRACE_ID));
        Long webParentSpanId = TracerUtils.getAttachmentLong(RpcContext.getContext().getAttachment(HydraConstants.PARENT_SPAN_ID));
        if(webTraceId == null || webParentSpanId == null) {
            if(logger.isDebugEnabled()) {
                logger.debug("[hydra] get traceid from web span fail");
            }
            TraceBo traceBo = tracer.getLevelTrace();
            if(traceBo == null || traceBo.getTraceId() ==null || traceBo.getParentSpanId()==null){
                logger.warn("[hydra] get traceid from level threadlocal fail");
                return null;
            }
            webTraceId = traceBo.getTraceId();
            webParentSpanId = traceBo.getParentSpanId();
            if(logger.isDebugEnabled()) {
                logger.debug("[hydra] get traceid from level threadlocal success");
            }
        }
        Span span = new Span();
        span = new Span();
        span.setTraceId(webTraceId);
        span.setId(webParentSpanId);
        span.setSample(true);
        if(logger.isDebugEnabled()) {
            logger.debug("[hydra] get web span success,webTraceId=" + webTraceId + "_webParentSpanId=" + webParentSpanId);
        }
        return span;
    }

    private void catchTimeoutException(RpcException e, Endpoint endpoint,Span span) {
        BinaryAnnotation exAnnotation = new BinaryAnnotation();
        exAnnotation.setKey(TracerUtils.EXCEPTION_TIMEOUT);
        exAnnotation.setValue(TracerUtils.EXCEPTION_TIMEOUT_VALUE);
        exAnnotation.setType("exTimeout");
        exAnnotation.setHost(endpoint);
        exAnnotation.setTimestamp(System.currentTimeMillis());
//        tracer.addBinaryAnntation(exAnnotation);
        span.addBinaryAnnotation(exAnnotation);
    }

    private void catchException(Throwable e, Endpoint endpoint,Span span) {
        BinaryAnnotation exAnnotation = new BinaryAnnotation();
        exAnnotation.setKey(TracerUtils.EXCEPTION);
        exAnnotation.setValue(HydraUtil.getDisplayValue(e,HydraUtil.getParaStr(RpcContext.getContext().getArguments())));
        exAnnotation.setType("ex");
        exAnnotation.setHost(endpoint);
        exAnnotation.setTimestamp(System.currentTimeMillis());
//        tracer.addBinaryAnntation(exAnnotation);
        span.addBinaryAnnotation(exAnnotation);
    }

    private void setAttachment(Span span, RpcInvocation invocation) {
        if (span.isSample()) {
            invocation.setAttachment(TracerUtils.PID, span.getParentId() != null ? String.valueOf(span.getParentId()) : null);
            invocation.setAttachment(TracerUtils.SID, span.getId() != null ? String.valueOf(span.getId()) : null);
            invocation.setAttachment(TracerUtils.TID, span.getTraceId() != null ? String.valueOf(span.getTraceId()) : null);
        }
    }

    private void setAttachmentToContext(RpcContext context,Long traceId,Long spanId,String serviceId) {
        RpcContext.getContext().setAttachment(HydraConstants.TRACE_ID, String.valueOf(traceId));
        RpcContext.getContext().setAttachment(HydraConstants.PARENT_SPAN_ID, String.valueOf(spanId));
        RpcContext.getContext().setAttachment(HydraConstants.SERVICE_ID, serviceId);
        if(logger.isDebugEnabled()) {
            logger.debug("traceId set to RpcContext.getContext,traceId=" + traceId);
        }
    }

    private void setAttachmentToLevel(Long traceId,Long parentSpanId) {
        tracer.setLevelTrace(new TraceBo(traceId,parentSpanId));
        if(logger.isDebugEnabled()) {
            logger.debug("TraceBo set to RpcContext.getContext,traceId=" + traceId + ",parentSpanId=" + parentSpanId);
        }
    }

    private void invokerAfter(Invocation invocation, Endpoint endpoint, Span span, long end, boolean isConsumerSide) {
        if (isConsumerSide && span.isSample()) {
            if(logger.isDebugEnabled()) {
                logger.debug("[hydra] invokerAfter client,sample=" + span.isSample());
            }
            tracer.clientReceiveRecord(span, endpoint, end);
        } else {
            if(logger.isDebugEnabled()) {
                logger.debug("[hydra] invokerAfter server,sample=" + span.isSample());
            }
            if (span.isSample()) {
                tracer.serverSendRecord(span, endpoint, end);
            }else{
                if(logger.isDebugEnabled()) {
                    logger.debug("[hydra] isSample = false, not record current trace,service=" + span.getServiceId());
                }
            }
            tracer.removeParentSpan();
        }
    }

    private void invokerBefore(Invocation invocation, Span span, Endpoint endpoint, long start) {
        RpcContext context = RpcContext.getContext();
        if (context.isConsumerSide() && span.isSample()) {
            tracer.clientSendRecord(span, endpoint, start);
        } else if (context.isProviderSide()) {
            if (span.isSample()) {
                tracer.serverReceiveRecord(span, endpoint, start);
            }
            tracer.setParentSpan(span);
        }
    }

    //setter
    public void setTracer(Tracer tracer) {
        this.tracer = tracer;
    }

    /*加载Filter的时候加载hydra配置上下文*/
    static {
        logger.info("Hydra filter is loading hydra-config file...");
        String resourceName = "classpath*:hydra-config.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
                resourceName
        });
        logger.info("Hydra config context is starting,config file path is:" + resourceName);
        context.start();

        list.add("com.alibaba.dubbo.monitor.MonitorService");
        list.add("com.jd.bdp.hydra.dubbomonitor.LeaderService");
        list.add("com.jd.bdp.hydra.dubbomonitor.HydraService");
        list.add("com.sinoservices.doppler.facade.TraceFacade");
        logger.info("Hydra BlackServices init..list=" + list);
    }
}