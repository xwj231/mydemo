package com.example.demo.httpserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author xuj231
 * @description 
 * @date 2019/12/12 14:40
 */
public class MyHttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        /**
         * HttpServerCodec相当于HttpRequestDecoder与HttpResponseEncoder的结合
         */
        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        pipeline.addLast("myHttpServerHandle",new MyHttpServerHandle());
    }
    
}
