package com.wankun.java8;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Java8MapTest {

    @Test
    public void testMapPut() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "A");
        map.put("b", "B");
        String v = map.put("b", "v"); // 输出 B
        System.out.println(v);
        String v1 = map.put("c", "v");
        System.out.println(v1); // 输出：NULL
    }

    @Test
    public void testMapCompute() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "A");
        map.put("b", "B");
        String val = map.compute("b", (k, v) -> "v"); // 输出 v
        System.out.println(val);
        String v1 = map.compute("c", (k, v) -> "v"); // 输出 v
        System.out.println(v1);
    }

    public String preparePutValue() {
        System.out.println("do prepare function regardless of whether the key exists");
        return "v";
    }

    @Test
    public void testMapPutIfAbsent() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "A");
        map.put("b", "B");
        String v = map.putIfAbsent("b", preparePutValue());  // 输出 B

        System.out.println(v);
        String v1 = map.putIfAbsent("c", preparePutValue());  // 输出 null
        System.out.println(v1);
    }

    @Test
    public void testMapComputeIfAbsent() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "A");
        map.put("b", "B");
        String v = map.computeIfAbsent("b", k -> {
            System.out.println("key exists, skip compute...");
            return "v";
        });  // 输出 B
        System.out.println(v);
        String v1 = map.computeIfAbsent("c", k -> {
            System.out.println("key not exists, do compute...");
            return "v";
        }); // 输出 v
        System.out.println(v1);
    }

}
