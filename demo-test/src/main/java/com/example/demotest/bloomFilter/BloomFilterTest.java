package com.example.demotest.bloomFilter;

import org.apache.lucene.util.RamUsageEstimator;

import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

/**
 * @author xuwj
 * @date 2021/8/10 13:46
 * @Description
 */
public class BloomFilterTest {
    /**
     * BitSet初始分配2^24个bit
     */
    private static final int DEFAULT_SIZE = 1 << 25;
    /**
     * 不同哈希函数的种子，一般应取质数
     */
    private static final int[] seeds = new int[] { 5, 7, 11, 13, 31, 37, 61 };
    private BitSet bits = new BitSet(DEFAULT_SIZE);
    /**
     * 哈希函数对象
     */
    private SimpleHash[] func = new SimpleHash[seeds.length];

    public BloomFilterTest() {
        for (int i = 0; i < seeds.length; i++) {
            func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
        }
    }

    /**
     * 将字符串标记到bits中
     * @param value
     */
    public void add(String value) {
        for (SimpleHash f : func) {
            bits.set(f.hash(value), true);
        }
    }

    /**
     * 判断字符串是否已经被bits标记
     * @param value
     * @return
     */
    public boolean contains(String value) {
        if (value == null) {
            return false;
        }
        boolean ret = true;
        for (SimpleHash f : func) {
            ret = ret && bits.get(f.hash(value));
        }
        return ret;
    }

    public BitSet getBitSet() {
        return bits;
    }

    /**
     * 哈希函数类
     */
    public static class SimpleHash {
        private int cap;
        private int seed;

        public SimpleHash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        // hash函数，采用简单的加权和hash
        public int hash(String value) {
            int result = 0;
            int len = value.length();
            for (int i = 0; i < len; i++) {
                result = seed * result + value.charAt(i);
            }
            return (cap - 1) & result;
        }
    }

    public static void main(String[] args) {
        BloomFilterTest bloomFilter = new BloomFilterTest();
        bloomFilter.add("123456");
        System.out.println(bloomFilter.contains("12345"));
        System.out.println(bloomFilter.contains("123456"));

        // 计算指定对象及其引用树上的所有对象的综合大小，返回可读的结果，如：2KB
        System.out.println(RamUsageEstimator.humanSizeOf(bloomFilter.getBitSet()));
    }
}
