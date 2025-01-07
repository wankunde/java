package com.wankun.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程 Z 字行打印 : ABCDCBABCD..
 */
public class SemaphorePrinter2 {
  public static int cnt = 4;
  public static Semaphore[] semaphores = new Semaphore[cnt];
  public static AtomicInteger flag = new AtomicInteger(-1);

  public static void main(String[] args) {
    semaphores[0] = new Semaphore(1);
    for (int i = 1; i < cnt; i++) {
      semaphores[i] = new Semaphore(0);
    }

    ExecutorService executor = Executors.newCachedThreadPool();
    for (int i = 0; i < cnt; i++) {
      executor.execute(new SemaphorePrinterThread(i));
    }
    executor.shutdown();
  }

  static class SemaphorePrinterThread extends Thread {
    private int i;
    private char c;

    public SemaphorePrinterThread(int i) {
      this.i = i;
      this.c = (char) (i + 'A');
    }

    @Override public void run() {
      int printCount = 20;
      if (i == 0 || i == cnt - 1) {
        printCount /= 2;
      }
      for (int j = 0; j < printCount; j++) {
        try {
          semaphores[i].acquire();
          System.out.print(c);
          if (i == 0 || i == cnt - 1) {
            flag.set(flag.get() * -1);
          }
          semaphores[i + flag.get()].release();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

}
