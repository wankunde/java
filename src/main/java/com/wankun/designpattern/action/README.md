## Chain of Responsibility 责任链模式

* 多个对象都有机会处理请求，从而避免请求的发送者和接收者的耦合关系。
* 这些请求形成一个责任链，沿着该链传递该请求，知道有一个对象处理它为止。
* 不明确具体接收者，根据请求对象，自动选择处理者。

```sequence
request->TLRequestHandle: tl request
TLRequestHandle->PMRequestHandle: pm request
PMRequestHandle->HRRequestHandle: hr request
TLRequestHandle-->>response: return
PMRequestHandle-->>response: return
HRRequestHandle-->>response: return
```
	

## Command 命令模式

把发出命令的责任和执行命令的责任分割开，委派给不同的对象。

![command parttern][1]
　
命令模式涉及到五个角色，它们分别是：

* 客户端(Client)角色：创建一个具体命令(ConcreteCommand)对象并确定其接收者。
* 命令(Command)角色：声明了一个给所有具体命令类的抽象接口。
* 具体命令(ConcreteCommand)角色：定义一个接收者和行为之间的弱耦合；实现execute()方法，负责调用接收者的相应操作。execute()方法通常叫做执行方法。
* 请求者(Invoker)角色：负责调用命令对象执行请求，相关的方法叫做行动方法。
* 接收者(Receiver)角色：负责具体实施和执行一个请求。任何一个类都可以成为接收者，实施和执行请求的方法叫做行动方法。



## Interpreter 解释器模式
## Iterator 迭代器模式
## Mediator 中介者模式
## Memento 备忘录模式
## Observer 观察者模式
## State 状态模式
## Strategy 策略模式

## TemplateMethod 模板方法

## Visitor 访问者模式


[1]: /doc/pic/command.png "command parttern"