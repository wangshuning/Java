package org.limingnihao.application.service.exception;

public class RegionUsingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RegionUsingException() {
		super("当前区域正在使用中，操作失败！");
	}

	public RegionUsingException(String message) {
		super(message);
	}

}
