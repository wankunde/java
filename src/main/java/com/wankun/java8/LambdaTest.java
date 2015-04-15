package com.wankun.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 函数式接口：仅仅只包含一个抽象方法的接口
 * 
 * @author kunwan
 *
 * @param <F>
 * @param <T>
 */
@FunctionalInterface
interface Converter<F, T> {
	T convert(F from);
}

public class LambdaTest {

	public static void main(String[] args) {
		// lambda 表达式理解：当作任意只包含一个抽象方法的接口类型
		Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
		// 新的静态方法引用
		// Converter<String, Integer> converter = Integer::valueOf;

		Integer converted = converter.convert("123");
		System.out.println(converted); // 123

		// 传统方式排序
		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
		Collections.sort(names, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return b.compareTo(a);
			}
		});
		System.out.println(names.toString());

		// lambda 排序
		names = Arrays.asList("peter", "anna", "mike", "xenia");
		// Collections.sort(names, (a, b) -> -b.compareTo(a)); // 和上面区别，做个反排序
		// Collections.sort(names, (String a, String b) -> -b.compareTo(a));
		Collections.sort(names, (String a, String b) -> {
			return -b.compareTo(a);
		});
		System.out.println(names.toString());

	}
}
