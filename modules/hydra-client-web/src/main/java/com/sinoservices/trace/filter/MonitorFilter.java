package com.sinoservices.trace.filter;


import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.rpc.RpcContext;
import com.jd.bdp.hydra.*;
import com.jd.bdp.hydra.util.HydraUtil;
import com.sinoservices.trace.HydraWebConstant;
import com.sinoservices.trace.TraceUtil;
import com.sinoservices.trace.TracerWeb;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/1/18.
 */
public class MonitorFilter implements Filter{
    private static final Logger logger = LoggerFactory.getLogger(MonitorFilter.class);
    private static TracerWeb tracer = null;
    Pattern pattern = null;
    public String urlPattern;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if(urlPattern == null || "".equals(urlPattern.trim())){
            urlPattern = ".*\\.[s]{0,1}html.*";
        }
        pattern = Pattern.compile(urlPattern);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        if(logger.isDebugEnabled()) {
            logger.debug("[hydra-web] doFilter start..............");
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        String cUrl = httpServletRequest.getRequestURI();
        if(!match(cUrl)){
            filterChain.doFilter(servletRequest,servletResponse);
            if(logger.isDebugEnabled()) {
                logger.debug("[hydra-web] doFilter drop..............cUrl=" + cUrl+",urlPattern="+urlPattern+",pattern="+pattern);
            }
            return;
        }

        String deployPath = TraceUtil.getDeployPath(servletRequest.getRealPath("/"));
        String serviceName = TraceUtil.getBrifePath(cUrl,deployPath);

        Long start = System.currentTimeMillis();
        Long traceId = TraceUtil.getTraceId();
        Long spanId = TraceUtil.getTraceId();
        RpcContext.getContext().setAttachment(HydraWebConstant.TRACE_ID, String.valueOf(traceId));
        RpcContext.getContext().setAttachment(HydraWebConstant.PARENT_SPAN_ID, String.valueOf(spanId));
        RpcContext.getContext().setAttachment(HydraWebConstant.SERVICE_NAME, serviceName);

        SpanWeb spanWeb = new SpanWeb(traceId,-1L,spanId);
        AnnotationWeb annotationWeb = new AnnotationWeb("sr",start,servletRequest.getLocalAddr(),servletRequest.getLocalPort());
        spanWeb.addAnnotationWeb(annotationWeb);
        if(logger.isDebugEnabled()) {
            logger.debug("[hydra-web] ....................traceId=" + traceId + "_spanId=" + spanId + "_serviceName=" + serviceName);
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        }catch (Exception e){
            logger.info("[hydra-web] catch HydraWebException,e="+e.getMessage());
            logger.error(e);
            catchException(e,servletRequest,spanWeb, traceId,spanId);
            throw new ServletException(e);
        }finally {
            Long end = System.currentTimeMillis();
            AnnotationWeb annotationWeb2 = new AnnotationWeb("ss",end,servletRequest.getLocalAddr(),servletRequest.getLocalPort());
            spanWeb.addAnnotationWeb(annotationWeb2);

            String appName = TraceUtil.getAppNameByDubboProp();
            if(appName == null || "".equals(appName.trim()) || "null".equals(appName.trim())){
                logger.error("[hydra-web] cann't get appName from dubbo.properties,may be not use it,please check it");
                return;
            }
            TraceUtil.assimble(servletRequest,spanWeb,cUrl,end - start,appName,traceId,spanId);
            if(tracer !=null) {
                tracer.syncSend(spanWeb);
            }else{
                logger.error("[hydra-web] doFilter tracer is null,hydra-config-web.xml  load error");
            }
            if(logger.isDebugEnabled()) {
                logger.debug("[hydra-web] doFilter end..............");
            }
        }

    }

    private void catchException(Throwable e,ServletRequest servletRequest, SpanWeb spanWeb,long traceId,long spanId) {
        BinaryAnnotationWeb exAnnotation = new BinaryAnnotationWeb();
        exAnnotation.setKey("exception.web");
        exAnnotation.setValue(HydraUtil.getDisplayValue(e,HydraUtil.getParaStr(servletRequest.getParameterMap())));
        exAnnotation.setType("ex");
        exAnnotation.setHost(new Endpoint(servletRequest.getLocalAddr(),servletRequest.getLocalPort()));
        exAnnotation.setTimestamp(System.currentTimeMillis());

        exAnnotation.setSpanId(spanId);
        exAnnotation.setTraceId(traceId);
        spanWeb.addBinaryAnnotation(exAnnotation);
    }


    @Override
    public void destroy() {}

    /**
     * url是否需要统计
     * @param currentUrl
     * @return
     */
    public boolean match(String currentUrl){
        //按配置过滤
        if(pattern != null){
            Matcher matcher = pattern.matcher(currentUrl);
            if(logger.isDebugEnabled()) {
                logger.debug("[hydra-web] doFilter match url,cUrl=" + currentUrl + ",urlPattern=" + urlPattern + ",pattern=" + pattern + ",result=" + matcher.matches());
            }
            return matcher.matches();
        }
        logger.error("MonitorFilter init error,pattern is null");
        //默认过滤shtml
        if(currentUrl.indexOf(".shtml")>0){
            return true;
        }
        return false;
    }
    /*加载Filter的时候加载hydra配置上下文*/
    static {
        logger.info("Hydra filter is loading hydra-config file...");
        String resourceName = "classpath*:hydra-config-web.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
                resourceName
        });
        logger.info("Hydra config context is starting,config file path is:" + resourceName);
        context.start();
        tracer = (TracerWeb)context.getBean("tracer");
    }
}
