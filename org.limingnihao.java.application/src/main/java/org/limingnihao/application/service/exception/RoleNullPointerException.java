package org.limingnihao.application.service.exception;

public class RoleNullPointerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RoleNullPointerException() {
		super("当前角色已不存在，操作失败！");
	}

	public RoleNullPointerException(String message) {
		super(message);
	}

}
