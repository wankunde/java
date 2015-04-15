package com.wankun.proxy.cglib;

public class BookBean {
	public void addBook() {
		System.out.println("增加图书的普通方法...");
	}

	public void deleteBook() {
		System.out.println("删除图书的普通方法...");
	}

	public void queryBook() {
		System.out.println("查询图书的普通方法...");
	}
}