package com.wankun.math3;

import org.apache.commons.math3.random.RandomDataGenerator;

public class RandomDataGeneratorTest {
	public static void main(String[] args) {
		RandomDataGenerator random = new RandomDataGenerator();
		for (int i = 0; i < 10; i++) {
			long value = random.nextLong(1, 100);
			System.out.println(value);
		}

		System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");

		for (int i = 0; i < 10; i++) {
			random = new RandomDataGenerator();
			long value = random.nextLong(1, 100);
			System.out.println(value);
		}
	}
	
}
