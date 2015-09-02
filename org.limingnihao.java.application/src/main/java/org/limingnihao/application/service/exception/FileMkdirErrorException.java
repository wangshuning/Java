package org.limingnihao.application.service.exception;

public class FileMkdirErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileMkdirErrorException() {
		super("创建文件夹失败！");
	}

	public FileMkdirErrorException(String message) {
		super(message);
	}
}
