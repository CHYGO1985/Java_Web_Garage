package com.chygo.rpc.regsitry.handler;

import com.chygo.rpc.api.NodeChangeListener;
import com.chygo.rpc.api.RpcRegistryHandler;
import com.chygo.rpc.util.Util;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 *
 * The ZooKeeper based register center.
 * The ZNode path will be: /lg-rpc/com.chygo.rpc.api.SimpleResponse/provider/127.0.0.1:8898
 *
 * @author jingjiejiang
 * @history Aug 23, 2021
 *
 */
public class ZookeeperRegistryHandler implements RpcRegistryHandler {

    // unit : ms
    private static final int TIME_OUT = 5000;
    private static final int RETRY_TIMES = 1;
    // unit : ms
    private static final int SLEEP_BTW_RETRY = 1000;
    private static final String PROVIDER_NAME = "provider";

    private static final List<NodeChangeListener> listenerList = new ArrayList<>();
    // ZooKeeper server url: 127.0.0.1:2181
    private final String url;
    private CuratorFramework zkClient;
    private volatile boolean closed;
    private List<String> serviceList = new CopyOnWriteArrayList<>();
    private static final ScheduledExecutorService REPORT_WORKER = Executors.newScheduledThreadPool(1);

    public ZookeeperRegistryHandler(String url) {

        this.url = url;

        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(url)
                .retryPolicy(new RetryNTimes(RETRY_TIMES, SLEEP_BTW_RETRY))
                .connectionTimeoutMs(TIME_OUT)
                .sessionTimeoutMs(TIME_OUT);

        zkClient = builder.build();
        zkClient.getConnectionStateListenable().addListener((CuratorFramework curatorFramework,
                                                             ConnectionState connectionState) -> {
            if (ConnectionState.CONNECTED.equals(connectionState)) {
                System.out.println("The ZooKeeper register server is successfully connected.");
            }
        });

        zkClient.start();

        // Timely report for client
    }

    @Override
    public boolean register(String service, String ip, int port) {
        return false;
    }

    @Override
    public List<String> discover(String servcie) {
        return null;
    }

    @Override
    public void addListener(NodeChangeListener listener) {

    }

    @Override
    public void destroy() {

    }

