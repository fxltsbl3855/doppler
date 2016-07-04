
package com.sinoservices.doppler.dao;


import com.sinoservices.doppler.entity.AppDB;
import com.sinoservices.doppler.entity.BusLogDB;

import java.util.List;

/**
 * Date: 13-4-2
 * Time: 下午3:28
 */
public interface BusLogMapper {

    /**
     *
     */
    void save(BusLogDB busLogDB);

    List<BusLogDB> get(BusLogDB busLogDB,long startTime,long endTime);

    BusLogDB getById(int id);
}
