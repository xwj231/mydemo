package com.example.demo.httpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author xuj231
 * @description
 * @date 2019/12/12 14:29
 */
public class MyHttpServer {
    public static void main(String[] args) throws Exception {
        //事件循环组（都是死循环）
        // 主线程获取连接分配任务，工作线程收发消息
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建netty封装的启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置线程组
            serverBootstrap.group(bossGroup,workerGroup);
            //
            serverBootstrap.channel(NioServerSocketChannel.class);
            //添加一个处理器
            serverBootstrap.childHandler(new MyHttpServerInitializer());
            ChannelFuture sync = serverBootstrap.bind(9434).sync();
            sync.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
