package com.wankun.java8;

public class LambdaScope {

	public static void main(String[] args) {
		localVariable();
		new LambdaScope().instanceVariable();
	}

	/**
	 * 局部变量可以不用加final,但是num还是不能修改的，重新赋值会报错
	 */
	public static void localVariable() {
		int num = 1;
		// final int num = 1;
		Converter<Integer, String> stringConverter = (from) -> String.valueOf(from + num);
		System.out.println(stringConverter.convert(2));
		// num = 3;
	}

	static int outerStaticNum;
	int outerNum;

	/**
	 * 在访问实例变量和静态变量的时候，可以改变数据值
	 */
	public void instanceVariable() {
		Converter<Integer, String> stringConverter1 = (from) -> {
			outerNum = 23;
			return String.valueOf(from);
		};
		Converter<Integer, String> stringConverter2 = (from) -> {
			outerStaticNum = 72;
			return String.valueOf(from);
		};

		System.out.println(stringConverter1.convert(11));
		System.out.println(stringConverter2.convert(12));
	}

}
