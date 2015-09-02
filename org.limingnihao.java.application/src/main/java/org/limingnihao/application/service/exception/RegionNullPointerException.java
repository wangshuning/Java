package org.limingnihao.application.service.exception;

public class RegionNullPointerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RegionNullPointerException() {
		super("当前区域不存在，请重新尝试！");
	}

	public RegionNullPointerException(String message) {
		super(message);
	}

}
