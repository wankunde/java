package com.wankun.java8;

import java.util.function.Function;

public class App {
	public static void main(String[] args) {
		Function<Integer, Integer> fn = x -> x + 2;
		System.out.println(fn.apply(2));
	}
}
