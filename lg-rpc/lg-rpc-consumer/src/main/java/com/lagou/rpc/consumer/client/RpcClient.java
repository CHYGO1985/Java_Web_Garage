package com.lagou.rpc.consumer.client;

import com.lagou.rpc.common.RpcRequest;
import com.lagou.rpc.common.RpcResponse;
import com.lagou.rpc.consumer.handler.RpcClientHandler;
import com.lagou.rpc.service.JSONSerializer;
import com.lagou.rpc.service.RpcDecoder;
import com.lagou.rpc.service.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 客户端
 * 1.连接Netty服务端
 * 2.提供给调用者主动关闭资源的方法
 * 3.提供消息发送的方法
 */
public class RpcClient {

    private EventLoopGroup group;

    private Channel channel;

    private String ip;

    private int port;

    private RpcClientHandler rpcClientHandler = new RpcClientHandler();

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public RpcClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        initClient();
    }

    /**
     * 初始化方法-连接Netty服务端
     */
    public void initClient() {
        try {
            //1.创建线程组
            group = new NioEventLoopGroup();
            //2.创建启动助手
            Bootstrap bootstrap = new Bootstrap();
            //3.设置参数
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            //String类型编解码器
//                            pipeline.addLast(new StringDecoder());
//                            pipeline.addLast(new StringEncoder());

                            // Add JSON Decoder/Encoder
                            pipeline.addLast(new RpcDecoder(RpcResponse.class, new JSONSerializer()));
                            pipeline.addLast(new RpcEncoder(RpcRequest.class, new JSONSerializer()));
                            //添加客户端处理类
                            pipeline.addLast(rpcClientHandler);
                        }
                    });
            //4.连接Netty服务端
            channel = bootstrap.connect(ip, port).sync().channel();
        } catch (Exception exception) {
            exception.printStackTrace();
            if (channel != null) {
                channel.close();
            }
            if (group != null) {
                group.shutdownGracefully();
            }
        }
    }

    /**
     * 提供给调用者主动关闭资源的方法
     */
    public void close() {
        if (channel != null) {
            channel.close();
        }
        if (group != null) {
            group.shutdownGracefully();
        }
    }

    /**
     * 提供消息发送的方法
     */
    public Object send(String msg) throws ExecutionException, InterruptedException {
        rpcClientHandler.setRequestMsg(msg);
        Future submit = executorService.submit(rpcClientHandler);
        return submit.get();
    }
}
