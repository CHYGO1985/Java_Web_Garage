package com.chygo.rpc.consumer.listener;

import com.chygo.rpc.api.NodeChangeListener;
import com.chygo.rpc.api.RpcRegistryHandler;
import com.chygo.rpc.consumer.client.RpcClient;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 *
 * The server change listener for removing services from list when a server is offline.
 *
 * @author jingjiejiang
 * @history Aug 25, 2021
 *
 */
public class ServerChangeListener implements NodeChangeListener {

    private static final int IP_IDX = 0;
    private static final int PORT_NUM_IDX = 1;

    private static final Map<String, List<RpcClient>> SERVICE_CLIENT_MAP = new ConcurrentHashMap<>();
    private RpcRegistryHandler rpcRegistryHandler;
    private Map<String, Object> serviceInstanceMap;

    /**
     *
     * Init <service interface name : client list> map.
     * service interface name: com.lagou.edu.rpc.api.UserService
     *
     * @param rpcRegistryHandler
     * @param serviceInstanceMap
     */
    public ServerChangeListener(RpcRegistryHandler rpcRegistryHandler, Map<String, Object> serviceInstanceMap) {

        this.rpcRegistryHandler = rpcRegistryHandler;
        this.serviceInstanceMap = serviceInstanceMap;

        // Automatically register client to service interface
        serviceInstanceMap.entrySet().forEach(new Consumer<Map.Entry<String, Object>>() {

            @Override
            public void accept(Map.Entry<String, Object> entry) {

                // interface name: com.chygo.rpc.api.UserService
                String svcsInterfaceName = entry.getKey();

                try {
                    buildSvcsClientMap(svcsInterfaceName);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        rpcRegistryHandler.addListener(this);
    }

    /**
     *
     * Build <service : client objects> map.
     *
     * @param svcsInterfaceName
     * @throws InterruptedException
     *
     */
    private void buildSvcsClientMap(String svcsInterfaceName) throws InterruptedException {

        // 127.0.0.1:8991
        List<String> svrAddrList = rpcRegistryHandler.discover(svcsInterfaceName);

        List<RpcClient> rpcClients = SERVICE_CLIENT_MAP.get(svcsInterfaceName);
        if (CollectionUtils.isEmpty(rpcClients)) {
            rpcClients = new ArrayList<>();
        }

        for (String addr : svrAddrList) {
            // 0: ip, 1: port num
            String[] addrElems = addr.split(":");
            RpcClient rpcClient = new RpcClient(addrElems[IP_IDX], Integer.parseInt(addrElems[PORT_NUM_IDX]));
            rpcClient.initClient(svcsInterfaceName);

            rpcClients.add(rpcClient);
            SERVICE_CLIENT_MAP.put(svcsInterfaceName, rpcClients);
        }
    }

    public static Map<String, List<RpcClient>> getServiceClientMap() {
        return SERVICE_CLIENT_MAP;
    }

    @Override
    public void notify(String children, List<String> serviceList, PathChildrenCacheEvent pathChildrenCacheEvent) {

    }
}
