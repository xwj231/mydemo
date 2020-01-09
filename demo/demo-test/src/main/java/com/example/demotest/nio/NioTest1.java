package com.example.demotest.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @author xuj231
 * @description  buffer使用
 * @date 2020/1/9 15:28
 */
public class NioTest1 {
    public static void main(String[] args) {
        //分配一个大小为10的缓冲区
        IntBuffer intBuffer = IntBuffer.allocate(10);
        for (int i = 0;i < 10;i++) {
            //SecureRandom生成的随机数比Random更强健更随机
            int randomNumber = new SecureRandom().nextInt(20);
            intBuffer.put(randomNumber);
        }
        //翻转
        //limit = position;
        //position = 0;
        //mark = -1;
        intBuffer.flip();intBuffer.clear();
        //是否还有剩下的元素
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
