package com.wankun.proxy.dynamic;

import com.wankun.proxy.factory.Subject;

public class Client {

	public static void main(String[] args) {

		Subject proxy = DynProxyFactory.getInstance();
		proxy.dealTask("DBQueryTask");
	}

}
