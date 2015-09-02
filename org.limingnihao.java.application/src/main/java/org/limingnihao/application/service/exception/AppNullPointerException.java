package org.limingnihao.application.service.exception;

public class AppNullPointerException extends RuntimeException {

	private static final long serialVersionUID = -3211015891228202257L;

	public AppNullPointerException() {
		super("空指针异常!");
	}

	public AppNullPointerException(String message) {
		super(message);
	}
}
