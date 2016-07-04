package com.jd.bdp.hydra.agent.support;


import com.jd.bdp.hydra.Span;
import com.jd.bdp.hydra.agent.CollectorService;
import com.jd.bdp.hydra.agent.RegisterService;
import com.jd.bdp.hydra.dubbomonitor.HydraService;
import com.jd.bdp.hydra.dubbomonitor.LeaderService;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 13-3-27
 * Time: 上午10:57
 */
public class TraceService implements RegisterService, CollectorService {

    private static final Logger logger = LoggerFactory.getLogger(TraceService.class);

    private LeaderService leaderService;
    private HydraService hydraService;
    public Map<String, String> registerInfo;
    public static final String APP_NAME = "applicationName";
    public static final String SEED = "seed";
    private boolean isRegister = false;

    public boolean isRegister() {
        return isRegister;
    }

    @Override
    public void sendSpan(List<Span> spanList) {
        //fixme try-catch性能影响？
        try {
            hydraService.push(spanList);
            if(logger.isDebugEnabled()) {
                logger.debug("[hydra] send anno data out........ " + spanList.size());
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public boolean registerService(String name, List<String> services, Map<String, Integer> map) {
       // logger.info(name + " " + services);
        try {
            this.registerInfo = leaderService.registerClient(name, services, map);
        } catch (Exception e) {
            logger.warn("[Hydra] hydra-client cannot register hydra system,e="+e.getMessage());
        }
        if (registerInfo != null) {
            logger.info("[Hydra] Global registry option is ok!");
            isRegister = true;
        }
        return isRegister;
    }

    /*更新注册信息*/
    @Override
    public boolean registerService(String appName, String serviceName, int serviceInvokeRole) {
        logger.info("[hydra.register] appName="+appName + " serviceName=" + serviceName +" serviceInvokeRole=" + serviceInvokeRole);
        String serviceId = null;
        try {
            if(logger.isDebugEnabled()) {
                logger.debug("[hydra.register] start registerService,appName="+appName +",serviceName="+serviceName+",serviceInvokeRole="+serviceInvokeRole);
            }
            serviceId = leaderService.registerClient(appName, serviceName,serviceInvokeRole);
        } catch (Exception e) {
            logger.error("[Hydra.register] client cannot regist service <" + serviceName + "> into the hydra system,e="+e.getMessage());
        }
        if (serviceId != null) {
            logger.info("[Hydra.register] Registry ["+serviceName+"] option is ok! serviceId="+serviceId);
            registerInfo.put(serviceName, serviceId); //更新本地注册信息
            return true;
        } else
            return false;
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

    public String getServiceId(String service) {
        if (isRegister && registerInfo.containsKey(service))
            return registerInfo.get(service);
        else
            return null;
    }

    public Long getSeed() {
        String s = null;
        if (isRegister) {
            s = registerInfo.get(SEED);
            return Long.valueOf(s);
        }
        return null;
    }
}
