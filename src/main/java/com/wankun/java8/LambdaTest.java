package com.wankun.java8;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

	public static List<String> parse(Path path) throws Exception{
        return Files.lines(path)
                .parallel()
                .flatMap(line -> Arrays.asList(line.split("\\b")).stream())
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry<String, Long>::getValue).reversed())
                .limit(20)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
   }
	
	public static void main(String[] args) throws Exception {
		System.out.println(parse(Paths.get(args[0])));
		
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

		int count = 0;
		List strings = Arrays.asList("a", "b", "c");
		strings.forEach(s -> {
//			count++; // 错误：不能更改count的值
		});
		

	}
}
