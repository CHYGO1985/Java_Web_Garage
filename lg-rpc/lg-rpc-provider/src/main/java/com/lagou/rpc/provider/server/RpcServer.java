package com.lagou.rpc.provider.server;

import com.lagou.rpc.common.RpcRequest;
import com.lagou.rpc.common.RpcResponse;
import com.lagou.rpc.provider.handler.RpcServerHandler;
import com.lagou.rpc.service.JSONSerializer;
import com.lagou.rpc.service.RpcDecoder;
import com.lagou.rpc.service.RpcEncoder;
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
 * 启动类
 *
 * @author jingjiejiang
 * @history Aug 15, 2021
 * 1. add decoder/encoder
 *
 * Aug 21, 2021
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
            //1. 创建线程组
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
            //2. 创建服务端启动助手
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //3. 设置参数
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            //添加String的编解码器
//                            pipeline.addLast(new StringDecoder());
//                            pipeline.addLast(new StringEncoder());

                            // Add JSON Decoder/Encoder
                            pipeline.addLast(new RpcDecoder(RpcRequest.class, new JSONSerializer()));
                            pipeline.addLast(new RpcEncoder(RpcResponse.class, new JSONSerializer()));

                            //业务处理类
                            pipeline.addLast(rpcServerHandler);
                        }
                    });
            //4.绑定端口
            ChannelFuture sync = serverBootstrap.bind(ip, port).sync();
            System.out.println("==========服务端启动成功==========");
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
