package org.limingnihao.application.service.exception;

public class FolderNullPointerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FolderNullPointerException() {
		super("目录不存在");
	}

	public FolderNullPointerException(String message) {
		super(message);
	}

}
