
package com.sinoservices.doppler.dao;


import com.sinoservices.doppler.entity.BusLogDB;
import com.sinoservices.doppler.entity.ReqStatDB;

import java.util.List;

/**
 * Date: 13-4-2
 * Time: 下午3:28
 */
public interface ReqStatMapper {

    List<ReqStatDB> getMethodStat(long startDate,long endDate,int serviceId);

    List<ReqStatDB> getWebAppReqStatService();

    List<ReqStatDB> getServerAppReqStatService();

    List<ReqStatDB> getWebAppReqStat(long startDate,long endDate);

    List<ReqStatDB> getServerAppReqStat(long startDate,long endDate);


    List<ReqStatDB> getReqNumByHour(long startDate, long endDate, int serviceIdInt, String method);
}
