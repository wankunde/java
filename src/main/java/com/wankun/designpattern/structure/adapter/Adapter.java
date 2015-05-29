package com.wankun.designpattern.structure.adapter;

/**
 * @author wankun
 */
public class Adapter implements Target {

	private Adaptee adaptee;

	public Adapter(Adaptee adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void adapteeMethod() {
		adaptee.adapteeMethod();
	}

	@Override
	public void adapterMethod() {
		System.out.println("Adapter method!");
	}

}
