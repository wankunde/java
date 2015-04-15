package com.wankun.proxy.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * 使用cglib动态代理
 * 
 * @author student
 * 
 */
public class BookFactory {

	/**
	 * prototype 原型模式
	 * 
	 * @return
	 */
	public static BookBean getBookBean() {
		return new BookBean();
	}

	/**
	 * 创建代理对象 Enhancer 工具方法
	 * 
	 * Object create(Class type, Callback callback)
	 * 
	 * Object create(Class superclass, Class interfaces[], Callback callback)
	 * 
	 * Object create(Class superclass, Class[] interfaces, CallbackFilter
	 * filter, Callback[] callbacks)
	 * 
	 */
	public static BookBean getProxyBean() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(BookBean.class);
		// 回调方法
		enhancer.setCallback(new BookProxy());
		// 创建代理对象
		return (BookBean) enhancer.create();
	}

	/**
	 * 增加callback filter
	 * 
	 * @return
	 */
	public static BookBean getFilterProxyBean() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(BookBean.class);
		// 回调方法
		enhancer.setCallbacks(new Callback[] { new BookProxy(), NoOp.INSTANCE });
		enhancer.setCallbackFilter(new BookProxyFilter());
		// 创建代理对象
		return (BookBean) enhancer.create();
	}
}