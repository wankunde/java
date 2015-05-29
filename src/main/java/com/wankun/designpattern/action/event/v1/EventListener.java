package com.wankun.designpattern.action.event.v1;

/**
 * @author wankun
 */
public interface EventListener<T extends Event> {

	public void handleEvent(T event);

}

interface ClickEventHandler extends EventListener<ClickEvent> {

}

interface DblClickEventHandler extends EventListener<DblClickEvent> {

}