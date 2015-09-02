package org.limingnihao.application.data.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "app_user_detail_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "defaultCache")
public class UserDetailEntity extends PersistenceEntity {

	private static final long serialVersionUID = 919319936967669568L;

	@Id
	@Column(name = "uid", unique = true, nullable = false)
	@GeneratedValue(strategy = IDENTITY)
	private Integer uid;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity userEntity;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "value", nullable = false)
	private String value;

	@Column(name = "description", nullable = true)
	private String description;

	public Integer getUid() {
		return uid;
	}

	protected void setUid(Integer uid) {
		this.uid = uid;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
