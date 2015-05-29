package com.wankun.designpattern.action.event.v2;

import java.util.concurrent.Callable;

/**
 * 通过setEvent 关联事件对象 通过call方法对事件进行响应
 * 
 * @author wankun
 */
public abstract class EventHandler<T extends Event> implements Callable<Object> {
	private Event event;

	public Event getEvent() {
		return event;
	}

	/**
	 * 注册时间
	 * 
	 * @param event
	 */
	public void setEvent(Event event) {
		this.event = event;
	}
}

class ClickEventHandler extends EventHandler<ClickEvent> {

	@Override
	public Object call() throws Exception {
		System.out.println("Click 事件已经发生...");
		return null;
	}

}

class DblClickEventHandler extends EventHandler<DblClickEvent> {

	@Override
	public Object call() throws Exception {
		System.out.println("Double click 事件已经发生...");
		return null;
	}

}