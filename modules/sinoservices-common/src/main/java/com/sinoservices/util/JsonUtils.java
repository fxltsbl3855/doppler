package com.sinoservices.util;

import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * Date: 13-4-17
 * Time: 上午10:01
 * To change this template use File | Settings | File Templates.
 */
public class JsonUtils {
	private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

    public static <T> T json2Object(String json, Class<T> objectClass) {
        Object o = null;
        Gson gson = new Gson();
        try {
            o = gson.fromJson(json, objectClass);
            return (T) o;
        } catch (Exception e) {
        	LOG.error("erorr json: "+json, e);
            return null;
        }

    }

    public static String object2Json(Object objectClass) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String jsonStr = gson.toJson(objectClass);
        return jsonStr;
    }

}
