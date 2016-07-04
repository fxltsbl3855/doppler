
package com.sinoservices.doppler.dao;


import com.sinoservices.doppler.entity.AppDB;

import java.util.List;

/**
 * Date: 13-4-2
 * Time: 下午3:28
 */
public interface AppMapper {

    /**
     *
     */
    AppDB getAppById(Integer id);

    /**
     * 获得所有
     * @return
     */
    List<AppDB> getAppAll();
}
