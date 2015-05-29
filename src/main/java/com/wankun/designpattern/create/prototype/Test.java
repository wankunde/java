package com.wankun.designpattern.create.prototype;

/**
 * @author wankun
 */
public class Test {

	public static void main(String[] args) {
		Prototype pro = new ConcretePrototype("prototype");
		Prototype pro2 = (Prototype) pro.clone();
		System.out.println(pro.getName());
		System.out.println(pro2.getName());

		System.out.println(pro);
		System.out.println(pro2);
	}

}
