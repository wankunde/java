package com.wankun.testclass;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2021-04-12.
 */
public class Main {
  public static void main(String[] args) {
    Person p = new Student();
    System.out.println(p.getI()); //
    System.out.println(p.getsum());
    System.out.println(p.getsum2());
  }
}

class Person {
  int i = 10;

  public int getI() {
    return i;
  }

  public int getsum() {
    return i + 10;
  }

  public int getsum2() {
    return getI() + 10;
  }
}

class Student extends Person {
  int i = 20;

  public int getI() {
    return i;
  }
}
