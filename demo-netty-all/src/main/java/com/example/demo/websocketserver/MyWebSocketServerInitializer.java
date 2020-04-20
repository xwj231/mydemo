package com.example.demo.websocketserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

/**
 * @author xuj231
 * @description
 * @date 2019/12/20 11:11
 */
public class MyWebSocketServerInitializer extends ChannelInitializer<SocketChannel> {
    /**
     * 
     */ 
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //http编解码器
        pipeline.addLast(new HttpServerCodec());
        //以块的方式写的处理器
        pipeline.addLast(new ChunkedWriteHandler());
        //netty会将大的请求分段(拆包) 这个处理器就是将被分块的请求聚合成一个完整的请求或者响应
        pipeline.addLast(new HttpObjectAggregator(8192));
        //完成websocket那些繁重的处理
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new MyWebSocketHandle());
    }
}
