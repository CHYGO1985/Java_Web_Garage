package com.lagou.rpc.consumer.proxy;

import com.lagou.rpc.common.RpcRequest;
import com.lagou.rpc.common.RpcResponse;
import com.lagou.rpc.consumer.client.RpcClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 客户端代理类-创建代理对象
 * 1.封装request请求对象
 * 2.创建RpcClient对象
 * 3.发送消息
 * 4.返回结果
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
                        //1.封装request请求对象
                        RpcRequest rpcRequest = new RpcRequest();
                        rpcRequest.setRequestId(UUID.randomUUID().toString());
                        rpcRequest.setClassName(method.getDeclaringClass().getName());
                        rpcRequest.setMethodName(method.getName());
                        rpcRequest.setParameterTypes(method.getParameterTypes());
                        rpcRequest.setParameters(args);
                        //2.创建RpcClient对象
                        RpcClient rpcClient = new RpcClient("127.0.0.1", 8899);
                        try {
                            //3.发送消息
                            Object responseMsg = rpcClient.send(rpcRequest);
//                            RpcResponse rpcResponse = JSON.parseObject(responseMsg.toString(), RpcResponse.class);
                            RpcResponse rpcResponse = (RpcResponse) responseMsg;
                            if (rpcResponse.getError() != null) {
                                throw new RuntimeException(rpcResponse.getError());
                            }
                            //4.返回结果
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
