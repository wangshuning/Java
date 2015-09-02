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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "app_group_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "defaultCache")
public class GroupEntity extends PersistenceEntity {

	private static final long serialVersionUID = -1524162527538152336L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id", unique = true, nullable = false)
	private Integer groupId;

	@Column(name = "group_name", nullable = false)
	private String groupName;

	@Column(name = "group_type", nullable = true)
	private Integer groupType;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "parent_id", nullable = true)
	private GroupEntity parentEntity;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "region_id", nullable = true)
	private RegionEntity regionEntity;

	@Column(name = "sequence", nullable = false)
	private Integer sequence;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "use_flag", nullable = false)
	private Integer useFlag;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentEntity", orphanRemoval = true)
	@OrderBy("sequence")
	private List<GroupEntity> childrenList = new ArrayList<GroupEntity>();

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "app_user_group_rel", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<UserEntity> userList = new ArrayList<UserEntity>();

	public Integer getGroupId() {
		return groupId;
	}

	protected void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	public GroupEntity getParentEntity() {
		return parentEntity;
	}

	public void setParentEntity(GroupEntity parentEntity) {
		this.parentEntity = parentEntity;
	}

	public RegionEntity getRegionEntity() {
		return regionEntity;
	}

	public void setRegionEntity(RegionEntity regionEntity) {
		this.regionEntity = regionEntity;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}

	public List<GroupEntity> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<GroupEntity> childrenList) {
		this.childrenList = childrenList;
	}

	public List<UserEntity> getUserList() {
		return userList;
	}

	public void setUserList(List<UserEntity> userList) {
		this.userList = userList;
	}

}
