package com.wankun.designpattern.action.event.v2;

/**
 * @author wankun
 */
enum EventType {
	NOTDEFINE,CLICK,DBLCLICK
}

public abstract class Event {

	private EventType eventType = EventType.NOTDEFINE;
	
	public EventType getType(){
		return eventType;
	}
	
}

class ClickEvent extends Event {
	private EventType eventType = EventType.CLICK;
	
	public EventType getType(){
		return eventType;
	}
}

class DblClickEvent extends Event {
	private EventType eventType = EventType.DBLCLICK;
	public EventType getType(){
		return eventType;
	}
}