package org.limingnihao.application.service.exception;

public class RegionHasChildException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer id;

	public RegionHasChildException() {
		super("");
	}

	public RegionHasChildException(Integer id) {
		super("id=" + id);
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
