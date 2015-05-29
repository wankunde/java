package com.wankun.designpattern.create.AbstractFactory;

/**
 * @author wankun
 */
public interface IDog {
	void eat();
}

class BlackDog implements IDog {
	@Override
	public void eat() {
		System.out.println("The black dog is eating");
	}

}

class WhiteDog implements IDog {
	@Override
	public void eat() {
		System.out.println("The white dog is eating");
	}
}
