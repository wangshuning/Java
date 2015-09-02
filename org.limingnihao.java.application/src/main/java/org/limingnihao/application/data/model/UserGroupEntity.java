package org.limingnihao.application.data.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "app_user_group_rel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "defaultCache")
public class UserGroupEntity extends PersistenceEntity {

	private static final long serialVersionUID = 9005217747566321315L;

	@Id
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity userEntity;

	@Id
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	private GroupEntity groupEntity;

	@Id
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	private RoleEntity roleEntity;

	@Override
	public boolean equals(Object obj) {
		try {
			UserGroupEntity target = (UserGroupEntity) obj;
			if (this.userEntity.getUserId().intValue() == target.getUserEntity().getUserId().intValue() && this.groupEntity.getGroupId().intValue() == target.getGroupEntity().getGroupId().intValue()
					&& this.roleEntity.getRoleId().intValue() == target.getRoleEntity().getRoleId().intValue()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public GroupEntity getGroupEntity() {
		return groupEntity;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setGroupEntity(GroupEntity groupEntity) {
		this.groupEntity = groupEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public RoleEntity getRoleEntity() {
		return roleEntity;
	}

	public void setRoleEntity(RoleEntity roleEntity) {
		this.roleEntity = roleEntity;
	}

}
