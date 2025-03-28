package com.wankun.jvm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestNewOOM {
  // Split java.version on non-digit chars:
  private static final int majorVersion =
      Integer.parseInt(System.getProperty("java.version").split("\\D+")[0]);

  // Access fields and constructors once and store them, for performance:
  private static final Constructor<?> DBB_CONSTRUCTOR;
  private static final Field DBB_CLEANER_FIELD;
  private static final Method CLEANER_CREATE_METHOD;

  static {
    // At the end of this block, CLEANER_CREATE_METHOD should be non-null iff it's possible to use
    // reflection to invoke it, which is not necessarily possible by default in Java 9+.
    // Code below can test for null to see whether to use it.

    try {
      Class<?> cls = Class.forName("java.nio.DirectByteBuffer");
      Constructor<?> constructor = (majorVersion < 21) ?
          cls.getDeclaredConstructor(Long.TYPE, Integer.TYPE) :
          cls.getDeclaredConstructor(Long.TYPE, Long.TYPE);
      Field cleanerField = cls.getDeclaredField("cleaner");
      try {
        constructor.setAccessible(true);
        cleanerField.setAccessible(true);
      } catch (RuntimeException re) {
        // This is a Java 9+ exception, so needs to be handled without importing it
        if ("InaccessibleObjectException".equals(re.getClass().getSimpleName())) {
          // Continue, but the constructor/field are not available
          // See comment below for more context
          constructor = null;
          cleanerField = null;
        } else {
          throw re;
        }
      }
/*
      if (!constructor.trySetAccessible()) {
        constructor = null;
      }
      if (!cleanerField.trySetAccessible()) {
        cleanerField = null;
      }*/
      // Have to set these values no matter what:
      DBB_CONSTRUCTOR = constructor;
      DBB_CLEANER_FIELD = cleanerField;

      System.out.println("DBB_CONSTRUCTOR = " + DBB_CONSTRUCTOR);
      System.out.println("DBB_CLEANER_FIELD = " + DBB_CLEANER_FIELD);
      // no point continuing if the above failed:
      if (DBB_CONSTRUCTOR != null && DBB_CLEANER_FIELD != null) {
        Class<?> cleanerClass = Class.forName("jdk.internal.ref.Cleaner");
        Method createMethod = cleanerClass.getMethod("create", Object.class, Runnable.class);
        // Accessing jdk.internal.ref.Cleaner should actually fail by default in JDK 9+,
        // unfortunately, unless the user has allowed access with something like
        // --add-opens java.base/jdk.internal.ref=ALL-UNNAMED  If not, we can't use the Cleaner
        // hack below. It doesn't break, just means the user might run into the default JVM limit
        // on off-heap memory and increase it or set the flag above. This tests whether it's
        // available:
        try {
          createMethod.invoke(null, null, null);
        } catch (IllegalAccessException e) {
          // Don't throw an exception, but can't log here?
          createMethod = null;
        }
        CLEANER_CREATE_METHOD = createMethod;
        System.out.println("CLEANER_CREATE_METHOD = " + CLEANER_CREATE_METHOD);
      } else {
        CLEANER_CREATE_METHOD = null;
      }
    } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException e) {
      // These are all fatal in any Java version - rethrow (have to wrap as this is a static block)
      throw new IllegalStateException(e);
    } catch (InvocationTargetException ite) {
      throw new IllegalStateException(ite.getCause());
    }
  }

  public static void main(String[] args) throws Exception {
    List<Object> leak = new LinkedList<>();
    try {
      int i = 0;
      while (true) {
        leak.add(new byte[1024 * 1024 * 100]);
        System.out.println("i=" + i++);
        Thread.sleep(500L);
      }
    } catch (OutOfMemoryError e) {
      System.out.println("Got OutOfMemoryError");
    }

    int j = 0;
    while (true) {
      System.out.println("Continue work after OOM... j = " + (j++));
      Thread.sleep(500L);
    }
  }
}