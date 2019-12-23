package com.example.demo.socketprotobuf.client;

import com.example.demo.protobuf.PbMessage;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * @author xuj231
 * @description
 * @date 2019/11/30 17:50
 */
public class MyClientHandler extends SimpleChannelInboundHandler<PbMessage.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PbMessage.MyMessage msg) throws Exception {
        System.out.println("收到：" + msg.getData());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接服务端成功。。。");
        try {
            PbMessage.MyMessage msg = PbMessage.MyMessage.newBuilder().setData("客户端连接成功").build();
            ChannelFuture cf = ctx.writeAndFlush(msg);
            System.out.println("消息发送结果："+cf.isSuccess());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}