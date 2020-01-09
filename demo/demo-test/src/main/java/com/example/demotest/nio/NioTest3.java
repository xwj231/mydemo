package com.example.demotest.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author xuj231
 * @description 证明像channel中写入数据，需要先写入buffer中
 * @date 2020/1/9 16:27
 */
public class NioTest3 {
    public static void main(String[] args) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("NioTest3.txt");
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        byte[] message = "hello myself".getBytes();
        byteBuffer.put(message);
        //同样需要翻转，不然跟指针在结尾，读取不到指针前的数据，写入文件数据为空
        byteBuffer.flip();
        channel.write(byteBuffer);
        fileOutputStream.close();
    }
}
