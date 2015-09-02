package org.limingnihao.application.service.exception;

public class GroupNameExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GroupNameExistsException() {
		super("当前名字已经存在，请重新换一个名字！");
	}

	public GroupNameExistsException(String message) {
		super(message);
	}

}
