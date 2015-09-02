package org.limingnihao.application.service.exception;

public class RegionNameExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RegionNameExistsException() {
		super("当前区域名称已经存在，请重新换一个名字！");
	}

	public RegionNameExistsException(String message) {
		super(message);
	}

}
