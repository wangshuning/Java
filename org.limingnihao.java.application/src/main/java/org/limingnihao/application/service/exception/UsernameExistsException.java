package org.limingnihao.application.service.exception;

public class UsernameExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsernameExistsException() {
		super("用户名已经存在，请尝试更换一个！");
	}

	public UsernameExistsException(String message) {
		super(message);
	}

}
