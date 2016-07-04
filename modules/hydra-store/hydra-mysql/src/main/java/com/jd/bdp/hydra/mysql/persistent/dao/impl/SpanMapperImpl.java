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

package com.jd.bdp.hydra.mysql.persistent.dao.impl;

import com.jd.bdp.hydra.Span;
import com.jd.bdp.hydra.mysql.persistent.dao.SpanMapper;
import com.jd.bdp.hydra.mysql.persistent.entity.ReqStat;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: biandi
 * Date: 13-5-8
 * Time: 下午4:54
 */
public class SpanMapperImpl implements SpanMapper{
    private static final Logger logger = LoggerFactory.getLogger(SpanMapperImpl.class);

    private SqlSessionTemplate sqlSession;

    @Override
    public void addSpanBatch(List<Span> spanList) {
        sqlSession.insert("addSpanBatch",spanList);
    }

    @Override
    public void addSpan(Span span) {
        sqlSession.insert("addSpan",span);
    }

    @Override
    public List<Span> findSpanByTraceId(Long traceId) {
        return (List<Span>)sqlSession.selectList("findSpanByTraceId", traceId);
    }

    @Override
    public void deleteAllSpans() {
        sqlSession.delete("deleteAllSpan");
    }

    @Override
    public void addReqStat(ReqStat reqStat) {
        sqlSession.insert("addReqStat",reqStat);
        sqlSession.insert("addReqStatService",reqStat);
    }

    @Override
    public void addReqStatBatch(List<ReqStat> reqStatList) {
        sqlSession.insert("addReqStatBatch",reqStatList);
    }

    @Override
    public void addReqStatServiceBatch(List<ReqStat> reqStatList) {
        sqlSession.insert("addReqStatServiceBatch",reqStatList);
    }

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }
}
