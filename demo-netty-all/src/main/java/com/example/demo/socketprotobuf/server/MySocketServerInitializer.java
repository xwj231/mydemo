package com.example.demo.socketprotobuf.server;

import com.example.demo.protobuf.PbMessage;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @author xuj231
 * @description
 * @date 2019/12/20 11:11
 */
public class MySocketServerInitializer extends ChannelInitializer<SocketChannel> {
    /**
     * 
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        //解码器设置一个默认实例，让程序知道解成什么样子
        pipeline.addLast(new ProtobufDecoder(PbMessage.MyMessage.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new MySocketHandle());
    }
}
