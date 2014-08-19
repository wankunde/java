#使用Guava实现订阅消息
  http://www.cnblogs.com/peida/p/EventBus.html
### 使用方法：就不用再继承指定的接口, 只需要在指定的方法上加上@Subscribe注解即可。
	消息封装类：TestEvent 
	消息接受类：EventListener 
	测试类及输出结果：TestEventBus 
	
### MultiListener的使用
	只需要在要订阅消息的方法上加上@Subscribe注解即可实现对多个消息的订阅
	订阅类：MultipleListener 
	测试类：TestMultipleEvents 