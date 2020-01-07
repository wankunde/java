package com.wankun.serde;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-01-07.
 */
public class PrimitiveTest {

  @Test
  public void test1() {
    System.out.println("ssdf");
  }

  @Test
  public void testPrimitive() {
    Integer i = Integer.valueOf(2);
    assertEquals(true, i.getClass().isPrimitive());

    PrimitiveTest o = new PrimitiveTest();
    assertEquals(true, o.getClass().isPrimitive());
  }

}
