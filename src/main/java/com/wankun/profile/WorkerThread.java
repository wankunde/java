package com.wankun.profile;

import java.util.Random;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-05-18.
 */
public class WorkerThread {

  public static void main(String[] args) throws InterruptedException {
    int count = 0;
    for (int i = 0; i < 100000; i++) {
      Thread.sleep(10);
      count++;
      int r = new Random().nextInt(100);
      System.out.println(r);
    }
  }
}
