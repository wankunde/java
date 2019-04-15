# Java 8 学习实践

* FunctionalInterfaceTest : FunctionalInterface 使用
* MethodReferrance : **::**  新方法引用方式
* GuavaTest ： Guava中的一些工具以Function 接口的形式使用 ,Supplier 接口,Consumer 接口, Comparator 接口,Optional 接口
* LambdaScope ： Lambda 表达式参数不一定为final 形式
* StreamTest : List Stream 使用示例,Map 使用示例
* ParallelStreamTest : 并发Stream 结合CGLib Proxy 例子
* LocalDateTimeTest : 新的Date和Time的处理工具

 ## MethodReferrance 

1. 概念引入
	方法引用Person::compareByAge跟lambda表达式(a, b) -> Person.compareByAge(a,b)在语义上是等价的。 两者有如下的特征 ：
		
  * 形参相同 ，都是(Person, Person)。
  * 调用方法Person.compareByAge。
		
2. 分类

|	Kind |	Example |
| ------ | -------- |
|Reference to a static method	|	ContainingClass::staticMethodName	|
|Reference to an instance method of a particular object	|	containingObject::instanceMethodName	|
|Reference to an instance method of an arbitrary object of a particular type	|	ContainingType::methodName	|
|Reference to a constructor	|	ClassName::new	|

# CompletableFuture

| 方法名	| 描述 |
| ------ | -------- |
| runAsync(Runnable runnable) | 使用ForkJoinPool.commonPool()作为它的线程池执行异步代码。|
| runAsync(Runnable runnable, Executor executor) | 使用指定的thread pool执行异步代码。|
| supplyAsync(Supplier<U> supplier) | 使用ForkJoinPool.commonPool()作为它的线程池执行异步代码，异步操作有返回值|
| supplyAsync(Supplier<U> supplier, Executor executor) | 使用指定的thread pool执行异步代码，异步操作有返回值