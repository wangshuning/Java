package org.limingnihao.application.service.model;

import java.util.List;

public class GroupTreeBean extends AsyncTreeNode {

	private Integer groupId;
	private String groupName;
	private Integer parentId;
	private String parentName;
	private String description;
	private Integer sequence;
	private Integer regionId;
	private String regionName;
	private Integer useFlag;
	private String useFlagName;
	private List<GroupTreeBean> children;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.id = groupId + "";
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.text = groupName;
		this.groupName = groupName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getRegionId() {
		return regionId;
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

	public Integer getUseFlag() {
		return useFlag;
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

	public List<GroupTreeBean> getChildren() {
		return children;
	}

	public void setChildren(List<GroupTreeBean> children) {
		this.children = children;
	}

}
