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


import com.sinoservices.doppler.dao.BusLogMapper;
import com.sinoservices.doppler.entity.BusLogDB;
import com.sinoservices.doppler.exception.RunTaskException;
import com.sinoservices.doppler.util.DopplerUtil;
import com.sinoservices.util.ST;
import com.sinoservices.util.StringUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: charlie
 * Date: 13-4-1
 * Time: 下午1:15
 */
@Repository("busLogMapper")
public class BusLogMapperImpl implements BusLogMapper {
    private static final Logger logger = LoggerFactory.getLogger(BusLogMapperImpl.class);

    @Autowired
    private SqlSessionTemplate sqlSession;


    @Override
    public void save(BusLogDB busLogDB) {
        sqlSession.insert("insertBusLogDB",busLogDB);
    }

    @Override
    public List<BusLogDB> get(BusLogDB busLogDB,long startTime,long endTime) {
        ST.start();
        String addr = StringUtil.isBlank(busLogDB.getAddr())?"":busLogDB.getAddr();
        String logInfo = StringUtil.isBlank(busLogDB.getLogInfo())?"":busLogDB.getLogInfo();
        if(!"".equals(addr)){
            if(!"".equals(logInfo)){
                logInfo += ("."+addr);
            }else{
                logInfo = addr;
            }
        }
        busLogDB.setLogInfo(logInfo);


        Map map = new HashMap();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("appId",busLogDB.getAppId());
        map.put("serviceId",busLogDB.getServiceId());
        map.put("errorType",busLogDB.getErrorType());
        map.put("logType",busLogDB.getLogType());
        map.put("logInfo",busLogDB.getLogInfo());
        try {
            return (List<BusLogDB>) sqlSession.selectList("getBusLogDBListByParam", map);
        }catch (BadSqlGrammarException e){
            logger.info("BusLogMapperImpl.get BadSqlGrammarException,e="+e.getSQLException().getMessage());
            if(DopplerUtil.matchTableNotExist(e.getSQLException().getMessage())){
                throw new RunTaskException(e);
            }else {
                throw e;
            }
        }finally{
            ST.stop();
        }
    }

    @Override
    public BusLogDB getById(int id) {
        Map map = new HashMap();
        map.put("id",id);
        ST.start();

        try {
            return (BusLogDB) sqlSession.selectOne("getBusLogById", map);
        }catch (BadSqlGrammarException e){
            logger.info("BusLogMapperImpl.getById BadSqlGrammarException,e="+e.getSQLException().getMessage());
            if(DopplerUtil.matchTableNotExist(e.getSQLException().getMessage())){
                throw new RunTaskException(e);
            }else {
                throw e;
            }
        }finally{
            ST.stop();
        }
    }
}
