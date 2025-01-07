package com.wankun.concurrent;

import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.CountDownLatch;

public class CountDownPrint {

  final static int cnt = 3;
  static CountDownLatch latch = new CountDownLatch(cnt);

  public static void main(String[] args) throws InterruptedException {
    Thread[] threads = new Thread[cnt];
    File[] files = new File[cnt];
    for (int i = 0; i < cnt; i++) {
      files[i] = new File("file" + i + ".txt");
    }
    for (int i = cnt - 1; i >= 0; i--) {
      threads[i] = new PrintThread(i, files);
      threads[i].start();
    }
    for (Thread thread : threads) {
      thread.join();
    }
  }

  static class PrintThread extends Thread {
    private int i;
    private File[] files;
    private char c;

    public PrintThread(int i, File[] files) {
      super("Printer-" + i);
      this.i = i;
      c = (char) ('A' + i);
      this.files = files;
    }

    @Override public void run() {
      for (int k = 0; k < 10; k++) {
        int j = (cnt - k % cnt + i) % cnt; // target
        doPrint(files[j]);
      }
    }

    public void doPrint(File file) {
      // 这里也不需要对文件加锁
      System.out.println(getName() + " start, task queue size: " + latch.getCount());
      try (FileWriter writer = new FileWriter(file, true)) {
        writer.append(c);
        // 需要等待其他线程都工作完毕，才能进行下一轮
        // 1. 对 latch 加锁
        // 2. 存在未完成任务, wait() 等待，释放锁
        // 3. 全部完成， 唤醒所有等待线程(锁仍然未释放，所以他们还不能执行)，reset latch
        synchronized (latch) {
          latch.countDown(); // 更新变量
          System.out.println(getName() + " end");
          if (latch.getCount() != 0)
            latch.wait(); // 等待其他线程全部完成
          else {
            latch.notifyAll();
            latch = new CountDownLatch(cnt); // 只能最后一个线程来 reset latch
            System.out.println("ALL finished");
          }
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
