package com.wankun.concurrent;

import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierPrinter {

  public static final int cnt = 3;

  public static CyclicBarrier barrier = new CyclicBarrier(cnt, () ->{
    System.out.println("全部打印完成");
  });

  public static void main(String[] args) {

    File[] files = new File[cnt];
    for (int i = 0; i < cnt; i++) {
      files[i] = new File("file" + i + ".txt");
    }
    ExecutorService executor = Executors.newFixedThreadPool(cnt);
    for (int i = 0; i < cnt; i++) {
      executor.execute(new Printer(i, files));
    }
    executor.shutdown();
  }

  static class Printer extends Thread {
    private int i = 0;
    private char c;
    private File[] files;

    public Printer(int i, File[] files) {
      super("Printer-" + i);
      this.i = i;
      this.c = (char) ('A' + i);
      this.files = files;
    }

    @Override public void run() {
      for (int j = 0; j < 10; j++) {
        int k = (cnt + i - j % cnt) % cnt;
        doPrint(files[k]);
      }
    }

    public void doPrint(File file) {
      try (FileWriter writer = new FileWriter(file, true)) {
        writer.append(c);
        System.out.println(getName() + " begin await()");
        barrier.await(); // 这里当有 CNT 个现在在这里 await 的时候，自动全部唤醒，继续执行，不需要额外的操作
        System.out.println(getName() + " notified");
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
