package com.wankun.nio;

import org.junit.Assert;
import org.junit.Test;

import java.nio.IntBuffer;
import java.util.Arrays;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-02-11.
 */
public class IntBufferTetst {

  @Test
  public void testIntBuffer() {
    int BUFFER_SIZE = 5;
    IntBuffer buffer = IntBuffer.allocate(BUFFER_SIZE);
    for (int i = 0; i < BUFFER_SIZE; i++) {
      buffer.put(i * 2);
    }

    buffer.flip();
    Integer[] res = new Integer[BUFFER_SIZE];
    int i = 0;
    while (buffer.hasRemaining()) {
      res[i++] = buffer.get();
    }
    Integer[] expected = {0, 2, 4, 6, 8};
    Assert.assertArrayEquals(expected, res);
  }

  @Test
  public void testIntBuffer2() {
    int BUFFER_SIZE = 5;
    IntBuffer buffer = IntBuffer.allocate(BUFFER_SIZE);
    for (int i = 0; i < BUFFER_SIZE; i++) {
      buffer.put(i * 2);
    }

    buffer.flip();
    buffer.position(1);
    buffer.limit(3);
    Integer[] actual = new Integer[2];
    int i = 0;
    while (buffer.hasRemaining()) {
      actual[i++] = buffer.get();
    }
    Integer[] expected = {2, 4};
    System.out.println(Arrays.toString(actual));
    System.out.println(Arrays.toString(expected));

    Assert.assertArrayEquals(expected, actual);
  }

  @Test
  public void testIntBuffer3() {
    Integer[] expected = {0, 1, 2};
    IntBuffer buffer = IntBuffer.wrap(new int[]{0, 1, 2});

    // wrap 出来的buffer，必须要设置好limit，才能读出数据
    buffer.limit(expected.length);
    Integer[] res = new Integer[expected.length];
    int i = 0;
    while (buffer.hasRemaining()) {
      res[i++] = buffer.get();
    }

    Assert.assertArrayEquals(expected, res);
  }
}
