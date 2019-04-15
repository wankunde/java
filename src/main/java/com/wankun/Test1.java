package com.wankun;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test1 {

  static int i = 0;

  static Lock lock = new ReentrantLock();
  static Condition cond1 = lock.newCondition();
  static Condition cond2 = lock.newCondition();


  static class T1 extends Thread {
    @Override
    public void run() {
      while (true) {

        try {
          lock.lock();
          i++;
          if (i == 100) {
            cond1.signal();
            cond2.await();
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          lock.unlock();
        }

        try {
          Thread.currentThread().sleep(10L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }


    }
  }

  static class T2 extends Thread {
    @Override
    public void run() {
      try {
        lock.lock();
        cond1.await();
        System.out.println("print i :" + i);
        cond2.signal();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }
  }


  public static void main2(String[] args) throws IOException, InterruptedException {
    T1 t1 = new T1();
    T2 t2 = new T2();
    t1.start();
    t2.start();
    t1.join();
    t2.join();
  }

  public static String s2 = new String("abc");
  public static void main(String[] args) {
    String s1 = new String("abc");


    System.out.println(s1 == s2);
  }
}

