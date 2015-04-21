package com.wankun.annotation.java8;

public class TestAnnotation {

	public static void main(String[] args) {
		Hint hint = NewPeople.class.getAnnotation(Hint.class);
		System.out.println(hint); // null
		Hints hints1 = NewPeople.class.getAnnotation(Hints.class);
		System.out.println(hints1.value().length); // 2
		Hint[] hints2 = NewPeople.class.getAnnotationsByType(Hint.class);
		System.out.println(hints2.length); // 2
	}
}
