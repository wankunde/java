package com.wankun.designpattern.action.event.v2;

/**
 * @author wankun
 */
public class Client {

	public static void main(String[] args) {
		EventManager manager = new EventManager();
		manager.start();
		manager.addEvent(new ClickEvent());
		manager.addEvent(new DblClickEvent());
		manager.addEvent(new ClickEvent());
		manager.stop();
	}

}
