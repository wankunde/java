package com.wankun.designpattern.action.command;

/**
 * @author wankun
 */
public class Receiver {
	/**
	 * 真正执行命令相应的操作
	 */
	public void action() {
		System.out.println("执行操作");
	}
}
