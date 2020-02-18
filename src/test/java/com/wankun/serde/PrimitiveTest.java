package com.wankun.serde;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.ObjectStreamClass;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-01-07.
 */
public class PrimitiveTest {

  @Test
  public void testPrimitive() {
    int i = 2;
    assertTrue(int.class.isPrimitive());

    Integer i2 = Integer.valueOf(2);
    assertFalse(i2.getClass().isPrimitive());

    PrimitiveTest o = new PrimitiveTest();
    assertFalse(o.getClass().isPrimitive());

    // 数组的类比较特殊
    PrimitiveTest[] arr = new PrimitiveTest[10];
    assertEquals(arr.getClass(), PrimitiveTest[].class);
    assertTrue(arr.getClass().isArray());
    assertEquals(arr.getClass().getComponentType(), PrimitiveTest.class);
    assertFalse(arr.getClass().getComponentType().isPrimitive());

    System.out.println(ObjectStreamClass.lookupAny(o.getClass()));
    System.out.println(ObjectStreamClass.lookupAny(arr.getClass()));

  }

}
