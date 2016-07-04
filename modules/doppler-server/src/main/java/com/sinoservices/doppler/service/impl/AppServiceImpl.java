package com.sinoservices.doppler.service.impl;

import com.alibaba.dubbo.rpc.RpcContext;
import com.jd.bdp.hydra.HydraConstants;
import com.sinoservices.doppler.assimble.ServiceAssimble;
import com.sinoservices.doppler.bo.*;
import com.sinoservices.doppler.dao.AnnotationMapper;
import com.sinoservices.doppler.dao.AppMapper;
import com.sinoservices.doppler.entity.AppDB;
import com.sinoservices.doppler.service.AppService;
import com.sinoservices.doppler.service.ServiceService;
import com.sinoservices.doppler.zk.RegisterInfo;
import com.sinoservices.doppler.bo.ServerRegDB;
import com.sinoservices.util.JsonUtils;
import com.sinoservices.util.ST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2016/1/21.
 */
@Service("appService")
public class AppServiceImpl implements AppService {
    private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

    @Autowired
    private AppMapper appMapper;
    @Autowired
    private AnnotationMapper annotationMapper;
    @Autowired
    private ServiceService serviceService;

    @Override
    public List<AppStatBo> getAppDetailList(String date) {
        return null;
    }

    @Override
    public List<AppDB> getAppAll() {
        return appMapper.getAppAll();
    }

    @Deprecated
    @Override
    public DashboardBo getDashboard() {
        List<AppDB> appDBWebList = new ArrayList<AppDB>();
        List<AppDB> appDBServerList = new ArrayList<AppDB>();
        ServiceAssimble.filterByName(getAppAll(),appDBWebList,appDBServerList);

        Map<Integer,AppStatBo> serverMap = ServiceAssimble.annoAppToMap(annotationMapper.getAnnoApp());
        Map<Integer,AppStatBo> webMap = ServiceAssimble.annoAppToMap(annotationMapper.getAnnoAppWeb());

        DashboardBo dashboardBo = new DashboardBo();
        dashboardBo.setWebNum(appDBWebList.size());
        dashboardBo.setServerNum(appDBServerList.size());

        List<AppReq> serverReq = new ArrayList<AppReq>();
        List<AppReq> webReq = new ArrayList<AppReq>();
        int serverReqTotal = 0;
        int webReqTotal = 0;
        for(AppDB server : appDBServerList){
            AppStatBo asbServer = serverMap.get(server.getId());
            int numServer = asbServer==null?0:asbServer.getReqNum();
            serverReqTotal += numServer;
            serverReq.add(new AppReq(server.getName(),numServer));
        }
        for(AppDB web : appDBWebList){
            AppStatBo asbWeb = webMap.get(web.getId());
            int numWeb = asbWeb==null?0:asbWeb.getReqNum();
            webReqTotal +=  numWeb;
            webReq.add(new AppReq(web.getName(),numWeb));
        }
        dashboardBo.setServerReqNum(serverReqTotal);
        dashboardBo.setWebReqNum(webReqTotal);
        dashboardBo.setServerReqList(serverReq);
        dashboardBo.setWebReqList(webReq);
        return dashboardBo;
    }

    @Override
    public List<AppInfoBo> getAppInfoBo() {
        ST.start();
        long start1 = System.currentTimeMillis();
        List<AppInfoBo> resList = new ArrayList<AppInfoBo>();
        for(Map.Entry<String,Map<String, String>> entry : RegisterInfo.webMap.entrySet()){
            if(entry.getValue().size()==0){
                continue;
            }
            resList.add(new AppInfoBo(entry.getKey()));
        }
        long start2 = System.currentTimeMillis();
        Map<String,AppInfoBo> tempMap = new HashMap<String, AppInfoBo>();
        for(Map.Entry<String,ServerRegDB> entry : RegisterInfo.serverMap.entrySet()){
            ServerRegDB serverRegDB = entry.getValue();
            if(!tempMap.containsKey(serverRegDB.getApp()) && serverRegDB.getHostList().size()>0){
                tempMap.put(serverRegDB.getApp(),new AppInfoBo(serverRegDB.getApp()));
            }
        }
        resList.addAll(new ArrayList(tempMap.values()));

        Collections.sort(resList, new Comparator<AppInfoBo>() {
            public int compare(AppInfoBo arg0, AppInfoBo arg1) {
                return arg0.getAppName().compareTo(arg1.getAppName());
            }
        });
        ST.stop();
        long start3 = System.currentTimeMillis();
        System.out.println((start2-start1)+"_______________getAppInfoBo___________"+(start3-start2));
        return resList;
    }

