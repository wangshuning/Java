package org.limingnihao.application.service.model;

public class ProgressBean {

	/** 当前读取文件的比特数(字节B) */
	private long readBytes = 0L;

	/** 文件总大小(字节B) */
	private long contentLength = 0L;

	/** 目当前正在读取第几个文件 */
	private int readItem;

	/** 当前下载百分比(0-100) */
	private int percent;

	/** 百分比字符串形式 */
	private String percentString;

	public long getContentLength() {
		return contentLength;
	}

	public int getPercent() {
		return percent;
	}

	public String getPercentString() {
		return percentString;
	}

	public long getReadBytes() {
		return readBytes;
	}

	public int getReadItem() {
		return readItem;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public void setPercentString(String percentString) {
		this.percentString = percentString;
	}

	public void setReadBytes(long readBytes) {
		this.readBytes = readBytes;
	}

	public void setReadItem(int readItem) {
		this.readItem = readItem;
	}

	public String toString() {
		return "ProgressBean - readBytes=" + readBytes + ", contentLength=" + contentLength + ", readItem=" + readItem + ", percent=" + percent + ", percentString=" + percentString;
	}

}
