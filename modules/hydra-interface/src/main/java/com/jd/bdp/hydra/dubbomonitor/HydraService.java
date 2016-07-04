package com.jd.bdp.hydra.dubbomonitor;


import com.jd.bdp.hydra.AnnotationWeb;
import com.jd.bdp.hydra.Span;
import com.jd.bdp.hydra.SpanWeb;

import java.io.IOException;
import java.util.List;

public interface HydraService {
    boolean push(List<Span> span) throws IOException;
    boolean pushWeb(List<SpanWeb> spanWeb) throws IOException;
}