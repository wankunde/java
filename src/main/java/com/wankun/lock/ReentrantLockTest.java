package com.wankun.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-08-25.
 */
public class ReentrantLockTest {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        System.out.println("first");
        lock.lock();
        System.out.println("second");
        lock.unlock();
        lock.unlock();
    }
}
