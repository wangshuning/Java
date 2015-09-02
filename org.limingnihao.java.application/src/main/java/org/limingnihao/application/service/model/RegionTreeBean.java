package org.limingnihao.application.service.model;

import java.util.List;

public class RegionTreeBean extends AsyncTreeNode {

	private int regionId;
	private String regionName;
	private int parentId;
	private String parentName;
	private int sequence;
	private int useFlag;
	private String useFlagName;
	private List<RegionTreeBean> children;

	public List<RegionTreeBean> getChildren() {
		return children;
	}

	public int getParentId() {
		return parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public int getRegionId() {
		return regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public int getSequence() {
		return sequence;
	}

	public int getUseFlag() {
		return useFlag;
	}

	public void setChildren(List<RegionTreeBean> children) {
		this.children = children;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public void setRegionId(int regionId) {
		this.setId("" + regionId);
		this.regionId = regionId;
	}

	public void setRegionName(String regionName) {
		this.setText(regionName);
		this.regionName = regionName;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
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

}
