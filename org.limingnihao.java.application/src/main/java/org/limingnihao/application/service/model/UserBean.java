package org.limingnihao.application.service.model;

import java.util.List;

public class UserBean {

	private int userId;
	private String username;
	private String nickname;
	private String password;
	private int userType;
	private String userTypeName;
	private String createTime;
	private String lastTime;
	private int useFlag;
	private String useFlagName;
	private int isOnline;

	private int[] groupIds;
	private String[] groupNames;
	private int[] roleIds;
	private String[] roleNames;
	private List<GroupBean> groupBeanList;
	private List<RoleBean> roleBeanList;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public int getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(int useFlag) {
		this.useFlag = useFlag;
	}

	public String getUseFlagName() {
		return useFlagName;
	}

	public void setUseFlagName(String useFlagName) {
		this.useFlagName = useFlagName;
	}

	public int getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}

	public int[] getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(int[] groupIds) {
		this.groupIds = groupIds;
	}

	public String[] getGroupNames() {
		return groupNames;
	}

	public void setGroupNames(String[] groupNames) {
		this.groupNames = groupNames;
	}

	public int[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(int[] roleIds) {
		this.roleIds = roleIds;
	}

	public String[] getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String[] roleNames) {
		this.roleNames = roleNames;
	}

	public List<GroupBean> getGroupBeanList() {
		return groupBeanList;
	}

	public void setGroupBeanList(List<GroupBean> groupBeanList) {
		this.groupBeanList = groupBeanList;
	}

	public List<RoleBean> getRoleBeanList() {
		return roleBeanList;
	}

	public void setRoleBeanList(List<RoleBean> roleBeanList) {
		this.roleBeanList = roleBeanList;
	}

}
