package org.limingnihao.application.type;

import java.io.Serializable;

/**
 * 默认状态
 */
public enum PredefinedType implements Serializable {

	/** 非默认 */
	NO(0),

	/** 默认的 */
	YES(1);

	private int value;

	PredefinedType(int value) {
		this.value = value;
	}

	public static PredefinedType valueOf(Integer i) {
		if (i != null) {
			return valueOf(i.intValue());
		} else {
			return null;
		}
	}

	public static PredefinedType valueOf(int i) {
		for (PredefinedType value : values()) {
			if (value.equals(i)) {
				return value;
			}
		}
		return null;
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

	public int value() {
		return this.value;
	}

}
