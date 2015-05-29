package com.wankun.designpattern.structure.bridge;

/**
 * @author wankun
 */
public class Clothing {
	private String type;

	public Clothing(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
}

class Jacket extends Clothing {
	public Jacket() {
		super("马甲");
	}
}

class Trouser extends Clothing {
	public Trouser() {
		super("裤子");
	}
}