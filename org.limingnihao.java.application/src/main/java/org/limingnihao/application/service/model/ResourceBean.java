package org.limingnihao.application.service.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResourceBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int resourceId;
	private String resourceName;
	private String resourceUrl;
	private int resourceType;
	private int sequence;
	private int parentId;
	private int useFlag;
	private List<ResourceBean> childrenList;
	private List<String> authorityFlagList;

	@Override
	public String toString() {
		return "ResourceBean - [resourceId=" + resourceId + ", resourceName=" + resourceName + ", resourceUrl=" + resourceUrl + ", resourceType=" + resourceType + ", sequence=" + sequence
				+ ", parentId=" + parentId + ", useFlag=" + useFlag + "]";
	}

	public ResourceBean() {
		this.childrenList = new ArrayList<ResourceBean>();
		this.authorityFlagList = new ArrayList<String>();
	}

	public List<String> getAuthorityFlagList() {
		return authorityFlagList;
	}

	public String getAuthorityFlagString() {
		return (this.authorityFlagList != null ? "" + Arrays.toString(this.authorityFlagList.toArray()) : "null");
	}

	public List<ResourceBean> getChildrenList() {
		return childrenList;
	}

	public int getParentId() {
		return parentId;
	}

	public int getResourceId() {
		return resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public int getResourceType() {
		return resourceType;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public int getSequence() {
		return sequence;
	}

	public int getUseFlag() {
		return useFlag;
	}

	public void setAuthorityFlagList(List<String> authorityFlagList) {
		this.authorityFlagList = authorityFlagList;
	}

	public void setChildrenList(List<ResourceBean> childrenList) {
		this.childrenList = childrenList;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public void setUseFlag(int useFlag) {
		this.useFlag = useFlag;
	}

}
