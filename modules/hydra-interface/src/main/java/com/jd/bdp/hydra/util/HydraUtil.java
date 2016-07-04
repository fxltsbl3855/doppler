package com.jd.bdp.hydra.util;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class HydraUtil {
    private static final Logger logger = LoggerFactory.getLogger(HydraUtil.class);

    public static int EXCEPTION_LENGTH = 5000; //最大异常内容长度
    public static int PARAM_LENGTH = 500;//最大参数内容长度
    public static int COLUMN_LENGTH = 5900; // 最大总长度

    public static String getDisplayValue(Throwable aThrowable,String para) {
        StringBuilder sb = new StringBuilder();
        sb.append("@Param:");
        sb.append(para);
        sb.append(" ");
        sb.append("@Exception:");
        sb.append(getStackTrace(aThrowable));
        return sb.length()>COLUMN_LENGTH?sb.substring(0,COLUMN_LENGTH)+"...":sb.toString();
    }

    public static String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        String eStr = result.toString();
        logger.debug("getStackTrace="+eStr);
        return eStr.length()>EXCEPTION_LENGTH?eStr.substring(0,EXCEPTION_LENGTH)+"...":eStr;
    }
    public static String getParaStr(Map<String,String[]> paraMa) {
        if(paraMa == null || paraMa.size()==0){
            return "no_web_param";
        }
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,String[]> entry : paraMa.entrySet()){
            sb.append(entry.getKey());
            sb.append("=");
            for (String i : entry.getValue()) {
                sb.append(i+",");
            }
            sb.append("#");
        }
        logger.info("getParaStr map="+sb.toString());
        return sb.length()>PARAM_LENGTH?sb.substring(0,PARAM_LENGTH)+"...":sb.toString();
    }
    public static String getParaStr(Object[] objects) {
        if(objects == null || objects.length==0){
            return "no_param";
        }
        StringBuilder sb = new StringBuilder();
        for(Object obj : objects){
            sb.append(obj.getClass().getName());
            sb.append("=");
            try {
                sb.append(getValue(obj));
            } catch (IOException e) {
                sb.append(obj.toString());
            }
            sb.append("#");
        }
        logger.debug("getParaStr array="+sb.toString());
        return sb.length()>PARAM_LENGTH?sb.substring(0,PARAM_LENGTH)+"...":sb.toString();
    }

    public static String getValue(Object obj) throws IOException {
        if(isBasicType(obj.getClass().getName())){
            return obj.toString();
        }
        return new Gson().toJson(obj);
    }

    /**
     * 只认为官网的对象为基础对象，其他第三方都是复杂对象
     * @param className
     * @return
     */
    public static boolean isBasicType(String className) {
        return className.startsWith("java.")||className.startsWith("javax.");
    }


}
