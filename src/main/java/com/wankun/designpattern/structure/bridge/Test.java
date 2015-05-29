package com.wankun.designpattern.structure.bridge;

/**
 * @author wankun
 */
public class Test {

	public static void main(String[] args) {
		Person man = new Man();
		Person lady = new Lady();
		Clothing jacket = new Jacket();
		Clothing trouser = new Trouser();

		man.setClothing(jacket);
		man.getStatus();
		man.setClothing(trouser);
		man.getStatus();
		lady.setClothing(jacket);
		lady.getStatus();
		lady.setClothing(trouser);
		lady.getStatus();
	}
}
