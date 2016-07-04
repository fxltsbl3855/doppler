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

import com.sinoservices.doppler.dao.AnnotationMapper;
import com.sinoservices.doppler.entity.*;
import com.sinoservices.doppler.exception.RunTaskException;
import com.sinoservices.doppler.util.DopplerUtil;
import com.sinoservices.util.JsonUtils;
import com.sinoservices.util.NumberUtil;
import com.sinoservices.util.ST;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: biandi
 * Date: 13-5-8
 * Time: 下午3:29
 */
@Repository("annotationMapper")
public class AnnotationMapperImpl implements AnnotationMapper {
    private static Logger logger = LoggerFactory.getLogger(AnnotationMapperImpl.class);

    @Autowired
    private SqlSessionTemplate sqlSession;

    @Override
    public List<AnnotationDB> getAnnotationsBySpanId(long spanId) {
        ST.start();
        Map map = new HashMap();
        map.put("spanId",spanId);
        try {
            return (List<AnnotationDB>) sqlSession.selectList("getAnnotationsBySpanId",spanId);
        }catch (BadSqlGrammarException e){
            logger.info("AnnotationMapperImpl.getAnnotationsBySpanId BadSqlGrammarException,e="+e.getSQLException().getMessage());
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
    public List<AnnotationWebDB> getAnnotationWebsBySpanId(long spanId) {
        ST.start();
        Map map = new HashMap();
        map.put("spanId",spanId);
        try {
            return (List<AnnotationWebDB>) sqlSession.selectList("getAnnotationsWebBySpanId",map);
        }catch (BadSqlGrammarException e){
            logger.info("AnnotationMapperImpl.getAnnotationWebsBySpanId BadSqlGrammarException,e="+e.getSQLException().getMessage());
            if(DopplerUtil.matchTableNotExist(e.getSQLException().getMessage())){
                throw new RunTaskException(e);
            }else {
                throw e;
            }
        }finally{
            ST.stop();
        }
    }

    /**
     * 超时算在服务端，关联查询，
     *
     * @param startTime
     * @param endTime
     * @param k  timeout值
     * @return
     */
    public List<AnnoSpanDB> getAnnoNumByServiceTimeK(long startTime, long endTime, String k,String serviceIds) {
        ST.start();
        Map map = new HashMap();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("k",k);
        map.put("serviceIds",serviceIds);
        List<AnnoSpanDB> list = (List<AnnoSpanDB>) sqlSession.selectList("getAnnoNumByServiceTimeK",map);
        ST.stop();
        return list;
    }

    public List<AnnoAppDB> getAnnoAppByTimeK(long startTime, long endTime, String k) {
        ST.start();
        Map map = new HashMap();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("k",k);
        List<AnnoAppDB> list = (List<AnnoAppDB>) sqlSession.selectList("getAnnoAppByTimeK",map);
        ST.stop();
        return list;
    }

    /**
     * 超时算在服务端，关联查询，
     *
     * @param startTime
     * @param endTime
     * @param k  timeout值
     * @return
     */
    public List<AnnoAppDB> getReverseAnnoAppByTimeK(long startTime, long endTime, String k) {
        ST.start();
        Map map = new HashMap();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("k",k);
        List<AnnoServiceDB> list = (List<AnnoServiceDB>) sqlSession.selectList("getAnnoService",map);
        if(list == null || list.size() ==0){
            return new ArrayList<AnnoAppDB>(0);
        }

        Map<Integer,AnnoServiceDB> asMap = new HashMap<Integer, AnnoServiceDB>();
        StringBuilder sb = new StringBuilder();
        for(AnnoServiceDB annoServiceDB :list){
            sb.append(",");
            sb.append(annoServiceDB.getServiceId());
            asMap.put(annoServiceDB.getServiceId(),annoServiceDB);
        }
        sb = sb.deleteCharAt(0);

        Map mapParam = new HashMap();
        mapParam.put("ids",sb.toString());
        List<ServiceAppDB> saList = (List<ServiceAppDB>) sqlSession.selectList("getServiceAppByReverseId",mapParam);
        if(saList == null || saList.size() ==0){
            return new ArrayList<AnnoAppDB>(0);
        }

        Map<Integer,AnnoAppDB> resMap = new HashMap<Integer, AnnoAppDB>(saList.size());
        for(ServiceAppDB serviceAppDB :saList){
            AnnoAppDB aad = resMap.get(serviceAppDB.getAppId());
            int num = asMap.get(serviceAppDB.getCserviceId())==null?0:asMap.get(serviceAppDB.getCserviceId()).getNum();
            if(aad == null){
                aad = new AnnoAppDB();
                aad.setAppId(serviceAppDB.getAppId());
                aad.setAppName(serviceAppDB.getAppName());
                resMap.put(serviceAppDB.getAppId(),aad);
            }
            aad.setNum(num+aad.getNum());
        }
        ST.stop();
        return new ArrayList<AnnoAppDB>(resMap.values());
    }



    public List<AnnoWebAppDB> getAnnoWebAppByTime(long startTime, long endTime) {
        ST.start();
        Map map = new HashMap();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        List<AnnoWebAppDB> list = (List<AnnoWebAppDB>) sqlSession.selectList("getAnnoWebApp",map);
        ST.stop();
        return list;
    }

    public List<AnnoSpanDB> getAnnoSpan(long startTime, long endTime,String k,int appId,String serviceId,String method) {
        ST.start();
        Map map = new HashMap();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("k",k);
        map.put("appId",appId);
        map.put("serviceId",serviceId);
        map.put("method",method);
        List<AnnoSpanDB> list = (List<AnnoSpanDB>) sqlSession.selectList("getAnnoSpan",map);
        ST.stop();
        return list;
    }

    public List<AnnoSpanDB> getAnnoSpanWeb(long startTime, long endTime,String k,int appId,String serviceId) {
        ST.start();
        Map map = new HashMap();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("k",k);
        map.put("appId",appId);
        map.put("serviceId",serviceId);
        List<AnnoSpanDB> list = (List<AnnoSpanDB>) sqlSession.selectList("getAnnoSpanWeb",map);
        ST.stop();
        return list;
    }

    @Override
    public List<AnnoAppDB> getAnnoApp() {
        ST.start();
        List<AnnoAppDB> list = (List<AnnoAppDB>) sqlSession.selectList("getAnnoApp",null);
        ST.stop();
        return list;
    }

    @Override
    public List<AnnoAppDB> getAnnoAppWeb() {
        ST.start();
        List<AnnoAppDB> list = (List<AnnoAppDB>) sqlSession.selectList("getAnnoAppWeb",null);
        ST.stop();
        return list;
    }
}
