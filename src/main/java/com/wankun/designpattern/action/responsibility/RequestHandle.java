package com.wankun.designpattern.action.responsibility;

/**
 * @author wankun
 */
public abstract class RequestHandle {
	protected RequestHandle successor;

	public RequestHandle(RequestHandle successor) {
		this.successor = successor;
	}

	protected abstract void handleRequest(Request request);
}

class HRRequestHandle extends RequestHandle {

	public HRRequestHandle() {
		super(null);
	}

	public void handleRequest(Request request) {
		if (request instanceof DimissionRequest) {
			System.out.println("要离职, 人事审批!");
		}

		System.out.println("请求完毕");
	}
}

class PMRequestHandle extends RequestHandle {

	public PMRequestHandle(RequestHandle successor) {
		super(successor);
	}

	public void handleRequest(Request request) {
		if (request instanceof AddMoneyRequest) {
			System.out.println("要加薪, 项目经理审批!");
		} else {
			successor.handleRequest(request);
		}
	}
}

class TLRequestHandle extends RequestHandle {
	public TLRequestHandle(RequestHandle successor) {
		super(successor);
	}

	public void handleRequest(Request request) {
		if (request instanceof LeaveRequest) {
			System.out.println("要请假, 项目组长审批!");
		} else {
			successor.handleRequest(request);
		}
	}
}
