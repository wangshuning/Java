package org.limingnihao.application.service.exception;

public class MessageServiceErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MessageServiceErrorException() {
		super("XMPP服务器异常，操作失败！");
	}

	public MessageServiceErrorException(String message) {
		super(message);
	}

}
