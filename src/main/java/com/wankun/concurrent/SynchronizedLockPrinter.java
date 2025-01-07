package com.wankun.concurrent;

public class SynchronizedLockPrinter {
  static class Printer {
    private final Object lock = new Object();
    private int count = 0;
    public void print(int n, int target, char content) {
      for (int i = 0; i < n; ) {
        synchronized (lock) {
          while (count % 3 != target) {
            try {
              lock.wait();
            } catch (Exception e) {
              System.out.println(e);
            }
          }
          System.out.print(content);
          count++;
          i++;
          lock.notifyAll();
        }
      }
    }

    public void print() {
      new Thread(()-> {print(10, 0, 'A');}).start();
      new Thread(()-> {print(10, 1, 'B');}).start();
      new Thread(()-> {print(10, 2, 'C');}).start();
    }
  }

  public static void main(String [] args) {
    new Printer().print();
  }
}