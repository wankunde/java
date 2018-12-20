package com.wankun.concurrent;

import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

public class CompletableFutureTest {

    /**
     * 通过线程池移步执行，并返回Future对象
     *
     * @return
     * @throws InterruptedException
     */
    public Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.complete("Hello");
            return null;
        });

        return completableFuture;
    }

    @Test
    public void testCompletableFuture() throws InterruptedException, ExecutionException {
        Future<String> completableFuture = calculateAsync();
        String result = completableFuture.get();
        assertEquals("Hello", result);
    }


    /**
     * 直接返回对象，没有Block
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void nonBlockFuture() throws ExecutionException, InterruptedException {
        Future<String> completableFuture = CompletableFuture.completedFuture("Hello");
        String result = completableFuture.get();
        assertEquals("Hello", result);
    }

    public Future<String> calculateAsyncWithCancellation() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.cancel(false);
            return null;
        });

        return completableFuture;
    }


    /**
     * 直接由Future对象进行Cancel
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test(expected = CancellationException.class)
    public void testCancelAsyncFuture() throws InterruptedException, ExecutionException {
        Future<String> future = calculateAsyncWithCancellation();
        future.get(); // CancellationException
    }

    /**
     * Supplier 允许异步执行，并返回值
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testLambdaAndSupplier() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future
                = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.currentThread().sleep(4000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });
        assertEquals("Hello", future.get());
    }

    @Test
    public void testHandleError() throws ExecutionException, InterruptedException {
        String name = null;
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            if (name == null) {
                throw new RuntimeException("Computation error!");
            }
            return "Hello, " + name;
        }).handle((s, t) -> {
            System.out.println(s);
            System.out.println(t);
            return s != null ? s : "Hello, Stranger!";
        });

        assertEquals("Hello, Stranger!", completableFuture.get());
    }

    @Test
    public void testAsyncMethod() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future = completableFuture
                .thenApplyAsync(s -> s + " World");

        assertEquals("Hello World", future.get());
    }

}
