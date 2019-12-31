package com.wankun.serde;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2019-12-31.
 */
public class Kong implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -7144694309484327560L;

  public String s;

  Kong(String s) {
    this.s = s;
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
    System.out.println("出");
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    System.out.println("入");
  }

}
