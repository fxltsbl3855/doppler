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

package com.sinoservices.doppler.dao;

import com.sinoservices.doppler.entity.AppDB;
import com.sinoservices.doppler.entity.ServiceDB;

import java.util.List;

/**
 * User: xiangkui
 * Date: 13-4-2
 * Time: 下午3:28
 */
public interface ServiceMapper {


    List<ServiceDB> getServicesByCascade(int serviceId);
    //根据name查找ServicePara
    ServiceDB getServiceByName(String name, int invokeRole);

    //根据appId查找
    List<ServiceDB> getServiceByAppId(int appId, int invokeRole);

    AppDB getAppByServiceId(String serviceId);

    ServiceDB getByServiceId(int serviceId);

    List<ServiceDB> getAppServiceByServiceId(String serviceIds);
}
