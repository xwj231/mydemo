package com.example.demozk.utils;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ZkClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private CuratorFramework client;
    private String zookeeperServer;
    private int sessionTimeoutMs;
    private int connectionTimeoutMs;
    private int baseSleepTimeMs;
    private int maxRetries;
    private String generatorPath = "/demo/server";
    private RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);

    public void setZookeeperServer(String zookeeperServer) {
        this.zookeeperServer = zookeeperServer;
    }
    public String getZookeeperServer() {
        return zookeeperServer;
    }
    public void setSessionTimeoutMs(int sessionTimeoutMs) {
        this.sessionTimeoutMs = sessionTimeoutMs;
    }
    public int getSessionTimeoutMs() {
        return sessionTimeoutMs;
    }
    public void setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }
    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }
    public void setBaseSleepTimeMs(int baseSleepTimeMs) {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }
    public int getBaseSleepTimeMs() {
        return baseSleepTimeMs;
    }
    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }
    public int getMaxRetries() {
        return maxRetries;
    }

    public void init() {

        client = CuratorFrameworkFactory.builder().connectString(zookeeperServer).retryPolicy(retryPolicy)
                .sessionTimeoutMs(sessionTimeoutMs).connectionTimeoutMs(connectionTimeoutMs).build();
        client.start();
    }

    public void stop() {
        client.close();
    }

    public CuratorFramework getClient() {
        return client;
    }

    public List<String> getChildren(String path) {
        List<String> childrenList = new ArrayList<>();
        try {
            childrenList = client.getChildren().forPath(path);
        } catch (Exception e) {
            logger.error("获取子节点出错", e);
        }
        return childrenList;
    }

    public int getChildrenCount(String path) {
        return getChildren(path).size();
    }


    /**
     * 获取节点数据
     *
     * @param path 路径
     * @return 节点数据，如果出现异常，返回null
     */
    public String getNodeData(String path) {
        try {
            byte[] bytes = client.getData().forPath(path);
            return new String(bytes);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 注册节点数据变化事件
     *
     * @param path              节点路径
     * @param nodeCacheListener 监听事件
     * @return 注册结果
     */
    public boolean registerWatcherNodeChanged(String path, NodeCacheListener nodeCacheListener) {
        NodeCache nodeCache = new NodeCache(client, path, false);
        try {
            nodeCache.getListenable().addListener(nodeCacheListener);
            nodeCache.start(true);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 更新节点数据
     *
     * @param path     路径
     * @param newValue 新的值
     * @return 更新结果
     */
    public boolean updateNodeData(String path, String newValue) {
        //判断节点是否存在
        if (!isExistNode(path)) {
            return false;
        }
        try {
            client.setData().forPath(path, newValue.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断节点是否存在
     *
     * @param path 路径
     * @return true-存在  false-不存在
     */
    public boolean isExistNode(String path) {
        try {
            Stat stat = client.checkExists().forPath(path);
            return stat != null ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void generatorRegister() {
        try {
            if(!isExistNode(generatorPath)){
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(generatorPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(String path){
        try {
            if(isExistNode(path)){
                client.delete().deletingChildrenIfNeeded().forPath(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNode(String nodeName) {
        String path = generatorPath + "/" + nodeName;
        try {
            if(!isExistNode(path)){
                client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createPath(String path) {
        try {
            if(!isExistNode(path)){
                client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long next() {
        generatorRegister();
        try {
            Stat stat = client.setData().forPath(generatorPath, new byte[0]);
            return stat.getVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }

    public String getGeneratorPath() {
        return generatorPath;
    }

    public Long increment(String path){
        DistributedAtomicLong distributedAtomicLong = new DistributedAtomicLong(client, path,retryPolicy);
        try {
            AtomicValue<Long> increment = distributedAtomicLong.increment();
            if(increment.succeeded()){
                return increment.postValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public Long reset(String path){
        /*createPath(path);
        updateNodeData(path,"0");*/
        DistributedAtomicLong distributedAtomicLong = new DistributedAtomicLong(client, path,retryPolicy);

        try {
            //AtomicValue<Long> longAtomicValue = distributedAtomicLong.compareAndSet(Long.valueOf(getChildrenCount(generatorPath)), 0l);
            AtomicValue<Long> longAtomicValue = distributedAtomicLong.compareAndSet(distributedAtomicLong.get().postValue(), 0l);
            if(longAtomicValue.succeeded()){
                return distributedAtomicLong.get().postValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }

    public Long getCurrent(String path){
        /*createPath(path);
        updateNodeData(path,"0");*/
        DistributedAtomicLong distributedAtomicLong = new DistributedAtomicLong(client, path,retryPolicy);

        try {
            return distributedAtomicLong.get().postValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }
}