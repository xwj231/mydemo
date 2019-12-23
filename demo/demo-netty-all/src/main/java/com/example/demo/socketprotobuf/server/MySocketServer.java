package com.example.demo.socketprotobuf.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author xuj231
 * @description
 * @date 2019/12/20 11:09
 */
public class MySocketServer {
    public static void main(String[] args) throws Exception {
        
        /**
         * 事件循环组（都是死循环）
         * 主线程获取连接分配任务，工作线程收发消息
         * bossGroup供handler使用
         * workGroup供childHandler使用
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new MySocketServerInitializer());
            ChannelFuture sync = serverBootstrap.bind(1111).sync();
            sync.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
