package com.sinoservices.doppler.zk;

import com.sinoservices.doppler.Constant;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/17.
 */
public class WebProcess {
    private static final Logger logger = LoggerFactory.getLogger(WebProcess.class);

    public static void updateRegInfo(ZooKeeper zk, String path){
        String[] classStrs = checkPath(path);
        if (classStrs == null) {
            logger.info("path invalid,path=" + path);
            return;
        }
        boolean hostChanged = classStrs.length == 3;

        try {
            if (hostChanged) {
                logger.info("host changed");
                List<String> hostList = zk.getChildren(path, true);
                if (hostList == null || hostList.size() == 0) {
                    RegisterInfo.webMap.remove(classStrs[2]);
                    logger.info("remove app,app=" + classStrs[2]);
                    return;
                }
                Map<String, String> hostMap = RegisterInfo.webMap.get(classStrs[2]);
                if (hostMap == null) {
                    hostMap = new HashMap<String, String>();
                } else {
                    hostMap.clear();
                }
                for (String hostNode : hostList) {
                    hostMap.put(hostNode, "");
                    logger.info("hostNode: " + hostNode);
                }
                RegisterInfo.webMap.put(classStrs[2], hostMap);
            }else{
                logger.info("app changed,path=" + path);
                Map<String, Map<String, String>> tmpMap = new HashMap<String, Map<String, String>>();
                List<String> appList = zk.getChildren(path, true);
                for (String appNode : appList) {
                    logger.info("appNode: " + appNode);
                    List<String> hostList = zk.getChildren(path + Constant.ZK_PATH_DELIMITER + appNode, true);
                    if (hostList == null || hostList.size() == 0) {
                        logger.info("empty app=" + path + Constant.ZK_PATH_DELIMITER + appNode);
                        continue;
                    }

                    Map<String, String> hostMap = new HashMap<String, String>();
                    tmpMap.put(appNode, hostMap);
                    for (String hostNode : hostList) {
                        hostMap.put(hostNode, "");
                    }
                }
                RegisterInfo.webMap = tmpMap;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("update register info exception,path="+path+",e="+e.getMessage());
        }

    }

    private static String[] checkPath(String path) {
        String[] classStrs = path.split(Constant.ZK_PATH_DELIMITER);
        if (classStrs.length == 0 || classStrs.length > 3) {
            logger.error("checkPath error,path array shoule not be (0,3) path="+path);
            return null;
        }
        if (!Constant.ROOT_WEB.equalsIgnoreCase(classStrs[1])) {
            logger.error("checkPath error, path array shoule be start 'webapp'");
            return null;
        }
        return classStrs;
    }

    public static boolean checkAndCreatePath(ZooKeeper zk,String path,CreateMode createMode)  {
        Stat stat = null;
        try {
            stat = zk.exists(path, null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("judge path exists exception,path="+path+",e="+e.getMessage());
        }
        if (stat == null) {
            try{
                String resB = zk.create(path, "".getBytes("utf-8"), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        createMode);
                logger.info("create path over,path="+path+",resB="+resB);
                return true;
            }catch(KeeperException.NodeExistsException e){
                logger.error("create path KeeperException.NodeExistsException,path="+path);
            }catch(Exception e){
                e.printStackTrace();
                logger.error("create path exception,path="+path+",e="+e.getMessage());
            }
        }
        return false;
    }

}
