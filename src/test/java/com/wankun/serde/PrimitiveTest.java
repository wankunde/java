package com.wankun.serde;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-01-07.
 */
public class PrimitiveTest {

  @Test
  public void testPrimitive() {
    int i = 2;
    i.getClass()
    assertTrue(int.class.isPrimitive());

    Integer i2 = Integer.valueOf(2);
    assertFalse(i2.getClass().isPrimitive());

    PrimitiveTest o = new PrimitiveTest();
    assertFalse(o.getClass().isPrimitive());
  }

}
