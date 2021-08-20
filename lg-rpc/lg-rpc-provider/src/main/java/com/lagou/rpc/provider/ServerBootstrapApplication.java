package com.lagou.rpc.provider;

import com.lagou.rpc.provider.server.RpcServer;
import com.lagou.rpc.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 *  Main method for server
 *
 * @author jingjiejiang
 * @history Aug 19, 2021
 *
 */
@SpringBootApplication
public class ServerBootstrapApplication implements CommandLineRunner {

    @Autowired
    RpcServer rpcServer;

    public static void main(String[] args) {
        SpringApplication.run(ServerBootstrapApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                rpcServer.startServer(Util.SERVER_IP, Util.SERVER_PORT_NUM);
            }
        }).start();
    }
}
