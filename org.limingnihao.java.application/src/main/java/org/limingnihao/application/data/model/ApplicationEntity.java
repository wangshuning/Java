package org.limingnihao.application.data.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 系统配置表
 */
@Entity
@Table(name = "app_application_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "defaultCache")
public class ApplicationEntity extends PersistenceEntity {

	private static final long serialVersionUID = -636002527401541083L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "property_id", unique = true, nullable = false)
	private Integer propertyId;

	@Column(name = "system_type", nullable = false)
	private Integer systemType;

	@Column(name = "property_name", nullable = false)
	private String propertyName;

	@Column(name = "property_flag", nullable = false)
	private String propertyFlag;

	@Column(name = "property_value", nullable = false)
	private String propertyValue;

	@Column(name = "description", nullable = true)
	private String description;

	public String getDescription() {
		return description;
	}

	public String getPropertyFlag() {
		return propertyFlag;
	}

	public Integer getPropertyId() {
		return propertyId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public Integer getSystemType() {
		return systemType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPropertyFlag(String propertyFlag) {
		this.propertyFlag = propertyFlag;
	}

	protected void setPropertyId(Integer propertyId) {
		this.propertyId = propertyId;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public void setSystemType(Integer systemType) {
		this.systemType = systemType;
	}

}