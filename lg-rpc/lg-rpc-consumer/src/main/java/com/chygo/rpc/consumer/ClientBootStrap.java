package com.chygo.rpc.consumer;

import com.chygo.rpc.api.UserService;
import com.chygo.rpc.consumer.proxy.RpcClientProxy;

/**
 * For testing.
 *
 * @author jingjiejiang
 * @history Aug 18, 2021
 *
 */
public class ClientBootStrap {
    public static void main(String[] args) {

        UserService userService = (UserService) RpcClientProxy.createProxy(UserService.class);
//        User user = userService.getById(1);
        String res = userService.getByIdReturnStr(1);
        System.out.println(res);
    }
}
