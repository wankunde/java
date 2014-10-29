package com.wankun.complement;

/**
 * @author wankun
 * @date 2014年10月20日
 * @version 1.0
 */
public class IntTest {

	public static void main(String[] args) {
		IntTest t = new IntTest();
		System.out.println("test1 -----------------------");
		t.test1();
		System.out.println("test2 -----------------------");
		t.test2();
		System.out.println("test3 -----------------------");
		t.test3();
		System.out.println("test4 -----------------------");
		t.test4();
	}

	private void test1() {
		// 计算补码
		System.out.println((byte) 130);
		int i = 6;
		System.out.println(~i);
		System.out.println((byte) 258);

		// 移位运算本身并不会改变数值,改变值可以使用 >>>= 运算符
		int t1 = -128;
		if (t1 >>> 2 == 0) {
		}
		System.out.println("t1:" + t1);

		// >>右移运算符，有符号。右边超出截掉，左边补上符号位
		// >>>右移运算符，无符号，左边补0
		byte a = -128;
		System.out.println(a);
		System.out.println(a >>> 2);
		// a = (byte) (a >>> 2);
		// System.out.println(a);

		// -128: 1000 0000
		// JAVA在对不足32位的数(byte,char,short)进行移位运算时候,都会先转成int(32位),所以此时a为11111111111111111111111110000000

		// 移位之后,(00)111111111111111111111111100000

		byte b = -128;
		b = (byte) (b >>> 2);
		System.out.println(b);
		// 但是你在此a=(byte)(a>>>2); //强制转成byte型,将对结果进行截断
		// 此时a为11100000,就是32了
	}

	private void test2() {
		int i = 170;
		System.out.println("Number = " + i);

		/*
		 * returns the string representation of the unsigned integer value
		 * represented by the argument in binary (base 2)
		 */
		System.out.println("Binary = " + Integer.toBinaryString(i));

		// returns the number of one-bits
		System.out.println("Number of one bits = " + Integer.bitCount(i));

		/*
		 * returns an int value with at most a single one-bit, in the position
		 * of the highest-order ("leftmost") one-bit in the specified int value
		 */
		System.out.println("Highest one bit = " + Integer.highestOneBit(i));

		/*
		 * returns an int value with at most a single one-bit, in the position
		 * of the lowest-order ("rightmost") one-bit in the specified int value.
		 */
		System.out.println("Lowest one bit = " + Integer.lowestOneBit(i));

		/*
		 * returns the number of zero bits preceding the highest-order
		 * ("leftmost")one-bit
		 */
		System.out.print("Number of leading zeros = ");
		System.out.println(Integer.numberOfLeadingZeros(i));

		System.out.println("int为32最高位前面0的个数：" + Integer.numberOfLeadingZeros(58));
		System.out.println("int为32最高位前面0的个数：" + Integer.numberOfTrailingZeros(58));
	}

	// 快速将指定bit位变为0或1
	private void test3() {
		int i = 170;
		System.out.println("Number = " + i + "  Binary = " + Integer.toBinaryString(i));
		i = i | 0x00000011;
		System.out.println("Number = " + i + "  Binary = " + Integer.toBinaryString(i));

	}

	/**
	 * 
	 * char:2字节 int：4字节
	 * 
	 * 这个是unsigned shifting用的办法
	 * java中的数值是int,所以0xFF是int,而byte是有符号数,int亦然,直接由byte升为int
	 * ,符号自动扩展,而进行了&0xFF后,就把符号问题忽略掉了 ,将byte以纯0/1地引用其内容,所以要0xFF,不是多馀的
	 */
	private void test4() {
		// -1 : 11111111111111111111111111111111
//		System.out.println(Integer.toBinaryString(255));
//		System.out.println(Integer.toBinaryString(65535));
		char c = (char) -1 & 0xFF;
		char d = (char) -1;
		System.out.println((int) c);
		System.out.println((int) d);

	}
}
