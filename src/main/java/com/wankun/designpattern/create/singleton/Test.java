package com.wankun.designpattern.create.singleton;

/**
 * @author wankun
 */
public class Test {

	public static void main(String[] args) {
		Singleton sing = Singleton.getInstance();
		Singleton sing2 = Singleton.getInstance();

		System.out.println(sing);
		System.out.println(sing2);
	}

}
