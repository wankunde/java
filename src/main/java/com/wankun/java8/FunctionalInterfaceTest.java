package com.wankun.java8;

/**
 * interface 允许带默认方法实现
 * 
 * @author kunwan
 *
 */
@FunctionalInterface
interface Formula {
	// 作为一个FunctionalInterface 只能有一个抽象方法，但是可以有多个默认实现方法
	double calculate(int a);

	// double calculate2(int a);

	default double sqrt(int a) {
		return Math.sqrt(a);
	}
}

public class FunctionalInterfaceTest {

	public static void main(String[] args) {
		Formula formula = new Formula() {
			@Override
			public double calculate(int a) {
				return sqrt(a * 100);
			}

			// sqrt方法可以重写，也可以不重写
		};
		System.out.println(formula.calculate(100)); // 100.0
		System.out.println(formula.sqrt(16)); // 4.0
	}
}
