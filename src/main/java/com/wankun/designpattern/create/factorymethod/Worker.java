package com.wankun.designpattern.create.factorymethod;

/**
 * @author wankun
 */
public interface Worker {
	void doWork();
}

class StudentWorker implements Worker {
	public void doWork() {
		System.out.println("学生做作业!");
	}
}

class TeacherWorker implements Worker {
	public void doWork() {
		System.out.println("老师审批作业!");
	}
}