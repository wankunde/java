package com.wankun.bitmap;

/**
 * BitMap ：使用场景大量数据中快速判断是否存在，重复
 * 
 * 8bit是1byte,1024byte是1kb,1024kb是1mb，所以10000000个bit占用的空间为，10000000/8/1024/1024
 * mb大概为1mb多些，
 * 
 * 原文：http://blog.csdn.net/yaoweijq/article/details/5982265
 * 
 * @author wankun
 * @date 2014年9月17日
 * @version 1.0
 */
public class BitMapTest {

	public static void main(String[] args) {
		// 创建一个具有10000000位的bitset　初始所有位的值为false
		java.util.BitSet bitSet = new java.util.BitSet(10000000);
		// 将指定位的值设为true
		bitSet.set(9999, true);
		// 输出指定位的值
		System.out.println(bitSet.get(9999));
		System.out.println(bitSet.get(9998));
	}
}
