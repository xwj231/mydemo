package com.example.demo.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author xuj231
 * @description
 * @date 2019/11/30 17:50
 */
public class Handler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("收到：" + s);
        TimeUnit.SECONDS.sleep(2);
        ChannelFuture result = channelHandlerContext.channel().writeAndFlush("client 发送\n");
        System.out.println("发送结果:"+result.isSuccess());
    }
}