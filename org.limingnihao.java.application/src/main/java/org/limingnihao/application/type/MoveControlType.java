package org.limingnihao.application.type;

public enum MoveControlType {

	/** 0.向上 */
	UP(0),

	/** 1.向下 */
	DOWN(1);

	public static MoveControlType valueOf(int i) {
		for (MoveControlType value : values()) {
			if (value.equals(i)) {
				return value;
			}
		}
		return null;
	}

	private int value;

	MoveControlType(int value) {
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

	public int value() {
		return this.value;
	}

}
