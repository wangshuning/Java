package org.limingnihao.application.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "app_attribute_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "defaultCache")
public class AttributeEntity extends PersistenceEntity  {

	private static final long serialVersionUID = -2822133366395867625L;

	@Id
	@Column(name = "uid", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer uid;

	@Column(name = "system_type", nullable = false)
	private Integer systemType;

	@Column(name = "attribute_flag", nullable = false)
	private String attributeFlag;

	@Column(name = "attribute_name")
	private String attributeName;

	@Column(name = "attribute_value", nullable = false)
	private Integer attributeValue;

	@Column(name = "description")
	private String description;

	public String getAttributeFlag() {
		return attributeFlag;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public Integer getAttributeValue() {
		return attributeValue;
	}

	public String getDescription() {
		return description;
	}

	public Integer getSystemType() {
		return systemType;
	}

	public Integer getUid() {
		return uid;
	}

	public void setAttributeFlag(String attributeFlag) {
		this.attributeFlag = attributeFlag;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public void setAttributeValue(Integer attributeValue) {
		this.attributeValue = attributeValue;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSystemType(Integer systemType) {
		this.systemType = systemType;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

}
