package com.wankun.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionPrint {

  public static int cnt = 4;
  public static int seq = 0;

  public static void main(String[] args) throws InterruptedException {
    Lock lock = new ReentrantLock();
    Condition cond = lock.newCondition();

    ExecutorService executors = Executors.newCachedThreadPool();
    for (int i = 0; i < cnt; i++) {
      executors.submit(new PrintThread(i, lock, cond));
    }
    executors.shutdown();
  }

  static class PrintThread implements Runnable {
    private final int i;
    private final char c;
    private final Lock lock;
    private final Condition cond;

    public PrintThread(int i, Lock lock, Condition cond) {
      this.i = i;
      this.c = (char) (i + 'A');
      this.lock = lock;
      this.cond = cond;
    }

    @Override public void run() {
      for (int i = 0; i < 10; i++) {
        doPrint();
      }
      System.out.print("\nPrinter " + c + " finished!");
    }

    public void doPrint() {
      lock.lock();
      try {
        // 获取lock 并看一下是否是自己，不是，则退出，否则开始干活
        while (seq != i) {
          cond.await(); // 这里要进行await() 来释放 CPU，否则会进入死循环的lock, unlock 操作
        }
        System.out.print(c);
        seq = (seq + 1) % cnt;
        cond.signalAll();
      } catch (Exception e) {
        throw new RuntimeException(e);
      } finally {
        lock.unlock();
      }
    }
  }

}

