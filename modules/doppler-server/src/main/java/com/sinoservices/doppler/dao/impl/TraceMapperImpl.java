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

import com.sinoservices.doppler.dao.TraceMapper;
import com.sinoservices.doppler.entity.TraceDB;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: biandi
 * Date: 13-5-8
 * Time: 下午3:29
 */
@Repository("traceMapper")
public class TraceMapperImpl implements TraceMapper {
    @Autowired
    private SqlSessionTemplate sqlSession;


    public List<TraceDB> getTracesById(String traceId) {
        return null;
    }
}
