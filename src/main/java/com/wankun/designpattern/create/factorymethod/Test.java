package com.wankun.designpattern.create.factorymethod;

/**
 * @author wankun
 */
public class Test {

	public static void main(String[] args) {
		IWorkFactory studentWorkFactory = new StudentWorkFactory();
		studentWorkFactory.getWorker().doWork();

		IWorkFactory teacherWorkFactory = new TeacherWorkFactory();
		teacherWorkFactory.getWorker().doWork();
	}
}