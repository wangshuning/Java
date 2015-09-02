package org.limingnihao.application.type;

import java.io.Serializable;

/**
 * 属性类型
 */
public enum PropertyFlagType implements Serializable {

	/** -----------------------------------系统基本配置信息------------------------------------------ */

	/**
	 * 项目名称
	 */
	PROJECT_NAME("PROJECT_NAME"),

	/**
	 * 版本管理 - 保存路径
	 */
	VERSION_SAVE_PATH("VERSION_SAVE_PATH"),

	/**
	 * 版本管理 - 访问路径
	 */
	VERSION_HTTP_PATH("VERSION_HTTP_PATH"),

	/**
	 * 日志上传 - 保存路径
	 */
	LOGFILE_SAVE_PATH("LOGFILE_SAVE_PATH"),

	/**
	 * 日志上传 - 访问路径
	 */
	LOGFILE_HTTP_PATH("LOGFILE_HTTP_PATH"),

	/**
	 * 是否进行OPENFIRE同步
	 */
	IS_SYN_USERNAME("IS_SYN_USERNAME");

	public static PropertyFlagType valueOf(int i) {
		for (PropertyFlagType value : values()) {
			if (value.equals(i)) {
				return value;
			}
		}
		return null;
	}

	private String value;

	PropertyFlagType(String value) {
		this.value = value;
	}

	public boolean equals(String value) {
		return this.value.equals(value);
	}

	public String value() {
		return this.value;
	}

}
