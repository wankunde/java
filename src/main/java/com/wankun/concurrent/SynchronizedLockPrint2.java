package com.wankun.concurrent;

public class SynchronizedLockPrint2 {

  final static int cnt = 3;

  static Integer flag = 0;

  public static void main(String[] args) throws InterruptedException {
    Printer[] threads = new Printer[cnt];
    Object lock = new Object(); // 三个变量通过lock 来同步，每次只有一个线程工作，其余的 wait()

    for (int i = 0; i < cnt; i++) {
      threads[i] = new Printer(i, flag, lock);
    }
    for (int i = cnt - 1; i >= 0; i--) {
      threads[i].start();
    }

    for (int i = 0; i < cnt; i++) {
      threads[i].join();
    }
  }

  static class Printer extends Thread {
    private int i;
    private char c;
    //    private Integer flag;
    private Object lock; // 三个变量通过lock 来同步，每次只有一个线程工作，其余的 wait()

    public Printer(int i, Integer flag, Object lock) {
      this.i = i;
      this.c = (char) ('A' + i);
      //      this.flag = flag;
      this.lock = lock;
    }

    @Override public void run() {
      for (int j = 0; j < 10; j++) {
        doPrint();
      }
    }
    
    public void doPrint() {
      synchronized (lock) { // 后面要调用 notify 方法，必须对 notify owner 加锁
        // 1. 等待执行，实际上只有Active task一直持有锁，其他的线程，看一下，不满足条件后就 sleep 了
        while (flag != i) { // 状态不满足，一直加锁
          try {
            lock.wait();
          } catch (InterruptedException e) {
          }
        }
        // 2. 工作
        System.out.print(c);

        // 3. 更新状态, 更新的时候需要锁住状态
        flag = (flag + 1) % cnt;

        // 3. 唤醒所有等待任务，其中满足条件的任务执行，其余的继续wait
        lock.notifyAll();
      }
    }
  }
}


