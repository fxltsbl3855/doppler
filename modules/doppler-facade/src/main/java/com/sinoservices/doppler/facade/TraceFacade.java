package com.sinoservices.doppler.facade;

import com.sinoservices.doppler.bo.BusLogBo;
import com.sinoservices.doppler.bo.ServiceBo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public interface TraceFacade {

    /**
     * info级别打点日志
     * @param busLogBo
     */
    void info(BusLogBo busLogBo);

    /**
     * warn级别打点日志
     * @param busLogBo
     */
    void warn(BusLogBo busLogBo);

    /**
     * error级别打点日志
     * @param busLogBo
     */
    void error(BusLogBo busLogBo);

    /**
     * fetal级别打点日志
     * @param busLogBo
     */
    void fetal(BusLogBo busLogBo);

}
