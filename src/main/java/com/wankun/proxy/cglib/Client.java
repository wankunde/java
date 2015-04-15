package com.wankun.proxy.cglib;

public class Client {

	public static void main(String[] args) {
		System.out.println("-------");
		commonBean();
		System.out.println("-------");
		proxyBean();
		System.out.println("-------");
		filterProxyBean();
	}

	public static void commonBean() {
		BookBean book = BookFactory.getBookBean();
		book.addBook();
		book.deleteBook();
		book.queryBook();
	}

	public static void proxyBean() {
		BookBean book = BookFactory.getProxyBean();
		book.addBook();
		book.deleteBook();
		book.queryBook();
	}
	
	public static void filterProxyBean() {
		BookBean book = BookFactory.getFilterProxyBean();
		book.addBook();
		book.deleteBook();
		book.queryBook();
	}
}