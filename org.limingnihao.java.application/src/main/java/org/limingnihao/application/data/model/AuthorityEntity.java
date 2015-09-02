package org.limingnihao.application.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "app_authority_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "defaultCache")
public class AuthorityEntity extends PersistenceEntity  {

	private static final long serialVersionUID = 5340666840232834754L;

	@Id
	@Column(name = "authority_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer authorityId;

	@Column(name = "system_type", nullable = false)
	private Integer systemType;

	@Column(name = "authority_name", nullable = false)
	private String authorityName;

	@Column(name = "authority_flag", nullable = false)
	private String authorityFlag;

	@Column(name = "use_flag", nullable = false)
	private Integer useFlag;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "app_authority_resource_rel", joinColumns = @JoinColumn(name = "authority_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
	@OrderBy("sequence")
	private List<ResourceEntity> resourceList = new ArrayList<ResourceEntity>();

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "app_role_authority_rel", joinColumns = @JoinColumn(name = "authority_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<RoleEntity> roleList = new ArrayList<RoleEntity>();

	public String getAuthorityFlag() {
		return authorityFlag;
	}

	public Integer getAuthorityId() {
		return authorityId;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public List<ResourceEntity> getResourceList() {
		return resourceList;
	}

	public List<RoleEntity> getRoleList() {
		return roleList;
	}

	public Integer getSystemType() {
		return systemType;
	}

	public Integer getUseFlag() {
		return useFlag;
	}

	public void setAuthorityFlag(String authorityFlag) {
		this.authorityFlag = authorityFlag;
	}

	protected void setAuthorityId(Integer authorityId) {
		this.authorityId = authorityId;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public void setResourceList(List<ResourceEntity> resourceList) {
		this.resourceList = resourceList;
	}

	public void setRoleList(List<RoleEntity> roleList) {
		this.roleList = roleList;
	}

	public void setSystemType(Integer systemType) {
		this.systemType = systemType;
	}

	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}

}
