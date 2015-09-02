package org.limingnihao.application.service.exception;

public class VersionExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VersionExistsException() {
		super("版本已经存在，请更换版本号！");
	}

	public VersionExistsException(String message) {
		super(message);
	}

}
