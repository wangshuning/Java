package org.limingnihao.application.type;

import java.io.Serializable;

/**
 * 使用状态
 */
public enum UseFlagType implements Serializable {

	/** 禁用 */
	DISABLED(0),

	/** 启用 */
	ENABLED(1);

	public static UseFlagType valueOf(int i) {
		for (UseFlagType value : values()) {
			if (value.equals(i)) {
				return value;
			}
		}
		return null;
	}

	private int value;

	UseFlagType(int value) {
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
		case DISABLED:
			return "禁用";
		case ENABLED:
			return "启用";
		default:
			return "未定义";
		}
	}

	public int value() {
		return this.value;
	}

}
