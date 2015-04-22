package com.wankun.java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

class Person {
	String firstName;
	String lastName;

	Person() {
	}

	Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}

public class GuavaTest {

	public static void main(String[] args) {
		Predicate<String> predicate = (s) -> s.length() > 0;
		System.out.println(predicate.test("foo")); // true
		System.out.println(predicate.negate().test("foo")); // false

		Predicate<Boolean> nonNull = Objects::nonNull;
		GuavaTest t1 = null;
		// System.out.println(nonNull.);

		Predicate<Boolean> isNull = Objects::isNull;
		Predicate<String> isEmpty = String::isEmpty;
		Predicate<String> isNotEmpty = isEmpty.negate();

		// Function 接口
		// Function 的apply:传一个参数，返回一个值
		// 可以通过compose(先执行指定方法) 或 andThen对方法进行增强
		Function<Integer, Integer> before = a -> {
			System.out.println("before add..");
			return a;
		};

		Function<Integer, Integer> after = a -> {
			System.out.println("after add..");
			return a;
		};

		Function<Integer, Integer> fadd = a -> {
			System.out.println("adding..");
			return a + 2;
		};

		Function<Integer, Integer> business = fadd.compose(before).andThen(after);
		System.out.println(business.apply(5));

		// function test2
		Function<String, Integer> toInteger = Integer::valueOf;
		Function<String, String> backToString = toInteger.andThen(String::valueOf);
		backToString.apply("123"); // "123"

		// Supplier 接口
		Supplier<Person> personSupplier = Person::new;
		personSupplier.get(); // new Person

		// Consumer 接口
		Consumer<Person> greeter = (p) -> System.out.println("Hello, " + p.firstName);
		greeter.accept(new Person("Luke", "Skywalker"));

		// Comparator 接口
		Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);
		Person p1 = new Person("John", "Doe");
		Person p2 = new Person("Alice", "Wonderland");
		System.out.println(comparator.compare(p1, p2)); // > 0
		System.out.println(comparator.reversed().compare(p1, p2)); // < 0

		// Optional 接口
		Optional<String> optional = Optional.of("bam");
		optional.isPresent(); // true
		optional.get(); // "bam"
		optional.orElse("fallback"); // "bam"
		optional.ifPresent((s) -> System.out.println(s.charAt(0))); // "b"

	}
}
