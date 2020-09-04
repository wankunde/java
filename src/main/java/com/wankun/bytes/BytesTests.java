package com.wankun.bytes;

import static com.wankun.bytes.PrintUtils.intString;

import java.util.Arrays;

public class BytesTests {

  public static void main(String[] args) {
    loopMove();
  }

  // 位移运算是在数据上下界内进行循环移动的
  public static void loopMove() {
    // 数据左移动会越界，越界后是负数，
    System.out.println(intString(10));         // 00000000 00000000 00000000 00001010
    System.out.println(intString(10 << 59)); // 01010000 00000000 00000000 00000000
    System.out.println(intString(10 << 60)); // 10100000 00000000 00000000 00000000 = -1610612736

    System.out.println(intString(10));          // 00000000 00000000 00000000 00001010
    System.out.println(intString(10 >> 1));     // 00000000 00000000 00000000 00000101
    System.out.println(intString(10 >>> 1));    // 00000000 00000000 00000000 00000101

    System.out.println(intString(-10));         // 11111111 11111111 11111111 11110110
    System.out.println(intString(-10 >> 1));    // 11111111 11111111 11111111 11111011 = -5
    System.out.println(intString(-10 >>> 1));   // 01111111 11111111 11111111 11111011 = 2147483643
  }

  public void testfilledArray() {
    int[][] d = new int[128][1024 * 1024];
    while (true) {
      try {
        for (int i = 0; i < 128; i++) {
          int[] v = new int[1024 * 1024];
          Arrays.fill(v, 0);
          d[i] = v;
          System.out.println(i + " : " + Runtime.getRuntime().freeMemory() / 1024 / 1024);
          Thread.sleep(10000);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
