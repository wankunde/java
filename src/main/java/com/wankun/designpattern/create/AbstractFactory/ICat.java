package com.wankun.designpattern.create.AbstractFactory;
/** 
 * @author wankun
 */
public interface ICat {
	void eat();
}

class BlackCat implements ICat {
	@Override
	public void eat() {
		System.out.println("The black cat is eating!");
	}
}

class WhiteCat implements ICat {
	@Override
	public void eat() {
		System.out.println("The white cat is eating!");
	}

}