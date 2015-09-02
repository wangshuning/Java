package org.limingnihao.application.service.exception;

public class FileSizeBiggestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileSizeBiggestException() {
		super("文件过大！");
	}

	public FileSizeBiggestException(String message) {
		super(message);
	}
}
