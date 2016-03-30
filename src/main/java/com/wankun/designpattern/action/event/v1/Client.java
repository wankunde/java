package com.wankun.designpattern.action.event.v1;

/**
 * @author wankun
 */
public class Client {

	private static Event currentEvent;

	private static Button button;

	public static void main(String[] args) {
		// 初始化事件监听
		button = new Button();

		button.addEventListener(new ClickEventHandler() {
			@Override
			public void handleEvent(ClickEvent event) {
				System.out.println("Button was clicked!");
			}
		});

		button.addEventListener(new DblClickEventHandler() {
			@Override
			public void handleEvent(DblClickEvent event) {
				System.out.println("Button was double clicked!");
			}
		});

		// 事件响应测试
		currentEvent = new ClickEvent();
		button.notifyListeners(currentEvent);

		currentEvent = new DblClickEvent();
		button.notifyListeners(currentEvent);
	}

}