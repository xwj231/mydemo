package com.example.demo.websocket;
import com.example.demo.utils.SslUtil;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author xuj231
 * @description
 * @date 2019/12/5 17:19
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();

        SSLContext sslCtx = SslUtil.createSSLContext("JKS", "E:\\workspace\\demoCA\\openssl2\\server.jks", "123456");
        SSLEngine sslEngine = sslCtx.createSSLEngine();
        // 服务器模式
        sslEngine.setUseClientMode(false);
        //ssl双向认证
        sslEngine.setNeedClientAuth(false);
        sslEngine.setWantClientAuth(false);
        // engine.setEnabledProtocols(new String[] { "SSLv3", "TLSv1" })
        // TLSv1.2包括了SSLv3
        sslEngine.setEnabledProtocols(new String[] { "TLSv1.2" });
        pipeline.addLast("ssl", new SslHandler(sslEngine));
        
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(8192));
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new WebSocketHandler());

    }
}