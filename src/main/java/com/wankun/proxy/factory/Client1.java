package com.wankun.proxy.factory;

public class Client1 {

	public static void main(String[] args) {
		Subject proxy = SubjectStaticFactory.getInstance();
		proxy.dealTask("DBQueryTask");
	}

}