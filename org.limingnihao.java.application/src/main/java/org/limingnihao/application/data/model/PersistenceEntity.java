package org.limingnihao.application.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class PersistenceEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Version
	@Column(name = "h_version")
	private Integer version;

//	@Column(name = "h_deleted")
//	private Integer deleted = 0;

	//
	// @Column(name = "h_modify")
	// @Temporal(value = TemporalType.TIMESTAMP)
	// private Date modify
	//

	public Integer getVersion() {
		return version;
	}

	protected void setVersion(Integer version) {
		this.version = version;
	}

//	public Integer getDeleted() {
//		return deleted;
//	}
//
//	public void setDeleted(Integer deleted) {
//		this.deleted = deleted;
//	}

}
