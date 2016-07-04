package com.sinoservices.trace.register;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class Register extends Thread{
	private static final Logger logger = LoggerFactory.getLogger(Register.class);

    private static final String ROOT = "webapp";
    private static final String ZK_PATH_DELIMITER = "/";
    private static final long SEEEP_TIME = 1*60*1000;

    private int port = 0;
	private String host = "127.0.0.1:2181";
    private String appName = "";

    public static boolean started = false;
    private static boolean isRun = false;
	private ZooKeeper zk;
	private volatile Map<String, Map<String, String>> resMap = new HashMap<String, Map<String, String>>();
	private CountDownLatch connectedSignal = new CountDownLatch(1);

	public Register(String appName,int port){
        this.setName("ruite-zk-register-thread");
        this.port = port;
        String zkAddr = ConfigUtils.getProperty("dubbo.registry.address");
        if(zkAddr == null || "".equals(zkAddr.trim())){
            logger.error("Register construct error,cann't get param(dubbo.registry.address)");
            return;
        }
        zkAddr = zkAddr.replaceAll("zookeeper://","");
        zkAddr = zkAddr.replaceAll("\\?backup=", ",");
        int ei = zkAddr.indexOf("&");
        if(ei>=0){
            zkAddr = zkAddr.substring(0,ei);
        }
		this.host = zkAddr;
        this.appName = appName;
        logger.info("[zkregister] Register construct zkAddr="+zkAddr+",appName="+appName+",port="+port);
	}
	
	public void connectZookeeper() throws Exception {
		zk = new ZooKeeper(host, 10000, new Watcher() {
			public void process(WatchedEvent event) {
				if (event.getState() == KeeperState.SyncConnected) {
					connectedSignal.countDown();
                    started = true;
                    logger.info("[zkregister] create zk connected to server");
                    try {
                        checkAndCreatePath(ZK_PATH_DELIMITER + ROOT, CreateMode.PERSISTENT);
                        logger.info("[zkregister] create root path ok,path=/"+ROOT);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.info("[zkregister] create root path exception,path=/"+ROOT+",e="+e.getMessage());
                    }

				}else if (event.getState() == KeeperState.Expired) {
                    isRun = false;
                    logger.info("[zkregister] zk connection expired.");
                }else if (event.getState() == KeeperState.Disconnected) {
                    isRun = false;
                    logger.info("[zkregister] zk connection Disconnected.");
                }
			}
		});
		connectedSignal.await();
        logger.info("[zkregister] create zk connetion over");
	}

    public void run() {
        while(true) {
            try {
                if (!isRun) {
                    this.connectZookeeper();
                    boolean exist = false;
                    String ip = this.getLocalIP();
                    if (appName != null && !"".equals(appName.trim())) {
                        this.checkAndCreatePath(ZK_PATH_DELIMITER + ROOT + ZK_PATH_DELIMITER + appName, CreateMode.PERSISTENT);
                        if (ip != null && !"".equals(ip.trim())) {
                            this.checkAndCreatePath(ZK_PATH_DELIMITER + ROOT + ZK_PATH_DELIMITER + appName + ZK_PATH_DELIMITER + ip+":"+port, CreateMode.EPHEMERAL);
                            exist = true;
                        } else {
                            logger.error("[zkregister] register error, ip=" + ip + ",port="+port);
                        }
                    }
                    if (exist) {
                        isRun = true;
                        logger.info("[zkregister] register success,thread start run...");
                    } else {
                        logger.error("[zkregister] register appName or ip may be error,appName=" + appName+",ip="+ip);
                    }
                } else {
                    logger.info("[zkregister] register thread running....");
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("[zkregister] register thread exception.e="+e.getMessage());
            }finally {
                this.handle(SEEEP_TIME);
            }
        }
    }

	public static void main(String[] args) throws Exception {
//		Register ac = new Register("testApp",8888);
//        System.out.println(ac.getLocalIP());
	}

	private boolean checkAndCreatePath(String path,CreateMode createMode) throws KeeperException,
			InterruptedException, UnsupportedEncodingException {
		Stat stat = zk.exists(path, null);
		if (stat == null) {
            try{
                String resB = zk.create(path, "".getBytes("utf-8"), Ids.OPEN_ACL_UNSAFE,
                        createMode);
                logger.info("[zkregister] create path over,path="+path+",resB="+resB);
                return true;
            }catch(KeeperException.NodeExistsException e){
                logger.info("[zkregister] create path KeeperException.NodeExistsException,path="+path);
            }
		}
        return false;
	}

    private String getLocalIP(){
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
            String ip=addr.getHostAddress().toString();
            return ip;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            logger.info("[zkregister] get local ip error,e="+e.getMessage());
        }
        return null;
    }

    private void handle(long time)  {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info("[zkregister] sleep error,e="+e.getMessage());
        }
    }

}