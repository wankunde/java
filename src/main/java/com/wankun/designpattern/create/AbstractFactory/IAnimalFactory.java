package com.wankun.designpattern.create.AbstractFactory;

/**
 * @author wankun
 */
public interface IAnimalFactory {
	ICat createCat();

	IDog createDog();

}

class BlackAnimalFactory implements IAnimalFactory {

	public ICat createCat() {
		return new BlackCat();
	}

	public IDog createDog() {
		return new BlackDog();
	}

}

class WhiteAnimalFactory implements IAnimalFactory {

	public ICat createCat() {
		return new WhiteCat();
	}

	public IDog createDog() {
		return new WhiteDog();
	}

}
