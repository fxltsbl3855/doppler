/*
 * Copyright jd
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.sinoservices.doppler.dao.impl;

import com.sinoservices.doppler.dao.ServiceMapper;
import com.sinoservices.doppler.entity.AppDB;
import com.sinoservices.doppler.entity.ServiceDB;
import com.sinoservices.util.ST;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: xiangkui
 * Date: 13-4-1
 * Time: 下午1:15
 */
@Repository("serviceMapper")
public class ServiceMapperImpl implements ServiceMapper {
    @Autowired
    private SqlSessionTemplate sqlSession;




    public ServiceDB getServiceByName(String name, int invokeRole) {
        ST.start();
        Map map = new HashMap();
        map.put("serviceName",name);
        map.put("invokeRole",invokeRole);
        ServiceDB serviceDB = (ServiceDB) sqlSession.selectOne("getByServiceName",map);
        ST.stop();
        return serviceDB;
    }

    /**
     * 得到上游service
     * @param serviceId
     * @return
     */
    public List<ServiceDB> getServicesByCascade(int serviceId) {
        ST.start();
        Map map = new HashMap();
        map.put("serviceId",serviceId);
        List<ServiceDB> list = (List<ServiceDB>) sqlSession.selectList("getServicesByCascade",map);
        ST.stop();
        return list;
    }

    public List<ServiceDB> getServiceByAppId(int appId, int invokeRole) {
        ST.start();
        Map map = new HashMap();
        map.put("appId",appId);
        map.put("invokeRole",invokeRole);
        List<ServiceDB> list = (List<ServiceDB>) sqlSession.selectList("getServiceByAppId",map);
        ST.stop();
        return list;
    }

    public AppDB getAppByServiceId(String serviceId) {
        ST.start();
        Map map = new HashMap();
        map.put("serviceId",serviceId);
        AppDB appDB = (AppDB) sqlSession.selectOne("getAppByServiceId",map);
        ST.stop();
        return appDB;
    }

    public ServiceDB getByServiceId(int serviceId){
        ST.start();
        Map map = new HashMap();
        map.put("serviceId",serviceId);
        ServiceDB serviceDB = (ServiceDB) sqlSession.selectOne("getByServiceId",map);
        ST.stop();
        return serviceDB;
    }

    public List<ServiceDB> getAppServiceByServiceId(String serviceIds){
        if(serviceIds == null || "".equals(serviceIds.trim())){
            return null;
        }
        ST.start();
        Map map = new HashMap();
        map.put("serviceIds",serviceIds);
        List<ServiceDB> serviceDB = (List<ServiceDB>) sqlSession.selectList("getAppServiceByServiceIds",map);
        ST.stop();
        return serviceDB;
    }
}
