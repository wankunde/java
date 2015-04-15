# 静态代理
	
![factory uml][1]
	
```seq
title: factory proxy

Client->Factory: request instance
note over Factory:get real instance
note over Factory:define proxy class,overloaded methods
Factory-->Client:get proxy instance
Client->proxy instance:rpc request
note over proxy instance: prehandle
proxy instance-> real instance:real work 
note over proxy instance: posthandle
proxy instance--> Client: rpc result
```
	
	核心：工厂模式，对工作者进行封装

# 动态代理

![jdk proxy uml][2]

```seq
title: dynamic proxy

Client->Factory: request instance
note over Factory:get real instance
note over Factory:define invocationHandler to complete aop operation
note over Factory:get jdk proxy instance by classloader,interfaces,and handler
Factory-->Client:proxy instance
Client->proxy instance:rpc request
note over proxy instance: prehandle
proxy instance-> real instance:real work 
note over proxy instance: posthandle
proxy instance--> Client: rpc result
```

# CGLib动态代理

原理：CGLib采用了非常底层的字节码技术，其原理是通过字节码技术为一个类创建子类，并在子类中采用方法拦截的技术拦截所有父类方法的调用，顺势织入横切逻辑。JDK动态代理与CGLib动态代理均是实现Spring AOP的基础。

这是一个需要被代理的类，也就是父类，通过字节码技术创建这个类的子类，实现动态代理。

通过Enhancer的create方法，根据传入的父类的字节码，扩展父类的class，和proxy instance来创建代理对象。
proxy instance的intercept()方法拦截所有目标类方法的调用，obj表示目标类的实例，method为目标类方法的反射对象，args为方法的动态入参，proxy为代理类实例。proxy.invokeSuper(obj, args)通过代理类调用父类中的方法。

看到有人在用 method.invoke(实例对象, agrs); 方法进行调用，还是有问题，这个还是要传入实例对象，麻烦了……

```seq
title: cglib proxy

Client->Factory: request instance
note over Factory:Enhancer.create(superclass,ProxyBean)
Factory-->Client:Enhancer
Client->Enhancer:rpc request
Enhancer->callbackFilter: accept
callbackFilter->callback: get proxy instance
note over proxy instance: intercept(prehandle, real work,posthandle)
proxy instance--> Client: rpc result
```


# 静态代理和动态代理对比

* 静态代理：由程序员创建或特定工具自动生成源代码，再对其编译。在程序运行前，代理类的.class文件就已经存在了。 
动态代理：在程序运行时，运用反射机制动态创建而成。

代理类能服务的接口
一个接口	N个接口





[1]: /doc/pic/factory.jpg "factory uml"
[2]: /doc/pic/jdkproxy.jpg "jdk proxy uml"
