package com.lagou.rpc.consumer;

import com.lagou.rpc.api.IUserService;
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
        IUserService userService = (IUserService) RpcClientProxy.createProxy(IUserService.class);
//        User user = userService.getById(1);
        String res = userService.getByIdReturnStr(1);
        System.out.println(res);
    }
}
