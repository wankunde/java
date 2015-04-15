package com.wankun.java8;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ParallelStreamTest {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int max = 1000000;
		List<String> values = new ArrayList<>(max);
		for (int i = 0; i < max; i++) {
			UUID uuid = UUID.randomUUID();
			values.add(uuid.toString());
		}

		ParallelStreamTest ins = new ParallelStreamTest();
		
		// 方式一：传统的CGLIB代理
//		ParallelStreamTest pp = (ParallelStreamTest) Enhancer.create(ins.getClass(),
//				new MethodInterceptor() {
//
//					@Override
//					public Object intercept(Object obj, Method method, Object[] args,
//							MethodProxy proxy) throws Throwable {
//						long t0 = System.nanoTime();
//						Object res = method.invoke(ins, args);
//						long t1 = System.nanoTime();
//						long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
//						System.out.println(String.format("sequential sort took: %d ms", millis));
//						return res;
//					}
//				});
		
		// Lambda 创建代理
		MethodInterceptor interceptor = (Object obj, Method method, Object[] agrs2,MethodProxy proxy) -> {
					long t0 = System.nanoTime();
					Object res = proxy.invokeSuper(obj, agrs2);
					long t1 = System.nanoTime();
					long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
					System.out.println(String.format("sequential sort took: %d ms", millis));
					return res;
				};
		ParallelStreamTest pp = (ParallelStreamTest) Enhancer.create(ins.getClass(),interceptor);

		pp.sort1(values);
		pp.sort2(values);

	}

	public void sort1(List<String> values) {
		long count = values.stream().sorted().count();
		System.out.println("sort 1 :" + count);
	}

	public void sort2(List<String> values) {
		long count = values.parallelStream().sorted().count();
		System.out.println("sort 2 :" + count);
	}

}
