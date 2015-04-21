package com.wankun.java8;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Function;

public class App {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Function<Integer, Integer> fn = x -> x + 2;
		System.out.println(fn.apply(2));

		// Runnable 简化
		new Thread(() -> {
			System.out.println("aa");
		}).start();

		// Callable 简化
		FutureTask<Integer> futuretask = new FutureTask<Integer>(() -> new Random().nextInt(100));
		new Thread(futuretask).start();
		System.out.println("futuretask11 : " + futuretask.get());

		// 直接提交Callable
		ExecutorService threadpool = Executors.newSingleThreadExecutor();
		Future<Integer> future = threadpool.submit(() -> new Random().nextInt(100));
		System.out.println("futuretask22 : " + future.get());
		threadpool.shutdown();
	}
}
