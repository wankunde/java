package com.wankun.designpattern.action.event.v2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * @author wankun
 */
public class EventDispatcher {

	private final BlockingQueue<Event> eventQueue;
	private final ExecutorService eventHandlerPool;
	protected final static Map<EventType, Class<? extends EventHandler<? extends Event>>> eventDispatchers = new HashMap<EventType, Class<? extends EventHandler<? extends Event>>>();
	private Thread eventHandlingThread;

	private volatile boolean stopped = false;

	public EventDispatcher(BlockingQueue<Event> eventQueue, ExecutorService eventHandlerPool) {
		this.eventQueue = eventQueue;
		this.eventHandlerPool = eventHandlerPool;
		System.out.println("Event dispatcher starting...");
	}

	Runnable createThread() {
		return new Runnable() {
			@Override
			public void run() {
				while (!stopped && !Thread.currentThread().isInterrupted()) {
					Event event;
					try {
						event = eventQueue.take();
					} catch (InterruptedException ie) {
						if (!stopped) {
							System.out.println("Dispatcher thread interrupted");
							ie.printStackTrace();
						}
						return;
					}
					if (event != null) {
						dispatch(event);
					}
				}
			}
		};
	}

	@SuppressWarnings("unchecked")
	protected void dispatch(Event event) {
		EventType type = event.getType();
		try {
			Class<? extends EventHandler> handlerClazz = eventDispatchers.get(type);
			if (handlerClazz != null) {
				EventHandler handler = handlerClazz.newInstance();
				handler.setEvent(event);
				eventHandlerPool.submit(handler);
			} else {
				throw new Exception("No handler for registered for " + type);
			}
		} catch (Throwable t) {
			System.err.println("Error in dispatcher thread");
			t.printStackTrace();
//			System.exit(-1);
		}
	}

	public static synchronized void register(EventType eventType, Class<? extends EventHandler<? extends Event>> handler) {
		Class<? extends EventHandler<? extends Event>> registeredHandler = eventDispatchers.get(eventType);
		System.out.println("Registering " + eventType + " for " + handler);
		if (registeredHandler == null) {
			eventDispatchers.put(eventType, handler);
		}
	}

	public void start() {
		eventHandlingThread = new Thread(createThread());
		eventHandlingThread.setName("AsyncDispatcher event handler");
		eventHandlingThread.start();
		System.out.println("Event dispatcher started!");
	}

	public void stop() {
		stopped = true;
		if (eventHandlingThread != null) {
			eventHandlingThread.interrupt();
			try {
				eventHandlingThread.join();
			} catch (InterruptedException ie) {
				System.out.println("Interrupted Exception while stopping");
				ie.printStackTrace();
			}
		}
	}

}