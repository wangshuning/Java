package org.limingnihao.application.service.model;

public class ApplicationBean {
	private Integer uid;
	private String propertyName;
	private String propertyFlag;
	private String propertyValue;
	private String description;
	private int systemType;

	public String getDescription() {
		return description;
	}

	public String getPropertyFlag() {
		return propertyFlag;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public int getSystemType() {
		return systemType;
	}

	public Integer getUid() {
		return uid;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPropertyFlag(String propertyFlag) {
		this.propertyFlag = propertyFlag;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public void setSystemType(int systemType) {
		this.systemType = systemType;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

}
