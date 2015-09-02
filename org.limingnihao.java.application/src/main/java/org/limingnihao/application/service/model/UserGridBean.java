package org.limingnihao.application.service.model;

public class UserGridBean extends UserBean {

	private String createTime;
	private String lastTime;

	private int roleId;
	private String roleName;

	private int groupId;
	private String groupName;

	public String getCreateTime() {
		return createTime;
	}

	public int getGroupId() {
		return groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getLastTime() {
		return lastTime;
	}

	public int getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
