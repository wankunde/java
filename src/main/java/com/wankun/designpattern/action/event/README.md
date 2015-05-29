# 事件驱动模型

# 事件驱动模型

* 使用List存储需要监听的事件
* 事件到达时，查找对应的Handler程序，对事件进行处理

## 事件驱动模型优化版

* BlockingQueue<Event> eventQueue addEvent() -- 事件接收和事件响应松耦合
* -- 多个Dispatcher 实现事件快速调度
* EventDispatcher takeEvent() and dispatch by EventType -- 根据EventType进行Map查找，而非遍历
* EventDispatcher new handler Instance and submit()  -- 后台线程池响应，异步执行