# Java 8 学习实践

* GuavaTest ： Guava中的一些工具以Function 接口的形式使用 ,Supplier 接口,Consumer 接口, Comparator 接口,Optional 接口
* InterfaceTest : interface 允许带默认方法实现
* LambdaScope ： Lambda 表达式参数不一定为final 形式
* StreamTest : List Stream 使用示例,Map 使用示例
* ParallelStreamTest : 并发Stream 结合CGLib Proxy 例子
* StaticMethod : 新的静态方法使用

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