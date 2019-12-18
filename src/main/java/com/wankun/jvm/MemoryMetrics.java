package com.wankun.jvm;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemoryMetrics {

  private static final Pattern PROCFS_STAT_FILE_FORMAT = Pattern.compile(
      "^([\\d-]+)\\s\\((.*)\\)\\s[^\\s]\\s([\\d-]+)\\s([\\d-]+)\\s" +
          "([\\d-]+)\\s([\\d-]+\\s){7}(\\d+)\\s(\\d+)\\s([\\d-]+\\s){7}(\\d+)\\s" +
          "(\\d+)(\\s[\\d-]+){15}");

  public static String getPid() {
    String name = ManagementFactory.getRuntimeMXBean().getName();
    String pid = name.split("@")[0];
    return pid;
  }

  public static void main(String[] args) throws InterruptedException, IOException {
    int[] a = new int[10240];
    // jvm-profiler 读取的文件，读取后作为Map分析
    System.out.println("===================   /proc/self/status ");
    for (String s : Files.readAllLines(Paths.get("/proc/self/status"))) {
      System.out.println(s);
    }

    // 用于测试 self 下的文件，其实只是对应pid下的文件的一个引用
    String pid = getPid();
    System.out.println("===================   /proc/" + pid + "/status ");
    for (String s : Files.readAllLines(Paths.get("/proc/" + pid + "/status"))) {
      System.out.println(s);
    }

    // hadoop-2.6.5
    // yarn / mapreduce1 ProcfsBasedProcessTree 需要读取其他进程的内存使用信息，并通过正则表达式来解析
    // vsize 虚拟内存，单位: byte，对应上面的 VmSize
    // rssmemPage 物理内存，单位PAGE，对应上面的 VmRSS， 在yarn跟踪内存使用的使用，自动 * 系统 PAGESIZE
    System.out.println("===================   /proc/" + pid + "/stat ");
    for (String s : Files.readAllLines(Paths.get("/proc/" + pid + "/stat"))) {
      Matcher m = PROCFS_STAT_FILE_FORMAT.matcher(s);
      boolean mat = m.find();
      if (mat) {
        String processName = "(" + m.group(2) + ")";
        // Set (name) (ppid) (pgrpId) (session) (utime) (stime) (vsize) (rss)
        System.out.println("name:" + processName);
        System.out.println("ppid:" + m.group(3));
        System.out.println("pgrpId:" + Integer.parseInt(m.group(4)));
        System.out.println("session:" + Integer.parseInt(m.group(5)));
        System.out.println("utime:" + Long.parseLong(m.group(7)));
        System.out.println("stime:" + new BigInteger(m.group(8)));
        System.out.println("vsize:" + Long.parseLong(m.group(10)));
        System.out.println("rssmemPage :" + Long.parseLong(m.group(11)));
        System.out.println("===================");
      }
      System.out.println(s);
    }

    // hadoop-2.6.5
    // LinuxResourceCalculatorPlugin 读取该文件，然后读取对应的内存使用信息 MemTotal 和 SwapTotal 分别为物理内存和虚拟内存的使用量
    System.out.println("===================   /proc/meminfo ");
    for (String s : Files.readAllLines(Paths.get("/proc/meminfo"))) {
      System.out.println(s);
    }

    // 输出结果和JVM 系统统计对比
    System.out.println("=================== Heap Usaging");
    Runtime runtime = Runtime.getRuntime();
    System.out.println("Used Memory:"
        + (runtime.totalMemory() - runtime.freeMemory()));
    System.out.println("Free Memory:" + runtime.freeMemory());
    System.out.println("Total Memory:" + runtime.totalMemory());
    System.out.println("Max Memory:" + runtime.maxMemory());

    // 输出结果和JVM Metrics对比
    MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    System.out.println("\nHeap Usage:");
    MemoryUsage usage = memoryMXBean.getHeapMemoryUsage();
    System.out.println("INT HEAP:" + usage.getInit()/1024 + "kB");
    System.out.println("MAX HEAP:" + usage.getMax()/1024 + "kB");
    System.out.println("USED HEAP:" + usage.getUsed()/1024 + "kB");
    System.out.println("COMMITTED HEAP:" + usage.getCommitted());

    System.out.println("\nNon Heap Usage:");
    MemoryUsage nonUsage = memoryMXBean.getNonHeapMemoryUsage();
    System.out.println("INT NON HEAP:" + nonUsage.getInit());
    System.out.println("MAX NON HEAP:" + nonUsage.getMax());
    System.out.println("USED NON HEAP:" + nonUsage.getUsed());
    System.out.println("COMMITTED NON HEAP:" + nonUsage.getCommitted());

    System.out.println("\nFull Information:");
    System.out.println("Heap Memory Usage:" + memoryMXBean.getHeapMemoryUsage());
    System.out.println("Non-Heap Memory Usage:" + memoryMXBean.getNonHeapMemoryUsage());
  }
}
