package org.limingnihao.application.type;

import java.io.Serializable;

/**
 * 资源类型
 */
public enum ResourceType implements Serializable {

	/** menu */
	MENU(0),

	/** url */
	URL(1);

	public static ResourceType valueOf(int i) {
		for (ResourceType value : values()) {
			if (value.equals(i)) {
				return value;
			}
		}
		return null;
	}

	private int value;

	ResourceType(int value) {
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
		case MENU:
			return "menu";
		case URL:
			return "url";
		default:
			return "null";
		}
	}

	public int value() {
		return this.value;
	}

}