    @Override
    public List<ServiceInfoBo> getServiceInfo() {
        long start1 = System.currentTimeMillis();
        ST.start();
        List<ServiceInfoBo> resList = new ArrayList<ServiceInfoBo>();
        for(Map.Entry<String,Map<String, String>> entry : RegisterInfo.webMap.entrySet()){
            if(entry.getValue().size()==0){
                continue;
            }
            resList.add(new ServiceInfoBo(entry.getKey(),"*"));
        }
        long start2 = System.currentTimeMillis();
        for(Map.Entry<String,ServerRegDB> entry : RegisterInfo.serverMap.entrySet()){
            ServerRegDB serverRegDB = entry.getValue();
            if(serverRegDB.getHostList().size()==0){
               continue;
            }
            resList.add(new ServiceInfoBo(serverRegDB.getApp(),entry.getKey()));
        }

        Collections.sort(resList, new Comparator<ServiceInfoBo>() {
            public int compare(ServiceInfoBo arg0, ServiceInfoBo arg1) {
                int appIndex = arg0.getAppName().compareTo(arg1.getAppName());
                if(appIndex!=0){
                    return appIndex;
                }
                return arg0.getServiceName().compareTo(arg1.getServiceName());
            }
        });
        long start3 = System.currentTimeMillis();
        ST.stop();
        System.out.println((start2-start1)+"___________getServiceInfo_______________"+(start3-start2));
        return resList;
    }

    @Override
    public List<HostInfoBo> getHostInfo() {
        long start1 = System.currentTimeMillis();
        ST.start();
        Map<String,HostInfoBo> resMap = new HashMap<String, HostInfoBo>();
        for(Map.Entry<String,Map<String, String>> entry : RegisterInfo.webMap.entrySet()){
            if(entry.getValue().size()==0){
                continue;
            }
            Map<String, String> hostsMap = entry.getValue();
            for(Map.Entry<String,String> en : hostsMap.entrySet()){
                HostInfoBo hostInfoBo = resMap.get(en.getKey());
                if(hostInfoBo == null){
                    hostInfoBo = new HostInfoBo(entry.getKey(),en.getKey());
                    resMap.put(en.getKey(),hostInfoBo);
                }else {
                    if(!hostInfoBo.getAppNameList().contains(entry.getKey())){
                        hostInfoBo.getAppNameList().add(entry.getKey());
                    }
                }
            }
        }
        long start2 = System.currentTimeMillis();
        for(Map.Entry<String,ServerRegDB> entry : RegisterInfo.serverMap.entrySet()){
            ServerRegDB serverRegDB = entry.getValue();
            if(serverRegDB.getHostList().size()==0){
                continue;
            }
            List<String> hostList = serverRegDB.getHostList();
            for(String h : hostList){
                HostInfoBo hostInfoBo = resMap.get(h);
                if(hostInfoBo == null){
                    hostInfoBo = new HostInfoBo(serverRegDB.getApp(),h);
                    resMap.put(h,hostInfoBo);
                }else {
                    if(!hostInfoBo.getAppNameList().contains(serverRegDB.getApp())){
                        hostInfoBo.getAppNameList().add(serverRegDB.getApp());
                    }
                }
            }
        }
        List resList = new ArrayList<HostInfoBo>(resMap.values());
        Collections.sort(resList, new Comparator<HostInfoBo>() {
            public int compare(HostInfoBo arg0, HostInfoBo arg1) {
                int appIndex = arg0.getHostName().compareTo(arg1.getHostName());
                return appIndex;
            }
        });
        ST.stop();
        long start3 = System.currentTimeMillis();
        System.out.println((start2-start1)+"_____________getHostInfo_____________"+(start3-start2));
        return resList;
    }

}
