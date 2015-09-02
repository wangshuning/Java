package org.limingnihao.application.service.exception;

public class GroupUsingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GroupUsingException() {
		super("当前组织结构正在使用中，不能进行删除操作！");
	}

	public GroupUsingException(String message) {
		super(message);
	}

}
