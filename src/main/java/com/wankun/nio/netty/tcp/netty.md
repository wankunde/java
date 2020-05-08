# Reactor模式
* 单Reactor单线程
* 单Reactor多线程
* 主从Reactor多线程

#TaskQueue / ScheduleTaskQueue 异步执行任务

# 异步模型 Future - Listener 机制

# Netty 核心组件

* Bootstrap 和 ServerBootstrap : 程序引导类
* Future 和 ChannelFuture : 注册监听，当操作成功或失败时，自动触发注册的监听事件。
* Channel : 网络通信组件，能用于执行网络 I/O 操作 
* Selector : 选择器，轮询判断注册的channel是否有事件发生
* ChannelHandler 及其实现类 : 针对对应的事件，实现对应的监听方法
* Pipeline 和 ChannelPipeline : Handler的集合