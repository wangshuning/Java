package org.limingnihao.application.service.model;

public class RoleBean {

	private int roleId;
	private String roleName;
	private int useFlag;
	private String useFlagName;
	private int[] authorityIds;
	private String[] authorityNames;
	private AuthorityBean[] authoritys;

	@Override
	public String toString() {
		String output = "RoleBean - roleId=" + roleId + ", roleName=" + roleName;
		return output;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public int[] getAuthorityIds() {
		return authorityIds;
	}

	public void setAuthorityIds(int[] authorityIds) {
		this.authorityIds = authorityIds;
	}

	public String[] getAuthorityNames() {
		return authorityNames;
	}

	public void setAuthorityNames(String[] authorityNames) {
		this.authorityNames = authorityNames;
	}

	public AuthorityBean[] getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(AuthorityBean[] authoritys) {
		this.authoritys = authoritys;
	}

}
