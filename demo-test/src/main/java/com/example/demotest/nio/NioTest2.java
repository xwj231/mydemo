package com.example.demotest.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author xuj231
 * @description 证明不能直接从channel中读取数据，需要先读入buffer中，再从buffer中读取数据
 * @date 2020/1/9 16:16
 */
public class NioTest2 {
    public static void main(String[] args) throws Exception {
        //文件输入流
        FileInputStream fileInputStream = new FileInputStream("NioTest2.txt");
        //获取文件输入流的通道
        FileChannel channel = fileInputStream.getChannel();
        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        //将通道中数据读取到缓冲区
        channel.read(byteBuffer);
        //反转，不然数据是反的
        byteBuffer.flip();
        while (byteBuffer.remaining() > 0) {
            System.out.println("Charset: " + (char)byteBuffer.get());
        }
        fileInputStream.close();
    }
}
