package org.limingnihao.application.data.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "app_user_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "defaultCache")
public class UserEntity extends PersistenceEntity {

	private static final long serialVersionUID = 3572673728696807848L;

	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	@GeneratedValue(strategy = IDENTITY)
	private Integer userId;

	@Column(name = "user_name", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "nick_name", nullable = false)
	private String nickname;

	@Column(name = "create_time", nullable = false)
	private Timestamp createTime;

	@Column(name = "last_time", nullable = true)
	private Timestamp lastTime;

	@Column(name = "user_type", nullable = false)
	private Integer userType;

	@Column(name = "use_flag", nullable = false)
	private Integer useFlag;
	
	@Column(name = "online", nullable = true)
	private Integer online;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "app_user_group_rel", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
	@OrderBy("sequence")
	private List<GroupEntity> groupList = new ArrayList<GroupEntity>();

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "app_user_role_rel", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<RoleEntity> roleList = new ArrayList<RoleEntity>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userEntity", orphanRemoval = true)
	private List<UserGroupEntity> userGroupList = new ArrayList<UserGroupEntity>();

	public Integer getUserId() {
		return userId;
	}

	protected void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastTime() {
		return lastTime;
	}

	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public List<GroupEntity> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<GroupEntity> groupList) {
		this.groupList = groupList;
	}

	public List<RoleEntity> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleEntity> roleList) {
		this.roleList = roleList;
	}

	public List<UserGroupEntity> getUserGroupList() {
		return userGroupList;
	}

	public void setUserGroupList(List<UserGroupEntity> userGroupList) {
		this.userGroupList = userGroupList;
	}

}
