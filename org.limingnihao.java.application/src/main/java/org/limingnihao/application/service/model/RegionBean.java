package org.limingnihao.application.service.model;

public class RegionBean {

	private Integer regionId;
	private String regionName;
	private String regionCode;
	private String mapName;
	private Integer parentId;
	private String parentName;
	private Integer sequence;
	private Integer useFlag;
	private String useFlagName;

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

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
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

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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

	@Override
	public String toString() {
		return "RegionBean [regionId=" + regionId + ", regionName=" + regionName + ", mapName=" + mapName + ", parentId=" + parentId + ", parentName=" + parentName + ", sequence=" + sequence + "]";
	}

}
