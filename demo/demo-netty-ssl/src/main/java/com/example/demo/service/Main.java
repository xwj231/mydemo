package com.example.demo.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.InputStream;

/**
 * @author xuj231
 * @description
 * @date 2019/11/29 16:19
 */
public class Main {
    private static final int m_port = 23333;

    public void run() throws Exception {
/*        File certChainFile = new File(".\\demo-netty-ssl\\src\\main\\resources\\ssl\\server.crt");
        File keyFile = new File(".\\demo-netty-ssl\\src\\main\\resources\\ssl\\pkcs8_server.key");
        File rootFile = new File(".\\demo-netty-ssl\\src\\main\\resources\\ssl\\ca.crt");*/
        InputStream certChainFile = this.getClass().getResourceAsStream("/ssl/server.crt");
        InputStream keyFile = this.getClass().getResourceAsStream("/ssl/pkcs8_server.key");
        InputStream rootFile = this.getClass().getResourceAsStream("/ssl/ca.crt");
        SslContext sslCtx = SslContextBuilder.forServer(certChainFile, keyFile).trustManager(rootFile).clientAuth(ClientAuth.REQUIRE).build();
        //主线程（获取连接，分配给工作线程）
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //工作线程，负责收发消息
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //netty封装的启动类
            ServerBootstrap b = new ServerBootstrap();
            //设置线程组，主线程和子线程
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //初始化处理器
                    .childHandler(new Initializer(sslCtx));
            ChannelFuture f = b.bind(m_port).sync();
            f.channel().closeFuture().sync();
        } finally {
            //出现异常以后，优雅关闭线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws Exception {
        new Main().run();
    }
}