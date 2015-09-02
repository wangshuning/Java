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
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "app_role_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "defaultCache")
public class RoleEntity extends PersistenceEntity {

	private static final long serialVersionUID = -2681491236195698530L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id", unique = true, nullable = false)
	private Integer roleId;

	@Column(name = "role_name", nullable = false)
	private String roleName;

	@Column(name = "system_type", nullable = false)
	private Integer systemType;

	@Column(name = "use_flag", nullable = false)
	private Integer useFlag;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "app_role_authority_rel", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
	private List<AuthorityEntity> authorityList = new ArrayList<AuthorityEntity>();

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "app_user_role_rel", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<UserEntity> userList = new ArrayList<UserEntity>();;

	public List<AuthorityEntity> getAuthorityList() {
		return authorityList;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public Integer getSystemType() {
		return systemType;
	}

	public Integer getUseFlag() {
		return useFlag;
	}

	public List<UserEntity> getUserList() {
		return userList;
	}

	public void setAuthorityList(List<AuthorityEntity> authorityList) {
		this.authorityList = authorityList;
	}

	protected void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setSystemType(Integer systemType) {
		this.systemType = systemType;
	}

	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}

	public void setUserList(List<UserEntity> userList) {
		this.userList = userList;
	}

}
