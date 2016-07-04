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

package com.jd.bdp.hydra.store.inter;


import com.jd.bdp.hydra.Span;
import com.jd.bdp.hydra.SpanWeb;

import java.io.IOException;
import java.util.List;

/**
  * User: yfliuyu
 * Date: 13-4-16
 * Time: 上午11:04
  */
public interface InsertService {

    void txAnnoReqstat(List<Span> span);

    void txAnnoReqstatWeb(List<SpanWeb> spanWeb);

    void addAnnotationBatch(List<Span> spanList);

    void addSpanBatch(List<Span> spanList);

    void addAnnotationWebBatch(List<SpanWeb> spanWebList);

    void addSpan(Span span);

    void addAnnotation(Span span);

    void addTrace(Span span);

    void addAnnotationWeb(SpanWeb spanWeb);

    void addReqStat(List<Span> span);

    void addReqStatWeb(List<SpanWeb> span);
}
