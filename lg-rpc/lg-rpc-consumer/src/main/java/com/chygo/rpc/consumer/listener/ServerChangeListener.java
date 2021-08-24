package com.chygo.rpc.consumer.listener;

import com.chygo.rpc.api.NodeChangeListener;
import com.chygo.rpc.api.RpcRegistryHandler;
import com.chygo.rpc.consumer.client.RpcClient;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerChangeListener implements NodeChangeListener {

    private static final Map<String, List<RpcClient>> SERVICE_CLIENT_MAP = new ConcurrentHashMap<>();

    public ServerChangeListener(RpcRegistryHandler rpcRegistryHandler, Map<String, Object> serviceInstanceMap) {

    }

    public static Map<String, List<RpcClient>> getServiceClientMap() {
        return SERVICE_CLIENT_MAP;
    }

    @Override
    public void notify(String children, List<String> serviceList, PathChildrenCacheEvent pathChildrenCacheEvent) {

    }
}
