package com.wankun.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class BookProxy implements MethodInterceptor {
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
			throws Throwable {
		System.out.println("事物开始");
		proxy.invokeSuper(obj, args);
		System.out.println("事物结束");
		return null;
	}
}
