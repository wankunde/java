# Calcite Demo

# Calcite 组件

## Table

### ScannableTable 
> a simple implementation of Table, using the ScannableTable interface, that enumerates all rows directly

这种方式基本不会用，原因是查询数据库的时候没有任何条件限制，默认会先把全部数据拉到内存，然后再根据filter条件在内存中过滤。

使用方式：实现Enumerable scan(DataContext root);，该函数返回Enumerable对象，通过该对象可以一行行的获取这个Table的全部数据。 

### FilterableTable 
> a more advanced implementation that implements FilterableTable, and can filter out rows according to simple predicates

初级用法，我们能拿到filter条件，即能再查询底层DB时进行一部分的数据过滤，一般开始介入calcite可以用这种方式（translatable方式学习成本较高）。

使用方式：实现Enumerable scan(DataContext root, List filters )。

在scan的源码定义中： Calcite入门   

如果当前类型的“表”能够支持我们自己写代码优化这个过滤器，那么执行完自定义优化器，可以把该过滤条件从集合中移除，否则，就让calcite来过滤，简言之就是，如果我们不处理Listfilters ，Calcite也会根据自己的规则在内存中过滤，无非就是对于查询引擎来说查的数据多了，但如果我们可以写查询引擎支持的过滤器（比如写一些hbase、es的filter），这样在查的时候引擎本身就能先过滤掉多余数据，更加优化。提示，即使走了我们的查询过滤条件，可以再让calcite帮我们过滤一次，比较灵活。demo如下： Calcite入门   

### TranslatableTable 
> advanced implementation of Table, using TranslatableTable, that translates to relational operators using planner rules.

高阶用法，有些查询用上面的方式都支持不了或支持的不好，比如join、聚合、或对于select的字段筛选等，需要用这种方式来支持，好处是可以支持更全的功能，代价是所有的解析都要自己写，“承上启下”，上面解析sql的各个部件，下面要根据不同的DB（es\mysql\drudi..）来写不同的语法查询。

当使用ScannableTable的时候，我们只需要实现函数Enumerable scan(DataContext root);，该函数返回Enumerable对象，通过该对象可以一行行的获取这个Table的全部数据（也就意味着每次的查询都是扫描这个表的数据，我们干涉不了任何执行过程）；当使用FilterableTable的时候，我们需要实现函数Enumerable scan(DataContext root, Listfilters );参数中多了filters数组，这个数据包含了针对这个表的过滤条件，这样我们根据过滤条件只返回过滤之后的行，减少上层进行其它运算的数据集；当使用TranslatableTable的时候，我们需要实现RelNode toRel( RelOptTable.ToRelContext context, RelOptTable relOptTable);，该函数可以让我们根据上下文自己定义表扫描的物理执行计划，至于为什么不在返回一个Enumerable对象了，因为上面两种其实使用的是默认的执行计划，转换成EnumerableTableAccessRel算子，通过TranslatableTable我们可以实现自定义的算子，以及执行一些其他的rule，Kylin就是使用这个类型的Table实现查询。



# Some Demos

* PlannerToolDemo : 使用Frameworks 的Planner 工具完成Sql parse,validate,rel,最后使用Interpreter解析执行，得到结果


* 

School.json ： 定义Schema，由 SchemaFactory 进行Schema初始化


# HR Schema 


``` 
dependents : dependents
  dataType : 
  #0: empid JavaType(int)
  #1: name JavaType(class java.lang.String)
---------------- 
depts : depts
  dataType : 
  #0: deptno JavaType(int)
  #1: name JavaType(class java.lang.String)
  #2: employees RecordType(JavaType(int) empid, JavaType(int) deptno, JavaType(class java.lang.String) name, JavaType(float) salary, JavaType(class java.lang.Integer) commission) ARRAY
  #3: location RecordType(JavaType(int) x, JavaType(int) y)
---------------- 
emps : emps
  dataType : 
  #0: empid JavaType(int)
  #1: deptno JavaType(int)
  #2: name JavaType(class java.lang.String)
  #3: salary JavaType(float)
  #4: commission JavaType(class java.lang.Integer)
---------------- 
locations : locations
  dataType : 
  #0: empid JavaType(int)
  #1: name JavaType(class java.lang.String)
```

# Tips

* 通过在vm 参数中添加 `-Dcalcite.debug` 可以打印calcite  internal的中间生成代码，但是目前看作用不大。

# 相关资料 

* [Introduction to Apache Calcite](https://www.slideshare.net/JordanHalterman/introduction-to-apache-calcite)