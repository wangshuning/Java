package org.limingnihao.application.service.exception;

public class RoleUsingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RoleUsingException() {
		super("当前角色正在使用中，操作失败！");
	}

	public RoleUsingException(String message) {
		super(message);
	}
}
