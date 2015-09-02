package org.limingnihao.application.data.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "app_resource_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "defaultCache")
public class ResourceEntity extends PersistenceEntity {

	private static final long serialVersionUID = -7610239223785738150L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "resource_id", unique = true, nullable = false)
	private Integer resourceId;

	@Column(name = "system_type", nullable = false)
	private Integer systemType;

	@Column(name = "resource_name", nullable = false)
	private String resourceName;

	@Column(name = "resource_url", nullable = false)
	private String resourceUrl;

	@Column(name = "resource_type", nullable = false)
	private Integer resourceType;

	@Column(name = "sequence", nullable = false)
	private Integer sequence;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "parent_id", nullable = true)
	private ResourceEntity parentEntity;

	@Column(name = "use_flag", nullable = false)
	private Integer useFlag;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentEntity", orphanRemoval = true)
	@OrderBy("sequence")
	private List<ResourceEntity> childrenList = new ArrayList<ResourceEntity>();

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "app_authority_resource_rel", joinColumns = @JoinColumn(name = "resource_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
	private List<AuthorityEntity> authorityList = new ArrayList<AuthorityEntity>();

	public List<AuthorityEntity> getAuthorityList() {
		return authorityList;
	}

	public List<ResourceEntity> getChildrenList() {
		return childrenList;
	}

	public ResourceEntity getParentEntity() {
		return parentEntity;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public Integer getSequence() {
		return sequence;
	}

	public Integer getSystemType() {
		return systemType;
	}

	public Integer getUseFlag() {
		return useFlag;
	}

	public void setAuthorityList(List<AuthorityEntity> authorityList) {
		this.authorityList = authorityList;
	}

	public void setChildrenList(List<ResourceEntity> childrenList) {
		this.childrenList = childrenList;
	}

	public void setParentEntity(ResourceEntity parentEntity) {
		this.parentEntity = parentEntity;
	}

	protected void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public void setSystemType(Integer systemType) {
		this.systemType = systemType;
	}

	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}

}
