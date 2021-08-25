package com.chygo.rpc.consumer.client;

import com.chygo.rpc.pojo.RpcRequest;
import com.chygo.rpc.pojo.RpcResponse;
import com.chygo.rpc.consumer.handler.RpcClientHandler;
import com.chygo.rpc.serializer.JSONSerializer;
import com.chygo.rpc.service.RpcDecoder;
import com.chygo.rpc.service.RpcEncoder;
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
 *
 * RPC client.
 *
 * 1.Connect to Netty server.
 * 2.Provide the invoker a way to close resources
 * 3.Provide methods to send message to server.
 *
 * @author jingjiejiang
 * @history Aug 15, 2021
 * 1. add a new send() method for send as an object.
 *
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
    }

    /**
     *
     * Init method -- connect to Netty server.
     *
     * @param svcsInterfaceName
     */
    public void initClient(String svcsInterfaceName) throws InterruptedException {

        try {
            // 1.Create thread pool
            group = new NioEventLoopGroup();
            // 2.Create bootstrap
            Bootstrap bootstrap = new Bootstrap();
            // 3.Config Bootstrap
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();

                            // Encoder/Decoder for String
//                            pipeline.addLast(new StringDecoder());
//                            pipeline.addLast(new StringEncoder());

                            // Add JSON Decoder/Encoder
                            pipeline.addLast(new RpcDecoder(RpcResponse.class, new JSONSerializer()));
                            pipeline.addLast(new RpcEncoder(RpcRequest.class, new JSONSerializer()));
                            // Add client handler
                            pipeline.addLast(rpcClientHandler);
                        }
                    });
            // 4. Connect to Netty server
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

        System.out.println("==== Launch client: " + svcsInterfaceName + ", ip" + ip + ", port" + port + "====");
    }

    /**
     *
     * Provide method to actively close resources
     *
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
     *
     * send message as an object.
     *
     */
    public Object send(Object msg) throws ExecutionException, InterruptedException {

        rpcClientHandler.setRequestObjMsg(msg);
        Future submit = executorService.submit(rpcClientHandler);
        return submit.get();
    }
}
