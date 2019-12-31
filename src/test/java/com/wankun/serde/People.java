package com.wankun.serde;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2019-12-31.
 */

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class People implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 2659082826995480601L;
  private int age;
  private String name;

  People(int age, String name) {
    this.age = age;
    this.name = name;
  }

  private void writeObject(ObjectOutputStream out) {
    System.out.println("是否调用了我？");
  }

  private void readObject(ObjectInputStream in) {
    System.out.println("是否调用了我？");
  }

  //在执行out.write()方法时会出发这个方法，先调用writeReplace
  // 如果没有writeReplace那么将会调用writeObject方法
  private Object writeReplace() {
    System.out.println("调用了 writeReplace()方法");
    return new Kong("路人");
  }

}