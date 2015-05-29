package com.wankun.designpattern.create.builder;

/**
 * @author wankun
 */
public class PersonDirector {
	public Person constructPerson(PersonBuilder pb) {
		pb.buildHead();
		pb.buildBody();
		pb.buildFoot();
		return pb.buildPerson();
	}
}
