package com.wankun.java8;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StreamTest {

	public static void main(String[] args) {
		// Stream 接口
		List<String> stringCollection = Arrays.asList("ddd2", "aaa2", "bbb1", "aaa1", "bbb3",
				"ccc", "bbb2", "ddd1");

		stringCollection.stream().filter((s) -> s.startsWith("b")).forEach(System.out::println);

		System.out.println("----- sort filter foreach");
		stringCollection.stream().sorted().filter((s) -> s.startsWith("b"))
				.forEach(System.out::println);

		System.out.println("-----");
		System.out.println(stringCollection);

		System.out.println("----- map sort ");
		stringCollection.stream().map(String::toUpperCase).sorted((a, b) -> b.compareTo(a))
				.forEach(System.out::println);

		System.out.println("----- Match 匹配 ");
		boolean anyStartsWithA = stringCollection.stream().anyMatch((s) -> s.startsWith("a"));
		System.out.println(anyStartsWithA); // true
		boolean allStartsWithA = stringCollection.stream().allMatch((s) -> s.startsWith("a"));
		System.out.println(allStartsWithA); // false
		boolean noneStartsWithZ = stringCollection.stream().noneMatch((s) -> s.startsWith("z"));
		System.out.println(noneStartsWithZ); // true

		System.out.println("----- Count 计数 ");
		long startsWithB = stringCollection.stream().filter((s) -> s.startsWith("b")).count();
		System.out.println(startsWithB); // 3

		System.out.println("----- Reduce 规约");
		Optional<String> reduced = stringCollection.stream().sorted()
				.reduce((s1, s2) -> s1 + "#" + s2);
		reduced.ifPresent(System.out::println);

		System.out.println("================  Map ==================");
		Map<Integer, String> map = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			map.putIfAbsent(i, "val" + i);
		}

		map.forEach((id, val) -> System.out.println(val));
		map.computeIfPresent(3, (num, val) -> val + num);
		System.out.println(map.get(3)); // val33
		map.computeIfPresent(9, (num, val) -> null);
		System.out.println(map.containsKey(9)); // false
		map.computeIfAbsent(23, num -> "val" + num);
		System.out.println(map.containsKey(23)); // true
		map.computeIfAbsent(3, num -> "bam");
		System.out.println(map.get(3)); // val33

		map.remove(3, "val3");
		System.out.println(map.get(3)); // val33
		map.remove(3, "val33");
		System.out.println(map.get(3)); // null

		map.getOrDefault(42, "not found"); // not found

		// Merge做的事情是如果键名不存在则插入，否则则对原键对应的值做合并操作并重新插入到map中。
		map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
		map.get(9); // val9
		map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
		map.get(9); // val9concat
	}
}
