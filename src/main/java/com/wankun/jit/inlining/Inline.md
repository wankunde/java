# 通过 JVM option `-XX:+UnlockDiagnosticVMOptions -XX:+PrintInlining` 查看JIT inline结果 

# Getter 和 Setter 方法默认会被 Inline

查看编译出来的 class 文件，发现就没有对应的 Getter 和 Setter 方法
```text
      11: ldc           #5                  // int 100000
      13: if_icmpge     32
      16: aload_1
      17: iconst_1
      18: invokespecial #6                  // Method setId:(I)V
      21: aload_1
      22: invokespecial #7                  // Method getId:()I
```

# 测试 Inline 方法

## 写法1 

```java
    for (int i = 0; i < NUMBER_OF_ITERATORS; i++) {
      new ConsecutiveNumbersSum(i).getTotalSum();
    }
```
对应的JVM class 一次调用了 ConsecutiveNumbersSum 的构造方法和 `Method getTotalSum:()J`
测试结果没有 inline ` @ 1   java.lang.Object::<init> (1 bytes)   inline (hot)`

```java
    for (int i = 0; i < NUMBER_OF_ITERATORS; i++) {
    calculateSum(i);
    }
```
此时JIT 发现 hot 方法 calculateSum，进行inline
```java
    194   41       4       com.wankun.jit.inlining.ConsecutiveNumbersSum::calculateSum (12 bytes)
                              @ 5   com.wankun.jit.inlining.ConsecutiveNumbersSum::<init> (10 bytes)   inline (hot)
                                @ 1   java.lang.Object::<init> (1 bytes)   inline (hot)
                              @ 8   com.wankun.jit.inlining.ConsecutiveNumbersSum::getTotalSum (37 bytes)   inline (hot)
```

# 正常 Public 方法调用

* 方法会正常编译在class 文件中，后面在 JIT Inline 是时候会动态优化
* 执行的是 `invokevirtual` 调用
```java
  public static void main(java.lang.String[]);
    Code:
       0: iconst_0
       1: istore_1
       2: iload_1
       3: bipush        10
       5: if_icmpge     21
       8: getstatic     #2                  // Field obj:Lcom/wankun/jit/inlining/InlineCommonMethod;
      11: invokevirtual #3                  // Method fn1:()J
      14: pop2
      15: iinc          1, 1
      18: goto          2
      21: return

  public long fn1();
    Code:
       0: aload_0
       1: bipush        100
       3: invokevirtual #4                  // Method fn2:(I)J
       6: lstore_1
    ....
      19: lreturn

  public long fn2(int);
    Code:
       0: lconst_0
    ....
```

* Private 方法在 class 文件中看不到，只能看到有调用
* 执行的是 `invokespecial` 调用

```java
  public static void main(java.lang.String[]);
    Code:
       0: iconst_0
       1: istore_1
       2: iload_1
       3: bipush        10
       5: if_icmpge     21
       8: getstatic     #2                  // Field obj:Lcom/wankun/jit/inlining/InlineCommonMethod;
      11: invokespecial #3                  // Method fn1:()J
      14: pop2
      15: iinc          1, 1
      18: goto          2
      21: return

  public long fn2(int);
    Code:
    ....

```