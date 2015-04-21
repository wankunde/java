package com.wankun.jvm.clsloader;

import java.net.URL;

public class BootLoader {

	public static void main(String[] args) {
		// BootStrap ClassLoader：称为启动类加载器，是Java类加载层次中最顶层的类加载器，

		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for (int i = 0; i < urls.length; i++) {
			System.out.println(urls[i].toExternalForm());
		}

		// 从属性中取
		System.out.println(System.getProperty("sun.boot.class.path"));

		// Extension ClassLoader
		// ：称为扩展类加载器，负责加载Java的扩展类库，默认加载JAVA_HOME/jre/lib/ext/目下的所有jar。
		// App ClassLoader：称为系统类加载器，负责加载应用程序classpath目录下的所有jar和class文件。
	}
}
