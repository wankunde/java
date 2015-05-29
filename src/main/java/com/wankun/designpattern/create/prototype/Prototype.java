package com.wankun.designpattern.create.prototype;

/**
 * @author wankun
 */
public class Prototype implements Cloneable {
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

class ConcretePrototype extends Prototype {
	public ConcretePrototype(String name) {
		setName(name);
	}

}
