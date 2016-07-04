package com.sinoservices.util;


import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * User: charlie
 * Time: 18:34
 * Timers
 */
public class ST {
    /**
     * 实例化一个registry，最核心的一个模块，相当于一个应用程序的metrics系统的容器，维护一个Map
     */
    private static final MetricRegistry metrics = new MetricRegistry();
    private static Slf4jReporter reporter = Slf4jReporter.forRegistry(metrics).build();
    private static final ThreadLocal<Map<String,Context>> threadLocal = new ThreadLocal<Map<String,Context>>();
    static{
        reporter.start(1, TimeUnit.MINUTES);
//        reporter.start(3, TimeUnit.SECONDS);
    }


    /**
     * 用于代码片段,在目标的代码片段前加
     * 耗时计数开始，线程安全
     * @param codeNumber 代码片段唯一号；建议使用className.methodName.{片段号} 用于在metrics日志中定位日志
     */
    public static void start(String codeNumber){
        Timer timer = metrics.timer(codeNumber);
        Context context = timer.time();
        Map<String,Context> map = threadLocal.get();
        if(map == null){
            map = new HashMap<String, Context>(2);
        }
        map.put(codeNumber,context);
        threadLocal.set(map);
    }

    /**
     * 用于代码片段,在目标的代码片段后加,跟start()是一对，配对使用
     * 耗时计数结束，线程安全
     * @param codeNumber 代码片段唯一号；建议使用className.methodName.{片段号} 用于在metrics日志中定位日志
     */
    public static void stop(String codeNumber){
        Map<String,Context> map = threadLocal.get();
        if(map == null){
            return;
        }
        Context context = map.get(codeNumber);
        if(context != null) {
            context.stop();
        }
    }

    /**
     * 方法耗时计算，开始标志
     */
    public static void start(){
        String key = getStackFrame(-2);
        Timer timer = metrics.timer(key);
        Context context = timer.time();
        Map<String,Context> map = threadLocal.get();
        if(map == null){
            map = new HashMap<String, Context>(2);
        }
        map.put(key,context);
        threadLocal.set(map);
    }

    /**
     * 方法耗时计算，结束标示
     */
    public static void stop(){
        Map<String,Context> map = threadLocal.get();
        if(map == null){
            return;
        }
        Context context = map.get(getStackFrame(-2));
        if(context != null) {
            context.stop();
        }
    }

    /**
     * 获取方法栈父父标示
     * @param index 往上第几层，0：当前方法栈；-1：父方法栈；-2：上上层方法栈
     * @return
     */
    private static String getStackFrame(int index){
        index = Math.abs(index);
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        if(index >= stackElements.length){
            return "frame_outofrange";
        }
        StringBuilder sb = new StringBuilder();
        if (stackElements != null) {
            sb.append(stackElements[index].getClassName());
            sb.append(".");
            sb.append(stackElements[index].getMethodName());
        }
        return sb.toString();
    }

}
