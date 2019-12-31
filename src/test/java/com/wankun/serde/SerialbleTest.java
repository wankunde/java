package com.wankun.serde;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2019-12-31.
 */

import org.junit.Test;

import java.io.*;

/**
 * @author 魏文思
 * @date 2019/11/11$ 10:59$
 */
public class SerialbleTest {

  @Test
  public void testOut03() throws Exception
  {
    People p = new People(2,"小白");
    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("temp01.txt"));
    out.writeObject(p);
    out.flush();
    out.close();
  }

  @Test
  public void testIn03() throws Exception
  {
    ObjectInputStream in = new ObjectInputStream(new FileInputStream("temp01.txt"));
    Kong k = (Kong)in.readObject();
    in.close();
    System.out.println(k.s);
  }

}
