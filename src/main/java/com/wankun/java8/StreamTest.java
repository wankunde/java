package com.wankun.java8;

import java.util.Arrays;
import java.util.List;
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
		
	}
}
