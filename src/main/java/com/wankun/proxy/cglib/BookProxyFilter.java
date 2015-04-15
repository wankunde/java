package com.wankun.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

public class BookProxyFilter implements CallbackFilter {

	@Override
	public int accept(Method method) {
		if (!method.getName().toLowerCase().startsWith("query"))
			return 0;
		return 1;
	}

}
