package com.wankun.java8.day2;

import java.util.Optional;

public class TestOption {
  public static void main(String[] args) {
    Optional<String> optional = Optional.of("test java 1.8 optional");
    boolean b = optional.isPresent();           // true
    String v = optional.get();                 // "test java 1.8 optional"
    String v2 = optional.orElse("test java 1.8 else");    // "test java 1.8 optional"
    System.out.println(b);
    System.out.println(v);
    System.out.println(v2);
    System.out.println(Optional.empty().orElse("empty else--"));

    optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "t"

  }
}
