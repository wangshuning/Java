package org.limingnihao.application.service.model;

import java.util.ArrayList;
import java.util.List;

public class GroupBean {

	private Integer groupId;
	private String groupName;
	private Integer groupType;
	private Integer parentId;
	private String parentName;
	private String description;
	private Integer sequence;
	private Integer regionId;
	private String regionName;
	private Integer useFlag;
	private String useFlagName;
	private List<GroupBean> childrenList = new ArrayList<GroupBean>();

	public String getDescription() {
		return description;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	public Integer getParentId() {
		return parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public Integer getSequence() {
		return sequence;
	}

	public Integer getUseFlag() {
		return useFlag;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}

	public String getUseFlagName() {
		return useFlagName;
	}

	public void setUseFlagName(String useFlagName) {
		this.useFlagName = useFlagName;
	}

	public List<GroupBean> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<GroupBean> childrenList) {
		this.childrenList = childrenList;
	}

	@Override
	public String toString() {
		return "GroupBean [groupId=" + groupId + ", groupName=" + groupName + ", parentId=" + parentId + ", parentName=" + parentName + ", description=" + description + ", sequence=" + sequence
				+ ", regionId=" + regionId + ", useFlag=" + useFlag + "]";
	}

}
