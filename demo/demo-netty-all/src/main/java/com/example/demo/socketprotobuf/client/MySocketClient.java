package com.example.demo.socketprotobuf.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * @author xuj231
 * @description
 * @date 2019/11/30 16:00
 */
public class MySocketClient {
    private static String m_host = "192.168.2.192";
    private static int m_prot = 1111;

    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioSocketChannel.class);
            b.handler(new MySocketClientInitializer());
            ChannelFuture sync = b.connect(m_host, m_prot).sync();
            sync.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
    
}