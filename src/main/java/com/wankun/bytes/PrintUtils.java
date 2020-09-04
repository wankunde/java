package com.wankun.bytes;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-09-04.
 */
public class PrintUtils {

  public static String numberString(Number n, Short length) {
    long f = 0x01;
    char[] cs = new char[length + (length - 1) / 8];
    int j = cs.length - 1;
    for (int i = 0; i < length; i++) {
      if (i % 8 == 0 && i > 0) {
        cs[j--] = ' ';
      }
      if ((n.longValue() & f) > 0) {
        cs[j--] = '1';
      } else {
        cs[j--] = '0';
      }
      f = f << 1;
    }
    return new String(cs);
  }

  public static String byteString(byte b) {
    return numberString(b, (short) 8);
  }

  public static String shortString(Short s) {
    return numberString(s, (short) 16);
  }

  public static String intString(Integer s) {
    return numberString(s, (short) 32);
  }

  public static String longString(Long s) {
    return numberString(s, (short) 64);
  }


  public static void main(String[] args) {
    System.out.println(byteString((byte) 0xff));
    System.out.println(shortString((short) 10));
    System.out.println(shortString((short) -10));
    System.out.println(intString( 10));
    System.out.println(intString( -10));
  }
}
