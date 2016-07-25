package com.sinoservices.doppler.zk;

import com.alibaba.dubbo.common.URL;
import com.sinoservices.doppler.Constant;
import com.sinoservices.doppler.bo.ServerRegDB;
import com.sinoservices.util.JsonUtils;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by Administrator on 2016/2/17.
 */
public class ServerProcess {
    private static final Logger logger = LoggerFactory.getLogger(ServerProcess.class);

    public static void updateRegInfo(ZooKeeper zk, String path) {
        String[] classStrs = checkPath(path);
        if (classStrs == null) {
            logger.info("path invalid,path=" + path);
            return;
        }

        boolean hostChanged = classStrs.length == 4;
        boolean serviceChanged = classStrs.length == 2;
        try {
            if(hostChanged){
                String serviceName = classStrs[2];
                List<String> hostList = zk.getChildren(path, true);
                if (hostList == null || hostList.size() == 0) {
                    RegisterInfo.serverMap.remove(serviceName);
                    return;
                }
                ServerRegDB serverRegDB = RegisterInfo.serverMap.get(serviceName);
                if (serverRegDB == null) {
                    serverRegDB = new ServerRegDB();
                    RegisterInfo.serverMap.put(serviceName,serverRegDB);
                } else {
                    serverRegDB.init();
                }
                for (String url : hostList) {
                    dubboURLToDB(serverRegDB,url);
                }
            }
            if(serviceChanged){
                List<String> serviceList = zk.getChildren(path, true);

                for (String serviceNode : serviceList) {
                    if(!RegisterInfo.serverMap.containsKey(serviceNode)){
                        Stat stat = zk.exists(path+Constant.ZK_PATH_DELIMITER + serviceNode +
                               Constant.ZK_PATH_DELIMITER + Constant.PROVIDERS, true);
                        if(stat == null){
                            logger.warn("provider dir is not generated,ingored,serviceNode="+serviceNode);
                            continue;
                        }
                        List<String> hostList = zk.getChildren(path+Constant.ZK_PATH_DELIMITER + serviceNode +
                                Constant.ZK_PATH_DELIMITER + Constant.PROVIDERS, true);
                        if(hostList == null || hostList.size()==0){
                            continue;
                        }
                        ServerRegDB sRegDB = new ServerRegDB();
                        RegisterInfo.serverMap.put(serviceNode, sRegDB);

                        for(String url : hostList) {
                            dubboURLToDB(sRegDB,url);
                        }
                    }else{
                        logger.error("should not here");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("update register info exception,path="+path+",e="+e.getMessage());
        }

    }

    /**
     * appName和host，port赋值给serverRegDB
     * @param serverRegDB
     * @param hostNode
     */
    private static void dubboURLToDB(ServerRegDB serverRegDB, String hostNode) {
        if(serverRegDB == null){
            return;
        }
        String str = null;
        try {
            str = URLDecoder.decode(hostNode,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("dubbo url decode error,e="+e.getMessage()+",dubbo url="+hostNode);
            return;
        }

        URL url = URL.valueOf(str);
        String cA = url.getParameters().get("application");
        if(!"".equals(serverRegDB.getApp()) && !serverRegDB.getApp().equals(cA)){
            cA = serverRegDB.getApp() +","+ cA;
        }
        serverRegDB.setApp(cA);
        serverRegDB.addHost(url.getHost()+":"+url.getPort());
    }


    private static String[] checkPath(String path) {
        String[] classStrs = path.split(Constant.ZK_PATH_DELIMITER);
        if (classStrs.length == 0) {
            logger.error("checkPath error,path array shoule not be 0, path="+path);
            return null;
        }
        if (!Constant.ROOT_SERVER.equalsIgnoreCase(classStrs[1])) {
            logger.error("checkPath error, path array shoule be start 'dubbo'");
            return null;
        }
        return classStrs;
    }


}
