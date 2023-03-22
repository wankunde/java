# 逃逸分析

* 默认JVM如果方法的内的对象分配没有返回给函数之后的其他地方使用，则分配对象到 stack
* 默认开启
* 测试结果

```text
$ jmap -histo 66171 | head -n 30

 num     #instances         #bytes  class name
----------------------------------------------
   1:           677        6760704  [I
   2:          3472        1958240  [B
   3:        109794        1756704  com.wankun.jit.EscapeAnalyze$Student
   4:          7861         994736  [C
   5:          5583         133992  java.lang.String
   6:           704          80576  java.lang.Class
   7:          1375          66624  [Ljava.lang.Object;

```