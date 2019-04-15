package com.wankun.java8.day2;


import java.util.function.Consumer;

public class TestConsumer {
  public static void main(String[] args) {
    Consumer<Integer> consumer = (x) -> {
      int num = x * 2;
      System.out.println(num);
    };
    Consumer<Integer> consumer1 = (x) -> {
      int num = x * 3;
      System.out.println(num);
    };
    consumer.andThen(consumer1).accept(10);

    // 有参 lambda Function 也可以作为Consumer，来调用accept 方法
    Consumer<String> greeter = (s) -> System.out.println("String lenght = " + s.length());
    greeter.accept(new String("test java 1.8 consumer"));

  }
}