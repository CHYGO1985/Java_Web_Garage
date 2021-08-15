package com.lagou.rpc.consumer.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Callable;

/**
 * 客户端处理类
 * 1.发送消息
 * 2.接收消息
 *
 * @author jingjiejiang
 * @history Aug 15, 2021
 * 1. add requestObjMsg for send as an object.
 *
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<String> implements Callable {

    ChannelHandlerContext context;
    //发送的消息
    String requestMsg;

    //服务端的消息
    String responseMsg;

    private Object requestObjMsg;

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }

    public void setRequestObjMsg(Object requestObjMsg) {
        this.requestObjMsg = requestObjMsg;
    }

    /**
     * 通道连接就绪事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    /**
     * 通道读取就绪事件
     *
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected synchronized void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        responseMsg = msg;
        //唤醒等待的线程
        notify();
    }

    /**
     * 发送消息到服务端
     *
     * @return
     * @throws Exception
     */
    public synchronized Object call1() throws Exception {
        //消息发送
        context.writeAndFlush(requestMsg);
        //线程等待
        wait();
        return responseMsg;
    }

    /**
     *
     * Send an object to server side.
     *
     */
    @Override
    public synchronized Object call() throws Exception {

        context.writeAndFlush(requestObjMsg);
        wait();
        return responseMsg;
    }
}
