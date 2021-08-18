package com.lagou.rpc.consumer.proxy;

import com.lagou.rpc.common.RpcRequest;
import com.lagou.rpc.common.RpcResponse;
import com.lagou.rpc.consumer.client.RpcClient;
import com.lagou.rpc.util.Util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 *
 * Client proxy class for creating proxy object.
 *
 * 1.Wrap request object
 * 2.Create RpcClient object
 * 3.Send message
 * 4.Get returned result
 *
 * @author jingjiejiang
 * @history Aug 15, 2021
 * 1. revised invoke() method, send as object not string.
 *
 * Aug 18, 2021
 * 1. revised invoke() method, to receive message as JSON.
 *
 */
public class RpcClientProxy {

    public static Object createProxy(Class serviceClass) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass}, new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        // 1.Wrap request object
                        RpcRequest rpcRequest = new RpcRequest();
                        rpcRequest.setRequestId(UUID.randomUUID().toString());
                        rpcRequest.setClassName(method.getDeclaringClass().getName());
                        rpcRequest.setMethodName(method.getName());
                        rpcRequest.setParameterTypes(method.getParameterTypes());
                        rpcRequest.setParameters(args);
                        // 2.Create RpcClient object
                        RpcClient rpcClient = new RpcClient(Util.SERVER_IP,
                                Util.SERVER_PORT_NUM);
                        try {
                            // 3.Send message
                            Object responseMsg = rpcClient.send(rpcRequest);
//                            RpcResponse rpcResponse = JSON.parseObject(responseMsg.toString(), RpcResponse.class);
                            RpcResponse rpcResponse = (RpcResponse) responseMsg;
                            if (rpcResponse.getError() != null) {
                                throw new RuntimeException(rpcResponse.getError());
                            }
                            // 4.Get returned result
                            Object result = rpcResponse.getResult();

                            return result.toString();
                        } catch (Exception e) {
                            throw e;
                        } finally {
                            rpcClient.close();
                        }
                    }
                });
    }
}
