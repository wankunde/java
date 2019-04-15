package com.wankun.java8.day2;

import java.util.function.Function;

public class TestFunction {


  public static void main(String[] args) {
    // JDK8以前，通过匿名内部类可以实现接口
    Function<Integer, String> fun1 = new Function<Integer, String>() {
      @Override
      public String apply(Integer t) {
        return String.valueOf(t);
      }
    };

    //JDK8中，通过lambda表达式实现
    Function<Integer, String> fun2 = (x) -> String.valueOf(x);

    System.out.println(fun1.apply(50));
    System.out.println(fun2.apply(100));
  }


}
