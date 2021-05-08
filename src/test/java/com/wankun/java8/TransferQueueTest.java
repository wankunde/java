package com.wankun.java8;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.util.stream.IntStream;

/**
 * Producer生成的消息过快，通过下游的Consumer来限制上游的Producer生产速度
 */
public class TransferQueueTest {

    class Producer extends Thread {
        private TransferQueue<String> queue;
        public Logger logger = LoggerFactory.getLogger(Producer.class);

        public Producer(TransferQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            IntStream.rangeClosed(1, 10).forEach(i -> {
                try {
                    logger.info("try to transfer :" + i);
                    queue.transfer("Data" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    class Consumer extends Thread {
        public Logger logger = LoggerFactory.getLogger(Consumer.class);

        private TransferQueue<String> queue;

        public Consumer(TransferQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                String msg;
                do {
                    Thread.sleep(1000);
                    msg = queue.poll(1, TimeUnit.SECONDS);
                    logger.info("consumer message :" + msg);
                } while (msg != null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testTransferQueue() throws InterruptedException {
        TransferQueue<String> queue = new LinkedTransferQueue();
        Consumer consumer = new Consumer(queue);
        consumer.start();
        IntStream.rangeClosed(1, 3).forEach(i -> new Producer(queue).start());
        consumer.join();
    }
}
