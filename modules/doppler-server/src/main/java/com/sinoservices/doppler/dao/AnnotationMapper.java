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

import com.sinoservices.doppler.entity.*;

import java.util.List;

/**
 * User: biandi
 * Date: 13-5-8
 * Time: 下午3:29
 */
public interface AnnotationMapper {

    List<AnnoSpanDB> getAnnoNumByServiceTimeK(long startTime, long endTime, String k,String serviceIds);

    List<AnnoAppDB> getReverseAnnoAppByTimeK(long startTime, long endTime, String k);

    List<AnnotationDB> getAnnotationsBySpanId(long spanId);

    List<AnnotationWebDB> getAnnotationWebsBySpanId(long spanId);

    List<AnnoAppDB> getAnnoAppByTimeK(long startTime, long endTime, String k);

    List<AnnoWebAppDB> getAnnoWebAppByTime(long startTime, long endTime);

    List<AnnoSpanDB> getAnnoSpan(long startTime, long endTime,String k,int appId,String serviceId,String method);

    List<AnnoSpanDB> getAnnoSpanWeb(long startTime, long endTime,String k,int appId,String serviceId);

    List<AnnoAppDB> getAnnoApp();

    List<AnnoAppDB> getAnnoAppWeb();
}
