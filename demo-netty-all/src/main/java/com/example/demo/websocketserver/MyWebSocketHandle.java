package com.example.demo.websocketserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author xuj231
 * @description
 * @date 2019/12/20 11:21
 */
public class MyWebSocketHandle extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    /**
     * 收到消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("收到消息：" + msg.text());   
        
        //泛型类是TextWebSocketFrame，所以通道不能直接传字符串，否者运行会报错
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器收到消息："+msg.text()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道进入" + ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //异常发生时，需要关闭连接
        ctx.channel();
    }
}