    /**
     *
     * Create a persistent ZNode without any data on ZooKeeper.
     *
     * @param zNodePath
     *
     */
    public void createPersistentZNodeWithoutData(String zNodePath) {

        try {
            zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(zNodePath);
        } catch (KeeperException.NodeExistsException e) {
            throw new IllegalStateException("Path : " + zNodePath + " already exists.", e);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     *
     * Create an ephemeral ZNode without any data on ZooKeeper.
     *
     * @param zNdoePath
     */
    public void createEphemeralZNodeWithoutData(String zNdoePath) {

        try {
            zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(zNdoePath);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     *
     * Create a persistent ZNode with data on ZooKeeper.
     *
     * @param zNodePath
     * @param zNodeData
     */
    protected void createPersistentZNodeWithData(String zNodePath, String zNodeData) {

        try {
            byte[] dataBytes = zNodeData.getBytes(Util.UTF_8);
            zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(zNodePath, dataBytes);
        } catch (KeeperException.NodeExistsException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     *
     * Create a ephemeral ZNode with data on ZooKeeper.
     *
     * @param zNodePath
     * @param zNodeData
     */
    protected void createEhemeralZNodeWithData(String zNodePath, String zNodeData) {

        try {
            byte[] dataBytes = zNodeData.getBytes(Util.UTF_8);
            zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(zNodePath, dataBytes);
        } catch (UnsupportedEncodingException e) {
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     *
     * Update data of a ZNode with specified path.
     *
     * @param zNodePath
     * @param zNodeData
     */
    protected void updateZNodeData(String zNodePath, String zNodeData) {

        try {
            byte[] dataBytes = zNodeData.getBytes(Util.UTF_8);
            zkClient.setData().forPath(zNodePath, dataBytes);
        } catch (UnsupportedEncodingException e) {
        } catch (Exception exception) {
            throw new IllegalStateException(exception.getMessage(), exception);
        }
    }

    /**
     *
     * Create a ZNode on ZooKeeper without data on ZooKeeper.
     *
     * @param zNodePath
     * @param isEhemeral
     */
    public void createZNodeWithoutData(String zNodePath, boolean isEhemeral) {

        if (isEhemeral) {
            this.createEphemeralZNodeWithoutData(zNodePath);
        } else {
            this.createPersistentZNodeWithoutData(zNodePath);
        }
    }

    /**
     *
     * Create a ZNode with specified data on ZooKeeper.
     *
     * @param zNodePath
     * @param zNodeData
     * @param isEphemeral
     */
    public void createZNodeWithData(String zNodePath, String zNodeData, boolean isEphemeral) {

        if (isEphemeral) {
            this.createEhemeralZNodeWithData(zNodePath, zNodeData);
        } else {
            this.createPersistentZNodeWithData(zNodePath, zNodeData);
        }
    }

    /**
     *
     * Update a ZNode with specified data on ZooKeeper.
     *
     * @param zNodePath
     * @param zNodeData
     *
     */
    public void updateZNodeWithData(String zNodePath, String zNodeData) {

        try {
            byte[] dataBytes = zNodeData.getBytes(Util.UTF_8);
            zkClient.setData().forPath(zNodePath, dataBytes);
        } catch (UnsupportedEncodingException e) {
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     *
     * Update a ZNode with specified data with on ZooKeeper. If the ZNode does not exist, create one.
     *
     * @param zNodePath
     * @param zNodeData
     * @param isEphemeral
     */
    public void updateZNodeWithDataIfExist(String zNodePath, String zNodeData, boolean isEphemeral) {

        if (isEphemeral) {
            updateEphemeralZNode(zNodePath, zNodeData);
        } else {
            updatePersistentZNode(zNodePath, zNodeData);
        }
    }

    /**
     *
     * Check an ephemeral ZNode path exists or not on ZooKeeper.
     * If yes, update ZNode with specified data.
     * If no, create a ephemeral ZNode with specified data.
     *
     * @param zNodePath
     * @param zNodeData
     *
     */
    private void updateEphemeralZNode(String zNodePath, String zNodeData) {

        if (isZNodePathExist(zNodePath)) {
            this.updateZNodeWithData(zNodePath, zNodeData);
        } else {
            this.createEhemeralZNodeWithData(zNodePath, zNodeData);
        }
    }

    /**
     *
     * Check a persistent ZNode path exists or not on ZooKeeper.
     * If yes, update ZNode with specified data.
     * If no, create a ephemeral ZNode with specified data.
     *
     * @param zNodePath
     * @param zNodeData
     *
     */
    private void updatePersistentZNode(String zNodePath, String zNodeData) {

        if (isZNodePathExist(zNodePath)) {
            this.updateZNodeWithData(zNodePath, zNodeData);
        } else {
            this.createPersistentZNodeWithData(zNodePath, zNodeData);
        }
    }

    /**
     *
     * Check if a path of a ZNode exits or not on ZooKeeper.
     *
     * @return
     */
    public boolean isZNodePathExist(String zNodePath) {

        try {
            if (zkClient.checkExists().forPath(zNodePath) != null) {
                return true;
            }
        } catch (KeeperException.NoNodeException e) {
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

        return false;
    }

    /**
     *
     * Generate ZNode path without server ip and port num: /lg-rpc/ + service + /provider
     * Example: /lg-rpc/com.chygo.rpc.api.SimpleResponse/provider/
     * Full path: /lg-rpc/com.chygo.rpc.api.SimpleResponse/provider/127.0.0.1:8898
     *
     * @param service
     * @return
     */
    private String genProviderPath(String service) {
        return Util.ZNODE_PATH_PREFIX + service + Util.ZNODE_PATH_DELIMITER + PROVIDER_NAME;
    }


}
