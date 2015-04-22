package com.wankun.java8;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

import com.wankun.java8.People.Sex;

/**
 * 
 * 方法引用Person::compareByAge跟lambda表达式(a, b) -> Person.compareByAge(a,b)在语义上是等价的。
 * 两者有如下的特征 ： · 形参相同 ，都是(Person, Person)。 · 调用方法Person.compareByAge。
 * 
 * @author kunwan
 *
 */
public class MethodReferrance {

	public static void main(String[] args) {
		// 1. 类的静态方法调用，条件：静态方法，且方法参数相同
		People p1 = new People("张三", LocalDate.of(1988, 6, 8), Sex.MALE, "aa@163.com");
		People p2 = new People("李四", LocalDate.of(1989, 9, 20), Sex.FEMALE, "bb@163.com");
		People[] peoples = new People[2];
		peoples[0] = p1;
		peoples[1] = p2;
		Arrays.sort(peoples, People::compareByAge);

		// 2. 对象方法引用，上面的引申，如果不是静态方法，可以声明出对象来调用
		People pp = new People();
		Arrays.sort(peoples, pp::compareByName);

		// 3. 特定类的任意对象的方法引用 == p1.compareByEmail(p2)
		Arrays.sort(peoples, People::compareByEmail);

		// 4. 构造方法使用
		// create方法参数为Supplier<People> supplier,这里传入构造方法
		People pp2 = People.create(People::new);

		// 这个方式我是这么理解的，People::new 代表构造方法，
		// Java编译器会自动根据PeopleFactory.create方法的签名来选择合适的构造函数
		PeopleFactory<People> factory = People::new;
		People pp3 = factory.create("李四", LocalDate.of(1989, 9, 20), Sex.FEMALE, "bb@163.com");

	}

	public static <T, SOURCE extends Collection<T>, DEST extends Collection<T>> DEST transferElements(SOURCE sourceCollection,
			Supplier<DEST> collectionFactory) {
		DEST result = collectionFactory.get();
		for (T t : sourceCollection) {
			result.add(t);
		}
		return result;
	}

}

interface PeopleFactory<P extends People> {
	P create(String name, LocalDate birthday, Sex gender, String email);
}

class People {

	public enum Sex {
		MALE, FEMALE
	}

	String name;
	LocalDate birthday;
	Sex gender;
	String email;

	public People() {
	}

	public static People create(final Supplier<People> supplier) {
		return supplier.get();
	}

	public People(String name, LocalDate birthday, Sex gender, String email) {
		this.name = name;
		this.birthday = birthday;
		this.gender = gender;
		this.email = email;
	}

	public int getAge() {
		return LocalDate.now().getYear() - birthday.getYear();
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public static int compareByAge(People a, People b) {
		return a.birthday.compareTo(b.birthday);
	}

	public int compareByName(People a, People b) {
		return a.name.compareTo(b.name);
	}

	public int compareByEmail(People p) {
		return email.compareTo(p.email);
	}
}
