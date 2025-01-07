package com.wankun.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class TestCAS {
  public static void main(String[] args) throws InterruptedException {
    AtomicInteger idx = new AtomicInteger();
    Thread[] threads = new Thread[3];
    for (int i = 0; i < 3; i++) {
      threads[i] = new Thread("Worker " + i) {

        @Override public void run() {
          int cur = idx.incrementAndGet();
          while (cur < 100) {
            System.out.println(getName() + " : " + cur);
            cur = idx.incrementAndGet();
          }
        }
      };
      threads[i].start();
    }
    for (Thread thread : threads) {
      thread.join();
    }
  }
}
