package com.example.demo.socketprotobuf.server;

import com.example.demo.protobuf.PbMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author xuj231
 * @description
 * @date 2019/12/20 11:21
 */
public class MySocketHandle extends SimpleChannelInboundHandler<PbMessage.MyMessage> {
    ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * 客户端消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PbMessage.MyMessage msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+":"+msg.getData());
        ctx.writeAndFlush(PbMessage.MyMessage.newBuilder().setData("bie bb").build());
    }

    /**
     * 连接成功调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    /**
     * 新通道加入
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        
        //实际发送消息还是遍历通道循环发送消息
        channels.writeAndFlush("新加入通道：" + channel.remoteAddress());
        
        channels.add(channel);
    }

    /**
     * 新通道断开
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        //可以不手动移除断开通道，通道断开后会自动从ChannelGroup中移除
        //调用不会报错，但是没有必要
        //channels.remove(channel);

        channels.writeAndFlush("断开通道：" + channel.remoteAddress());
    }

    /**
     * 客户端连接异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        System.out.println(ctx.channel().remoteAddress()+":连接异常");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        Channel channel = ctx.channel();
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)evt;
            switch (event.state()) {
                case ALL_IDLE:break;
                case READER_IDLE:break;
                case WRITER_IDLE:break;
                default:break;
            }
        }
    }
}
