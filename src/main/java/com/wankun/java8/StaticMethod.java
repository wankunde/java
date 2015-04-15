package com.wankun.java8;

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

interface PersonFactory<P extends Person> {
	P create(String firstName, String lastName);
}

public class StaticMethod {

	public static void main(String[] args) {
		// 使用 Person::new 来获取Person类构造函数的引用，
		// Java编译器会自动根据PersonFactory.create方法的签名来选择合适的构造函数
		PersonFactory<Person> personFactory = Person::new;
		Person person = personFactory.create("Peter", "Parker");

		System.out.println(person);
	}
}
