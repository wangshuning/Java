package org.limingnihao.application.service.model;

public class AttributeBean {

	private String attributeName;
	private int attributeValue;

	public String getAttributeName() {
		return attributeName;
	}

	public int getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public void setAttributeValue(int attributeValue) {
		this.attributeValue = attributeValue;
	}

	@Override
	public String toString() {
		String output = "AttributeBean - attributeName=" + this.attributeName + ", attributeValue=" + this.attributeValue;
		return output;
	}

}
