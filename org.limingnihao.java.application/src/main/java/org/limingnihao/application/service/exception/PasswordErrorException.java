package org.limingnihao.application.service.exception;

public class PasswordErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PasswordErrorException() {
		super("用户名或密码不正确！");
	}

	public PasswordErrorException(String message) {
		super(message);
	}
}
