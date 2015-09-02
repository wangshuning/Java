package org.limingnihao.application.service.exception;

public class UserUsingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserUsingException() {
		super("用户正在使用中，操作失败！");
	}

	public UserUsingException(String message) {
		super(message);
	}

}
