package com.wankun.serde;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2019-12-31.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * @author 魏文思
 * @date 2019/11/11$ 10:59$
 */
public class SerialbleTest {

  File file;

  public void cleanTestFile() {
    file = new File(SerialbleTest.class.getResource("/").getPath(), "temp01.txt");
    if (file.exists()) {
      file.delete();
    }
  }

  @Before
  public void before() {
    cleanTestFile();
  }

  @After
  public void after() {
    cleanTestFile();
  }

  @Test
  public void testSerde() throws Exception {
    People p = new People(2, "小白");
    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
    out.writeObject(p);
    out.flush();
    out.close();

    ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
    Kong k = (Kong) in.readObject();
    in.close();
    System.out.println(k.s);
  }

}
