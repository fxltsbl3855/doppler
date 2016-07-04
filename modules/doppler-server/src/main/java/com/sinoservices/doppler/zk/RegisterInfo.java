package com.sinoservices.doppler.zk;

import com.sinoservices.doppler.bo.ServerRegDB;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/16.
 */
public class RegisterInfo {

    /**
     * web应用注册信息，zk监听器会实时更新注册信息，
     * 线程不安全的，同时只允许一个线程更新
     * map内容：<app,<host,空>> ，第二级map的value为预留字段，以后可以扩展信息
     */
    public static volatile Map<String, Map<String, String>> webMap = new HashMap<String, Map<String, String>>();

    /**
     * server应用注册信息，zk监听器会实时更新注册信息，
     * 线程不安全，同时只允许一个线程更新
     * map内容：<serviceName,ServerRegDB>
     */
    public static volatile Map<String,ServerRegDB> serverMap = new HashMap<String,ServerRegDB>();


}
