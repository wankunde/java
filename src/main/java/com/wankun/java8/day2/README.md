# Supplier & Consumer

* Supplier
  * 通过 `TestSupplier::new` 提供一个supplier
  * 通过 `get()` 方法得到一个目标实例，类似 new 操作，也可以叫做 **工厂方法** 
  * Supplier 与 直接new对象 的区别是: Supplier 不接收任何参数,不能 new 接口 与 抽象类
  * 无参 lambda 函数也实现了supplier接口

* Consumer
  * 可以接收一个入参，并对入参执行对应对函数（好像是函数式编程中的函数）
  * `accept` 接收入参
  * `andThen` 可以实现一个参数，执行多个函数
  * 有参 lambda Function 也可以作为Consumer，来调用accept 方法
  
# lambda方式定义Function

通过 ` () -> {} ` 语法实现一个Function 内部类的定义，Function 可以接收一个输入值 T，返回结果 R

# Optional 

* 对象容器，他封装了一些方法，来判断容器中的对象是否为null
* `isPresent` 容器中的内容是否为 null
* `get` 获取容器中内容
* `orElse` 如果对象为null，取的值
* 如果对象不为空，执行的Function
