package com.jd.bdp.hydra.agent.support;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Date: 13-3-27
 * Time: 下午6:02
 */
public class GenerateTraceId {
    private static Logger logger = LoggerFactory.getLogger(GenerateTraceId.class);
    private Long seed;
    private Long MAX_STEP = 0xffffffL;
    private AtomicLong plusId = new AtomicLong(0L);
    private AtomicLong index = new AtomicLong(0L);

    public GenerateTraceId(Long seed) {
        this.seed = seed;
    }

    public Long getTraceId() {
        return (seed << 40) | getPlusId();
    }


    private long getPlusId() {
        if (plusId.get() >= MAX_STEP) {
            plusId.set(0L);
        }
        return plusId.incrementAndGet();
    }

}
