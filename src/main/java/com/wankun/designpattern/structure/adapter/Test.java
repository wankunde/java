package com.wankun.designpattern.structure.adapter;

/**
 * @author wankun
 */
public class Test {

	public static void main(String[] args) {
		Target target = new Adapter(new Adaptee());
		target.adapteeMethod();
		target.adapterMethod();
	}

}
