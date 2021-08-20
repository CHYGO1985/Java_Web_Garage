package com.lagou.rpc.consumer;

import com.lagou.rpc.api.UserService;
import com.lagou.rpc.consumer.proxy.RpcClientProxy;

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
