package com.chygo.rpc.provider.server;

import com.chygo.rpc.pojo.RpcRequest;
import com.chygo.rpc.provider.handler.RpcServerHandler;
import com.chygo.rpc.service.RpcDecoder;
import com.chygo.rpc.service.RpcEncoder;
import com.chygo.rpc.pojo.RpcResponse;
import com.chygo.rpc.serializer.JSONSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * The server class.
 *
 * @author jingjiejiang
 * @history Aug 15, 2021
 * 1. add decoder/encoder
 *
 * Aug 18, 2021
 * 1. Change encoder to JSON encoder.
 *
 */
@Service
public class RpcServer implements DisposableBean {

    private NioEventLoopGroup bossGroup;

    private NioEventLoopGroup workerGroup;

    @Autowired
    RpcServerHandler rpcServerHandler;

    public void startServer(String ip, int port) {
        try {
            // 1. Create thread pool.
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
            // 2. Creat server bootstrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 3. Config server
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            // Add encoder/decoder for String msg
//                            pipeline.addLast(new StringDecoder());
//                            pipeline.addLast(new StringEncoder());

                            // Add JSON Decoder/Encoder
                            pipeline.addLast(new RpcDecoder(RpcRequest.class, new JSONSerializer()));
                            pipeline.addLast(new RpcEncoder(RpcResponse.class, new JSONSerializer()));

                            // Business logic
                            pipeline.addLast(rpcServerHandler);
                        }
                    });

            // 4. Bind port
            ChannelFuture sync = serverBootstrap.bind(ip, port).sync();
            System.out.println("========== Server is successfully launched ==========");
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (bossGroup != null) {
                bossGroup.shutdownGracefully();
            }

            if (workerGroup != null) {
                workerGroup.shutdownGracefully();
            }
        }
    }


    /**
     *
     * Close resources.
     *
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }

        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }
}
