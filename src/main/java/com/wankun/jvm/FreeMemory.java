package com.wankun.jvm;

import java.awt.Button;

public class FreeMemory {

	public static void main(String[] args) {
		Runtime runtime = Runtime.getRuntime();
		System.out.println("free memory : " + runtime.freeMemory() / 1024);

		Button[] btns = new Button[1000];
		for (int i = 0; i < 1000; i++)
			btns[i] = new Button("button " + i);

		System.out.println("free memory : " + runtime.freeMemory() / 1024);

		System.gc();

		System.out.println("free memory : " + runtime.freeMemory() / 1024);

		btns = null;
		System.gc();

		System.out.println("free memory : " + runtime.freeMemory() / 1024);
	}
}
