package com.wankun.designpattern.structure.bridge;

/**
 * @author wankun
 */
public abstract class Person {
	private Clothing clothing;

	private String name;

	public Person(String name) {
		this.name = name;
	}

	public Clothing getClothing() {
		return clothing;
	}

	public void setClothing(Clothing clothing) {
		this.clothing = clothing;
	}

	public void getStatus() {
		System.out.println(name + " is dressing " + clothing);
	}

}

class Man extends Person {
	public Man() {
		super("男人");
	}
}

class Lady extends Person {
	public Lady() {
		super("女人");
	}
}
