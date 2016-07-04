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
import com.sinoservices.doppler.dao.ReqStatMapper;
import com.sinoservices.doppler.entity.BusLogDB;
import com.sinoservices.doppler.entity.ReqStatDB;
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
@Repository("reqStatMapper")
public class ReqStatMapperImpl implements ReqStatMapper {
    private static final Logger logger = LoggerFactory.getLogger(ReqStatMapperImpl.class);

    @Autowired
    private SqlSessionTemplate sqlSession;

    @Override
    public List<ReqStatDB> getMethodStat(long startDate,long endDate,int serviceId) {
        ST.start();
        Map map = new HashMap();
        map.put("serviceId",serviceId);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        try{
            return (List<ReqStatDB>) sqlSession.selectList("getMethodStat", map);
        }catch (BadSqlGrammarException e){
            logger.info("ReqStatMapperImpl.getMethodStat BadSqlGrammarException,e="+e.getSQLException().getMessage());
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
    public List<ReqStatDB> getWebAppReqStatService() {
        ST.start();
        Map map = new HashMap();
        try{
            return (List<ReqStatDB>)sqlSession.selectList("getWebAppReqStatService",map);
        }catch (BadSqlGrammarException e){
            logger.info("ReqStatMapperImpl.getWebAppReqStatService BadSqlGrammarException,e="+e.getSQLException().getMessage());
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
    public List<ReqStatDB> getServerAppReqStatService() {
        ST.start();
        Map map = new HashMap();
        try{
            return (List<ReqStatDB>)sqlSession.selectList("getServerAppReqStatService",map);
        }catch (BadSqlGrammarException e){
            logger.info("ReqStatMapperImpl.getServerAppReqStatService BadSqlGrammarException,e="+e.getSQLException().getMessage());
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
    public List<ReqStatDB> getWebAppReqStat(long startDate,long endDate) {
        ST.start();
        Map map = new HashMap();
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        try{
            return (List<ReqStatDB>)sqlSession.selectList("getWebAppReqStat",map);
        }catch (BadSqlGrammarException e){
            logger.info("ReqStatMapperImpl.getWebAppReqStat BadSqlGrammarException,e="+e.getSQLException().getMessage());
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
    public List<ReqStatDB> getServerAppReqStat(long startDate,long endDate) {
        ST.start();
        Map map = new HashMap();
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        try{
            return (List<ReqStatDB>)sqlSession.selectList("getServerAppReqStat",map);
        }catch (BadSqlGrammarException e){
            logger.info("ReqStatMapperImpl.getServerAppReqStat BadSqlGrammarException,e="+e.getSQLException().getMessage());
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
    public List<ReqStatDB> getReqNumByHour(long startDate, long endDate, int serviceId, String method) {
        ST.start();
        Map map = new HashMap();
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("serviceId",serviceId);
        map.put("method",method);
        try{
            return (List<ReqStatDB>)sqlSession.selectList("getReqNumByHour",map);
        }catch (BadSqlGrammarException e){
            logger.info("ReqStatMapperImpl.getReqNumByHour BadSqlGrammarException,e="+e.getSQLException().getMessage());
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
