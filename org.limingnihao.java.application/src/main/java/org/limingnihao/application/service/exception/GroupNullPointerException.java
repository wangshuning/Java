package org.limingnihao.application.service.exception;

public class GroupNullPointerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GroupNullPointerException() {
		super("用户组不存在，操作失败！");
	}

	public GroupNullPointerException(String message) {
		super(message);
	}

}
