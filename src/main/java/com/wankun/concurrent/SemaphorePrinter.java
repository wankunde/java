package com.wankun.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphorePrinter {
  public static int cnt = 4;

  public static void main(String[] args) {
    Semaphore[] semaphores = new Semaphore[cnt];
    semaphores[0] = new Semaphore(1);
    for (int i = 1; i < cnt; i++) {
      semaphores[i] = new Semaphore(0);
    }

    ExecutorService executor = Executors.newCachedThreadPool();
    for (int i = 0; i < cnt; i++) {
      executor.execute(new SemaphorePrinterThread(i, semaphores[i], semaphores[(i + 1) % cnt]));
    }
    executor.shutdown();
  }

  static class SemaphorePrinterThread extends Thread {
    private Semaphore cur;
    private Semaphore next;
    private char c;

    public SemaphorePrinterThread(int i, Semaphore cur, Semaphore next) {
      this.c = (char) (i + 'A');
      this.cur = cur;
      this.next = next;
    }

    @Override public void run() {
      for (int j = 0; j < 10; j++) {
        try {
          cur.acquire();
          System.out.print(c);
          next.release();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

}
