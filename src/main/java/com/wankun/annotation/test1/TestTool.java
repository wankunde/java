package com.wankun.annotation.test1;

import java.lang.reflect.Method;

/**
 * 访问注解
 * 
 * @author kunwan
 *
 */
public class TestTool {

	public static void main(String[] args) {
		Method[] methods = ForumService.class.getDeclaredMethods();
		for (Method m : methods) {
			NeedTest t = m.getAnnotation(NeedTest.class);
			if (t != null && t.value())
				System.out.println("方法:" + m.getName() + "  需要测试");
			else
				System.out.println("方法:" + m.getName() + "  不需要测试");
		}
	}
}
