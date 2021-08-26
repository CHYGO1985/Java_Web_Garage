package com.chygo.rpc.consumer;

import com.chygo.rpc.api.RpcRegistryHandler;
import com.chygo.rpc.api.UserService;
import com.chygo.rpc.consumer.listener.ServerChangeListener;
import com.chygo.rpc.consumer.proxy.RpcClientProxy;
import com.chygo.rpc.regsitry.handler.ZookeeperRegistryHandler;
import com.chygo.rpc.util.Util;

import java.util.*;

/**
 * For testing.
 *
 * @author jingjiejiang
 * @history Aug 18, 2021
 *
 */
public class ClientBootStrap {
    public static void main(String[] args) {

        Map<String, Object> serviceInstanceMap = new HashMap<>();

        RpcRegistryHandler rpcRegistryHandler = new ZookeeperRegistryHandler(Util.ZOOKEEPER_ADDRESS);
        ServerChangeListener serverChangeListener = new ServerChangeListener(rpcRegistryHandler, serviceInstanceMap);

        UserService userService = (UserService) RpcClientProxy.createProxy(UserService.class,
                serverChangeListener.getServiceClientMap());
//        User user = userService.getById(1);
        String res = userService.getByIdReturnStr(1);
        System.out.println(res);
    }
}
