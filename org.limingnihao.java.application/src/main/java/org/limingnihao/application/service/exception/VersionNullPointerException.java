package org.limingnihao.application.service.exception;

public class VersionNullPointerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VersionNullPointerException() {
		super("版本信息不存在，操作失败！");
	}

	public VersionNullPointerException(String message) {
		super(message);
	}

}
