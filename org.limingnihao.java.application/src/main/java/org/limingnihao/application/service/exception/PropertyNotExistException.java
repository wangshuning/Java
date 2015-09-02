package org.limingnihao.application.service.exception;

public class PropertyNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PropertyNotExistException() {
		super("属性没配置！");
	}

	public PropertyNotExistException(String message) {
		super(message);
	}
}
