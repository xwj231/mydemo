package com.example.demozk.test;

import com.example.demozk.utils.ZkClient;

/**
 * @author xuj231
 * @description
 * @date 2019/9/10 16:13
 */
public class Test {
    
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient();
        zkClient.setZookeeperServer("192.168.2.110:2181");
        zkClient.setSessionTimeoutMs(6000);
        zkClient.setConnectionTimeoutMs(6000);
        zkClient.setMaxRetries(3);
        zkClient.setBaseSleepTimeMs(1000);
        
        zkClient.init();
        
        zkClient.addNode("test");
        System.out.println("节点是否存在:"+zkClient.isExistNode("/demo/server/test"));
        System.out.println("获取节点数据:"+zkClient.getNodeData("/demo/server/test"));
    }
}
