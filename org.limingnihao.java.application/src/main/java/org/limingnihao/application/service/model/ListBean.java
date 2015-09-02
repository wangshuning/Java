package org.limingnihao.application.service.model;

import java.util.List;

public class ListBean<T> {

	/**
	 * 当前页数
	 */
	private int pageNow = 0;

	/**
	 * 总页数
	 */
	private int pageTotal = 0;

	/**
	 * 总个数
	 */
	private int numberTotal = 0;

	/**
	 * 列表
	 */
	private List<T> beanList;

	public List<T> getBeanList() {
		return beanList;
	}

	public int getNumberTotal() {
		return numberTotal;
	}

	public int getPageNow() {
		return pageNow;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}

	public void setNumberTotal(int numberTotal) {
		this.numberTotal = numberTotal;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

}
