package com.sinoservices.doppler.zk;


import java.util.concurrent.CountDownLatch;

import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.sinoservices.doppler.Constant;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * zk client
 * @author  charlie
 */
public class ZkClient extends Thread{
    /** 日志 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkClient.class);
    /** 运行状态 **/
    private static boolean IS_RUN = false;

    /** SLEEP TIME **/
    private final long seeepTime = 5*60 * 1000;
    /** host **/
    private String host = null;
    /** zk client对象 **/
    private ZooKeeper zk;
    /** zk连接信号 **/
    private CountDownLatch connectedSignal = new CountDownLatch(1);

    /**
     * 构造方法
     * 从系统获取host，初始化参数
     */
    public ZkClient(){
        this.setName("zk-client");
        //zookeeper://192.168.1.152:2181?backup=192.168.1.152:2182,192.168.1.152:2183
        String zkAddr = ConfigUtils.getProperty("dubbo.registry.address");
        if(zkAddr == null || "".equals(zkAddr.trim())){
            LOGGER.error("zk-client construct error,cann't get param(dubbo.registry.address)");
            return;
        }
        zkAddr = zkAddr.replaceAll("zookeeper://","");
        zkAddr = zkAddr.replaceAll("\\?backup=", ",");
        int ei = zkAddr.indexOf("&");
        if(ei>=0){
            zkAddr = zkAddr.substring(0,ei);
        }
        this.host = zkAddr;
    }

    /**
     * 带参构造方法
     * 指定host
     * @param host
     */
    public ZkClient(String host){
        this.setName("zk-client-test");
        this.host = host;
    }

    /**
     * 启动client
     */
    public void init(){
        this.start();
    }

    /**
     * 线程执行体
     */
    public void run() {
        if(host == null || "".equals(host.trim())){
            LOGGER.error("zk-client is error, and quit, host is empty");
            return;
        }
        while (true) {
            try {
                if(!IS_RUN){
                    connectZookeeper();
                }
                LOGGER.info("[zkregister] zk connectZookeeper running...");
            }catch (Exception e){
                e.printStackTrace();
                LOGGER.info("[zkregister] zk connectZookeeper exception,e="+e.getMessage());
            }finally {
                this.handle(seeepTime);
            }
        }
    }

    /**
     * 连接zk
     * @throws Exception 异常
     */
    public void connectZookeeper() throws Exception {
        zk = new ZooKeeper(host, 10000, new Watcher() {
            public void process(WatchedEvent event) {

                if (event.getType() == EventType.NodeChildrenChanged) {
                    LOGGER.info("[zkregister] zk NodeChildrenChanged ,path="+event.getPath());
                    boolean isWebPath = event.getPath().startsWith(Constant.ZK_PATH_DELIMITER+Constant.ROOT_WEB);
                    boolean isServerPath = event.getPath().startsWith(Constant.ZK_PATH_DELIMITER+Constant.ROOT_SERVER);

                    if(isWebPath){
                        WebProcess.updateRegInfo(zk,event.getPath());
                    }else if(isServerPath){
                        ServerProcess.updateRegInfo(zk,event.getPath());
                    }else{
                        LOGGER.error("[zkregister] unknow event,path = "+event.getPath());
                    }

                }else if (event.getState() == KeeperState.SyncConnected) {
                    connectedSignal.countDown();
                    IS_RUN = true;
                    LOGGER.info("[zkregister] create zk connected to server");

                    WebProcess.updateRegInfo(zk,Constant.ZK_PATH_DELIMITER + Constant.ROOT_WEB);
                    ServerProcess.updateRegInfo(zk,Constant.ZK_PATH_DELIMITER + Constant.ROOT_SERVER);
                    LOGGER.info("[zkregister] update register info,create root path over,path=/"+Constant.ROOT_WEB);

                }else if (event.getState() == KeeperState.Expired) {
                    IS_RUN = false;
                    LOGGER.info("[zkregister] zk connection expired.");
                }else if (event.getState() == KeeperState.Disconnected) {
                    IS_RUN = false;
                    LOGGER.info("[zkregister] zk connection Disconnected.");
                }
            }
        });
        connectedSignal.await();
        LOGGER.info("[zkregister] create zk connect action over");

        WebProcess.checkAndCreatePath(zk,Constant.ZK_PATH_DELIMITER + Constant.ROOT_WEB, CreateMode.PERSISTENT);
    }

    /**
     * 测试main方法
     * @param args 参数
     * @throws Exception 异常
     */
    public static void main(String[] args) throws Exception {
        ZkClient zz = new ZkClient("127.0.0.1:2181,127.0.0.1:2182");
        zz.start();
    }


    /**
     * sleep
     * @param time 休眠时长
     */
    private void handle(long time)  {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
            LOGGER.info("[zkregister] sleep error,e="+e.getMessage());
        }
    }

}