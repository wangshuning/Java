package org.limingnihao.application.service.exception;

public class FileNullPointerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileNullPointerException() {
		super("文件不存在！");
	}

	public FileNullPointerException(String message) {
		super(message);
	}

}
