package org.limingnihao.application.service.model;

public class AsyncTreeNode {

	protected String id;
	protected String text; // 结点显示名称
	protected boolean leaf; // 是否为叶子结点
	protected String icon; // 结点图标样式
	protected String href; // 点击后时的链接

	public String getHref() {
		return href;
	}

	public String getIcon() {
		return icon;
	}

	public String getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public void setText(String text) {
		this.text = text;
	}

}
