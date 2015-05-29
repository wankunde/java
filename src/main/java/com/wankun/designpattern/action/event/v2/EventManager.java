package com.wankun.designpattern.action.event.v2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 核心:dispatcher ，负责从queue中取事件，根据EventType去生成对应的EventHandler去处理事件
 * 
 * @author wankun
 */
public class EventManager {

	private static final int EVENT_QUEUE_LENGTH = 1000;
	private static final int DISPATCHER_NUM = 2;
	private static final int EVENT_HANDLER_NUM = 10;

	protected BlockingQueue<Event> eventQueue = null;
	protected ExecutorService eventHandlerPool = null;
	private EventDispatcher[] dispatchers = null;

	public void init() {
		eventQueue = new LinkedBlockingQueue<Event>(EVENT_QUEUE_LENGTH);
		eventHandlerPool = Executors.newFixedThreadPool(EVENT_HANDLER_NUM);
		dispatchers = new EventDispatcher[DISPATCHER_NUM];

		EventDispatcher.register(EventType.CLICK, ClickEventHandler.class);
		EventDispatcher.register(EventType.DBLCLICK, DblClickEventHandler.class);
	}

	public void start() {
		init();
		for (int i = 0; i < DISPATCHER_NUM; i++) {
			dispatchers[i] = new EventDispatcher(this.eventQueue, this.eventHandlerPool);
			dispatchers[i].start();
		}
	}

	public void stop() {
		for (int i = 0; i < DISPATCHER_NUM; i++) {
			dispatchers[i].stop();
		}
		eventHandlerPool.shutdown();
	}

	public void addEvent(Event event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
		}
	}

}