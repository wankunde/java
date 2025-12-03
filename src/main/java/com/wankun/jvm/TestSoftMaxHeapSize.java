package com.wankun.jvm;

import java.util.LinkedList;
import java.util.List;

public class TestSoftMaxHeapSize {

  public static  final int BLOCK_SIZE = 4096;
  public static  final int MIN_BLOCK_NUM = 1024 * 1024 / 4;
  public static  final int MAX_BLOCK_NUM = MIN_BLOCK_NUM * 3;
  public static void main(String[] args) throws InterruptedException {

    List<byte[]> list = new LinkedList<>();
    long start = System.currentTimeMillis();
    long end = start + 180 * 1000;
    while (System.currentTimeMillis() <= end) {
      adjustHeapSize(list);
    }
    list.clear();
    list = null;
    System.out.println("Current time: " + System.currentTimeMillis());
    Thread.sleep(10000000L);
  }

  public static void adjustHeapSize(List<byte[]> list) {
    int blockNum = System.currentTimeMillis() / 1000 / 60 % 2 == 0 ? MIN_BLOCK_NUM : MAX_BLOCK_NUM;
    if(list.size() < blockNum) {
      for (int i = list.size(); i < blockNum; i++) {
        list.add(new byte[BLOCK_SIZE]);
      }
    } else if (list.size() > blockNum) {
      while (list.size() > blockNum && list.size() > 0) {
        list.remove(0);
      }
    }
    try {
      Thread.sleep(1L);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
