package org.limingnihao.application.service.exception;

public class FileTypeErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileTypeErrorException() {
		super("文件类型不正确！");
	}

	public FileTypeErrorException(String message){
		super(message);
	}
}
