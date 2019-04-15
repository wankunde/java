package com.wankun.java8.day2;

import java.util.function.Supplier;

public class TestSupplier {
  private int age;

  TestSupplier() {
    System.out.println("call constructor  " + age);
  }

  public static void main(String[] args) {
    //创建Supplier容器，声明为TestSupplier类型，此时并不会调用对象的构造方法，即不会创建对象
    Supplier<TestSupplier> sup = TestSupplier::new;

    //get()方法调用构造方法，即获得到真正对象；多次调用，对象不同
    TestSupplier s1 = sup.get();
    System.out.println(s1);

    TestSupplier s2 = sup.get();
    System.out.println(s2);

//    所以该lambda表达式可以实现Supplier接口
    Supplier<String> sup2 = () -> "lambda supplier";
    System.out.println(sup2.get());
  }
}