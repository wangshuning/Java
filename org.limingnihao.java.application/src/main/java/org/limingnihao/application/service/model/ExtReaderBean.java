package org.limingnihao.application.service.model;

/**
 * 用于给ext的GridPanel等请求数据带分页时使用
 */
public class ExtReaderBean {

	private Object[] dataArray;
	private int totalSize;

	public Object[] getDataArray() {
		return dataArray;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setDataArray(Object[] dataArray) {
		this.dataArray = dataArray;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

}
