package com.example.demonettyssl.utils;

import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;


/**
 * @author xuj231
 * @description
 * @date 2019/11/23 10:20
 */
public class SslUtil {

    public static SslContext getSslContextService(InputStream ccf, InputStream kf, InputStream rf) {
        InputStream certChainFile = ccf;
        InputStream keyFile = kf;
        InputStream rootFile = rf;
        SslContext sslCtx = null;
        try {
            sslCtx = SslContextBuilder.forServer(certChainFile, keyFile).trustManager(rootFile).clientAuth(ClientAuth.REQUIRE).build();
        } catch (SSLException e) {
            
        }
        return sslCtx;
    }

    public static SSLContext createSSLContext(String type, String path, String password) throws SSLException {
        // "JKS"
        KeyStore ks = null; 
        // "JKS"
        SSLContext sslContext = null;
        try {
            ks = KeyStore.getInstance(type);
            // 证书存放地址
            InputStream ksInputStream = new FileInputStream(path); 
            ks.load(ksInputStream, password.toCharArray());
            //KeyManagerFactory充当基于密钥内容源的密钥管理器的工厂。
            KeyManagerFactory kmf = KeyManagerFactory
                    //getDefaultAlgorithm:获取默认的 KeyManagerFactory 算法名称。
                    .getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password.toCharArray());
            //SSLContext的实例表示安全套接字协议的实现，它充当用于安全套接字工厂或 SSLEngine 的工厂。
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, null);
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | NoSuchAlgorithmException | IOException | KeyManagementException e) {
            throw new SSLException("create ssl find error "+e.getMessage());
        }

        return sslContext;
    }
    
}