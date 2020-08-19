package com.wankun.bytes;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-08-19.
 */
public class BytesTests {

    public static void main(String[] args) {
        loopMove();
    }

    // 位移运算是在数据上下界内进行循环移动的
    public static void loopMove() {
        Long l = 10L;
        System.out.println(Long.toBinaryString(l)); // 1010
        System.out.println(Long.toBinaryString(l << 2)); // 101000
        System.out.println(Long.toBinaryString(l << 65)); // 10100
        System.out.println(l << 60); // 根据Long类型定义，第一位应该是符号位 -6917529027641081856
        System.out.println(Long.toBinaryString(l << 60));
        System.out.println(l << 62);
        System.out.println(Long.toBinaryString(l << 62));
    }
}
