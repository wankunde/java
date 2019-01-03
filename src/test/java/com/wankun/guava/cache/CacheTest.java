package com.wankun.guava.cache;

import com.google.common.base.Stopwatch;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CacheTest {

    /**
     * 测试数据插入，超过阀值后，数据自动过期
     */
    @Test
    public void testPut() {
        Cache cache = CacheBuilder.newBuilder().maximumSize(3).recordStats().build();
        //通过put或者putAll手动将数据添加到缓存
        cache.put("id", "10");
        Map batch = new HashMap<>();
        cache.put("name", "aty");
        cache.put("addr", "sz");
        cache.putAll(batch);

        //数量超出最大限制,会导致guava清除之前的数据,evictionCount增加1
        //手动添加缓存数据,不会影响其他缓存统计指标值
        cache.put("new", "replace");
        System.out.println(cache.stats());

        //不接受null
        try {
            cache.put("a", null);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        System.out.println(cache.asMap());
    }

    @Test
    public void testGet() throws ExecutionException {
        Cache cache = CacheBuilder.newBuilder().recordStats().build();
        cache.put("name", "aty");
        //缓存未命中missCount加1
        System.out.println(cache.getIfPresent("s") == null);
        System.out.println(cache.stats());
        //缓存命中hitCount加1
        System.out.println(cache.getIfPresent("name") != null);
        System.out.println(cache.stats());

        //Callable.call()不能返回null,否则guava报异常
        Callable callable = new Callable() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "demo";
            }
        };
        //使用guavaStopwatch计时
        Stopwatch watch = Stopwatch.createStarted();
        cache.get("a", callable);
        watch.stop();
        //缓存不存在missCount加1,调用时间也会增加到totalLoadTime
        System.out.println(cache.stats());
        //大致2s可以证明:guavacache是在调用者的线程中执行callable任务的
        System.out.println("elapsetime=" + watch.elapsed(TimeUnit.MILLISECONDS));
    }
}
