package com.wankun.designpattern.create.singleton;

/**
 * @author wankun
 */
public class Singleton {
	private static Singleton sing;

	private Singleton() {
	}

	public synchronized static Singleton getInstance() {
		if (sing == null) {
			sing = new Singleton();
		}
		return sing;
	}

}
