package org.limingnihao.application.service.model;

public class AuthorityBean {

	private int authorityId;
	private String authorityName;
	private String authorityFlag;
	private int useFlag;
	private String useFlagName;
	private int[] resourceIds;
	private String[] resourceNames;
	private ResourceBean[] resources;

	public int getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(int authorityId) {
		this.authorityId = authorityId;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public String getAuthorityFlag() {
		return authorityFlag;
	}

	public void setAuthorityFlag(String authorityFlag) {
		this.authorityFlag = authorityFlag;
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

	public int[] getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(int[] resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String[] getResourceNames() {
		return resourceNames;
	}

	public void setResourceNames(String[] resourceNames) {
		this.resourceNames = resourceNames;
	}

	public ResourceBean[] getResources() {
		return resources;
	}

	public void setResources(ResourceBean[] resources) {
		this.resources = resources;
	}

}
