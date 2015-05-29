package com.wankun.designpattern.create.factorymethod;

/**
 * @author wankun
 */
public interface IWorkFactory {
	Worker getWorker();
}

class StudentWorkFactory implements IWorkFactory {
	public Worker getWorker() {
		return new StudentWorker();
	}
}

class TeacherWorkFactory implements IWorkFactory {
	public Worker getWorker() {
		return new TeacherWorker();
	}
}