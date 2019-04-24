# Metrics-core 介绍

* 使用

```xml
        <dependency>
          <groupId>io.dropwizard.metrics</groupId>
          <artifactId>metrics-core</artifactId>
          <version>3.1.5</version>
        </dependency>
```

* Metrics Registries类似一个metrics容器，维护一个Map，可以是一个服务一个实例。
* 支持五种metric类型：Gauges、Counters、Meters、Histograms和Timers。
* 可以将metrics值通过JMX、Console，CSV文件和SLF4J loggers发布出来。

Metrics 类型介绍

* Gauges是一个最简单的计量，一般用来统计瞬时状态的数据信息，比如系统中处于pending状态的job
* Counter是Gauge的一个特例，维护一个计数器，可以通过inc()和dec()方法对计数器做修改
* Meters用来度量某个时间段的平均处理次数（request per second），每1、5、15分钟的TPS。比如一个service的请求数，通过metrics.meter()实例化一个Meter之后，然后通过meter.mark()方法就能将本次请求记录下来。统计结果有总的请求数，平均每秒的请求数，以及最近的1、5、15分钟的平均TPS。
* Histograms主要使用来统计数据的分布情况，最大值、最小值、平均值、中位数，百分比（75%、90%、95%、98%、99%和99.9%）
* Timers主要是用来统计某一块代码段的执行时间以及其分布情况

```

19-4-24 14:42:09 ===============================================================

-- Gauges ----------------------------------------------------------------------
com.wankun.metrics.TestGauges.pending-job.size
             value = 9
             
-- Counters --------------------------------------------------------------------
com.wankun.metrics.TestCounter.pedding-jobs
             count = 9
             
-- Meters ----------------------------------------------------------------------
com.wankun.metrics.TestMeters.request
             count = 239
         mean rate = 9.96 events/second
     1-minute rate = 10.00 events/second
     5-minute rate = 10.00 events/second
    15-minute rate = 10.00 events/second
    
-- Histograms ------------------------------------------------------------------
com.wankun.metrics.TestHistograms.random
             count = 239
               min = 0
               max = 98
              mean = 48.22
            stddev = 28.16
            median = 47.00
              75% <= 72.00
              95% <= 94.00
              98% <= 96.00
              99% <= 98.00
            99.9% <= 98.00

-- Timers ----------------------------------------------------------------------
com.wankun.metrics.TestTimers.request
             count = 30
         mean rate = 2.00 calls/second
     1-minute rate = 1.84 calls/second
     5-minute rate = 1.81 calls/second
    15-minute rate = 1.80 calls/second
               min = 29.76 milliseconds
               max = 939.89 milliseconds
              mean = 497.45 milliseconds
            stddev = 254.83 milliseconds
            median = 444.97 milliseconds
              75% <= 710.39 milliseconds
              95% <= 921.37 milliseconds
              98% <= 939.89 milliseconds
              99% <= 939.89 milliseconds
            99.9% <= 939.89 milliseconds
```


# Health Check

* pom
```xml
        <dependency>
            <groupId>io.dropwizard.metrics</groupId>
            <artifactId>metrics-healthchecks</artifactId>
            <version>3.1.5</version>
        </dependency>
```
* 继承 `HealthCheck` 并实现`check()` 方法，返回 `Result.healthy()`或者`Result.unhealthy("Can't ping database.")` 对象 
* 遍历 `registry.runHealthChecks().entrySet()` 来执行health check 并迭代返回结果。
```
database1: FAIL, error message: Can't ping database.
database2: OK
```

# 参考文档

* [官方文档](https://metrics.dropwizard.io/3.1.0/manual/core/#man-core-reporters-jmx)
* [Metrics-Java版的指标度量工具之一](https://www.cnblogs.com/nexiyi/p/metrics_sample_1.html)
* [Metrics-Java版的指标度量工具之二](https://www.cnblogs.com/nexiyi/p/metrics_sample_2.html)

[hadoop metrics2源码分析](http://blog.sina.com.cn/s/blog_829a682d0101m0wd.html)