package org.limingnihao.application.service.exception;

public class UserNullPointerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNullPointerException() {
		super("当前用户不存在，操作失败！");
	}

	public UserNullPointerException(String message) {
		super(message);
	}

}
