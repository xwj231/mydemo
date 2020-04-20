package com.example.demo.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author xuj231
 * @description
 * @date 2019/12/23 11:47
 */
public class ProtoBufTest {
    public static void main(String[] args) {
        DateInfo.Student student = DateInfo
                .Student
                .newBuilder()
                .setName("张三")
                .setAddress("sichuan")
                .setAge("12")
                .build();
        
        byte[] student2ByteArray = student.toByteArray();

        DateInfo.Student student2 = null;
        try {
            student2 = DateInfo.Student.parseFrom(student2ByteArray);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        System.out.println(student2.getName());
    }
}
