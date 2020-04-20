package com.example.demonettyssl.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * @author xuj231
 * @description
 * @date 2019/11/30 16:00
 */
public class Main {
    private static String m_host = "127.0.0.1";
    private static int m_prot = 23333;

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    public void run() throws Exception {
        File certChainFile = new File(".\\demo-netty-ssl\\src\\main\\resources\\ssl\\client.crt");
        File keyFile = new File(".\\demo-netty-ssl\\src\\main\\resources\\ssl\\pkcs8_client.key");
        File rootFile = new File(".\\demo-netty-ssl\\src\\main\\resources\\ssl\\ca.crt");
        final SslContext sslCtx = SslContextBuilder.forClient().keyManager(certChainFile, keyFile).trustManager(rootFile).build();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).handler(new Initializer(sslCtx));
            Channel ch = b.connect(m_host, m_prot).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                ch.writeAndFlush(in.readLine() + "\r\n");
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}