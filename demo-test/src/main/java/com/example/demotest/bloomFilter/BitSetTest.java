package com.example.demotest.bloomFilter;

import org.apache.lucene.util.RamUsageEstimator;

import java.util.BitSet;

/**
 * @author xuwj
 * @date 2021/8/10 14:52
 * @Description bitmap测试
 */
public class BitSetTest {
    /**
     * 1个int = 4个byte
     * 1byte = 8bit
     * 1bit表示表示一个数字是否存在
     * 按字节位存，int[5]最大可以存放4 * 8 * 5个小于4 * 8 * 5的不重复数字
     */
    private static int[] arr = new int[5];

    public static void main(String[] args) {
        BitSet bitSet = new BitSet();
        // 8388608bit = 1bit * 8 * 1024 * 1024 = 1MB
        /*for (int i = 0; i < 8388608; i++) {
            bitSet.set(i);
        }*/
        bitSet.set(1);
        System.out.println(bitSet.length());
        System.out.println(bitSet.get(0, 4).length());
        System.out.println(bitSet.get(5, 10).length());
        System.out.println(bitSet.get(0, 10));
        System.out.println(bitSet.get(5));

        // 获取对象占用内存大小
        System.out.println("-----------------------------------");
        // 计算指定对象及其引用树上的所有对象的综合大小，单位字节
        System.out.println(RamUsageEstimator.sizeOf(bitSet));

        // 计算指定对象本身在堆空间的大小，单位字节
        System.out.println(RamUsageEstimator.shallowSizeOf(bitSet));

        // 计算指定对象及其引用树上的所有对象的综合大小，返回可读的结果，如：2KB
        System.out.println(RamUsageEstimator.humanSizeOf(bitSet));

    }


}
