package com.wankun.jvm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import sun.misc.Signal;
import sun.misc.SignalHandler;
import sun.misc.Unsafe;

public class TestUnsafeOOM {

  private static final Unsafe _UNSAFE;

  public static final int BOOLEAN_ARRAY_OFFSET;

  public static final int BYTE_ARRAY_OFFSET;

  public static final int SHORT_ARRAY_OFFSET;

  public static final int INT_ARRAY_OFFSET;

  public static final int LONG_ARRAY_OFFSET;

  public static final int FLOAT_ARRAY_OFFSET;

  public static final int DOUBLE_ARRAY_OFFSET;

  static {
    sun.misc.Unsafe unsafe;
    try {
      Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
      unsafeField.setAccessible(true);
      unsafe = (sun.misc.Unsafe) unsafeField.get(null);
    } catch (Throwable cause) {
      unsafe = null;
    }
    _UNSAFE = unsafe;
    System.out.println("Unsafe = " + _UNSAFE);

    if (_UNSAFE != null) {
      BOOLEAN_ARRAY_OFFSET = _UNSAFE.arrayBaseOffset(boolean[].class);
      BYTE_ARRAY_OFFSET = _UNSAFE.arrayBaseOffset(byte[].class);
      SHORT_ARRAY_OFFSET = _UNSAFE.arrayBaseOffset(short[].class);
      INT_ARRAY_OFFSET = _UNSAFE.arrayBaseOffset(int[].class);
      LONG_ARRAY_OFFSET = _UNSAFE.arrayBaseOffset(long[].class);
      FLOAT_ARRAY_OFFSET = _UNSAFE.arrayBaseOffset(float[].class);
      DOUBLE_ARRAY_OFFSET = _UNSAFE.arrayBaseOffset(double[].class);
    } else {
      BOOLEAN_ARRAY_OFFSET = 0;
      BYTE_ARRAY_OFFSET = 0;
      SHORT_ARRAY_OFFSET = 0;
      INT_ARRAY_OFFSET = 0;
      LONG_ARRAY_OFFSET = 0;
      FLOAT_ARRAY_OFFSET = 0;
      DOUBLE_ARRAY_OFFSET = 0;
    }
  }

  // Same as jemalloc's debug fill values.
  public static byte MEMORY_DEBUG_FILL_CLEAN_VALUE = (byte) 0xa5;
  public static byte MEMORY_DEBUG_FILL_FREED_VALUE = (byte) 0x5a;

  void run() throws InterruptedException {
      new ActionHandler(new Signal("TERM"));
      new ActionHandler(new Signal("HUP"));
      new ActionHandler(new Signal("INT"));

    long size = 100 * 1024 * 1024;
    List<MemoryBlock> list = new ArrayList<>();
    try {
      int i = 0;
      while (true) {
        int numWords = (int) ((size + 7) / 8);
        long[] array = new long[numWords];
        System.out.println("worked....");
        MemoryBlock block = new MemoryBlock(array, LONG_ARRAY_OFFSET, size);
        list.add(block);
        _UNSAFE.setMemory(array, LONG_ARRAY_OFFSET, size, MEMORY_DEBUG_FILL_CLEAN_VALUE);
        System.out.println("Loop i = " + (i++) + "  memoryblock = " + block);
        Thread.sleep(500L);
      }
    } catch (OutOfMemoryError e) {
      System.out.println("Got OOM error");
    }

    int j = 0;
    while (true) {
      System.out.println("Continue work after OOM... j = " + (j++));
      Thread.sleep(500L);
    }
  }

  public static void main(String[] args) throws InterruptedException {
    new TestUnsafeOOM().run();
  }
}

class ActionHandler implements SignalHandler {

    private final Signal signal;
    private final SignalHandler prevHandler;

    public ActionHandler(Signal signal) {
        this.signal = signal;
        this.prevHandler = Signal.handle(signal, this);

    }

    @Override
    public void handle(Signal sig) {
        Signal.handle(sig, prevHandler);
        System.out.println("RECEIVED SIGNAL " + signal.getName());
        prevHandler.handle(sig);
        Signal.handle(sig, this);
    }
}

class MemoryBlock {
  Object obj;
  long offset;
  long length;

  public MemoryBlock(Object obj, long offset, long length) {
    this.obj = obj;
    this.offset = offset;
    this.length = length;
  }

  @Override
  public String toString() {
    return "com.wankun.jvm.MemoryBlock{" +
        "obj=" + obj +
        ", offset=" + offset +
        ", length=" + length +
        '}';
  }
}