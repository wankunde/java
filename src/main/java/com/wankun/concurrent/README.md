# ForkJoin

相同点: 都是通过 submit() 方法提交子Task，子task 继续通过 fork() 方法进行子task拆分
* ForkJoinTask : RecursiveTask<Integer> 的 compute() 方法可以返回子task的计算结果 Future<Result>
* ForkJoin: RecursiveAction 的 compute() 方法不返回结果，只计算

# CAS

可以被多个线程安全的读取和更新的变量

# 多线程打印单个文件

按线程顺序打印，强调线程执行的先后顺序，只有 seq == 自己的时候，才能工作，否则要进行等待

## 通过 synchronized wait 和 notifyAll 实现

* 定义全局顺序标示符号
* 对 lock 加锁
  * 标示符号不满足条件 --> sleep()
  * 开始工作
  * 更新标示符号为下一个
  * 唤醒所有等待线程

## 通过 lock, await 和 signalAll 实现

* 实现原理和上面类似，必须有全局顺序符号
* 加锁控制使用 lock 方法，唤醒控制使用 lock.newCondition 变量控制

## Semaphore 数组控制打印顺序

* 初始化时，第一个 semaphore 可以工作
* 第一个线程 semaphore获取后，开始工作， 之后设置下一个需要工作的线程 semaphore
* Next 线程开始工作
* 无需额外变量来控制线程调度顺序，只有指定的线程会被调度起来，或者等待指定的线程启动后来执行

# 多线程打印多个文件

强调多个线程可以作为一组同时工作，全部完成后，开始下一组任务

## CountDown 实现

* CountDown 标示一组可以同时工作的线程
* 每个都独立工作，工作完成后，countdown， 然后 wait()
* 最后一个完成后，先 notifyAll(), reset countDown, 进入下一组工作线程

## CyclicBarrier

* await() 等待全部线程完成
* 自动开始下一组任务

### CyclicBarrier 实现原理

* `count` 或 `parties` 是工作并发数
* 每次调用 `await()` 方法，`count--`
* `count == 0` 全部完成,执行 `barrierCommand`
* `nextGeneration()`

```java

    private void nextGeneration() {
        // signal completion of last generation
        trip.signalAll();
        // set up next generation
        count = parties;
        generation = new Generation();
    }
    
            int index = --count;
            if (index == 0) {  // tripped
                Runnable command = barrierCommand;
                if (command != null) {
                    try {
                        command.run();
                    } catch (Throwable ex) {
                        breakBarrier();
                        throw ex;
                    }
                }
                nextGeneration();
                return 0;
            }
```


# 互斥锁


1）互斥锁（3）：LockSupport、ReentrantLock、Sync

（2）读写锁（1）：ReentrantReadWriteLock

（3）信号量（2）：Semaphore、CountDownLatch

（4）屏障（1）：CyclicBarrier

（5）事件（3）：Wait/NotifyAll、Join、ReentrantLock+Condition

（6）CAS（1）：AtomicInteger