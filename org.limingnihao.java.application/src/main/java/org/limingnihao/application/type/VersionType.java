package org.limingnihao.application.type;

import java.io.Serializable;

/**
 * 版本类型
 */
public enum VersionType implements Serializable {

	/** 必须的required */
	REQUIRED(0),

	/** 可选的optional */
	OPTIONAL(1);

	public static VersionType valueOf(int i) {
		for (VersionType value : values()) {
			if (value.equals(i)) {
				return value;
			}
		}
		return null;
	}

	private int value;

	VersionType(int value) {
		this.value = value;
	}

	public boolean equals(int value) {
		return this.value == value;
	}

	public boolean equals(Integer value) {
		if (value != null) {
			return this.value == value.intValue();
		} else {
			return false;
		}
	}

	public String getName() {
		switch (this) {
			case REQUIRED:
				return "必须的";
			case OPTIONAL:
				return "可选的";
			default:
				return "未定义";
		}
	}

	public int value() {
		return this.value;
	}

}
