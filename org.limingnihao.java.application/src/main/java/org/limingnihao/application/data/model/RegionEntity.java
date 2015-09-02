package org.limingnihao.application.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "app_region_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "defaultCache")
public class RegionEntity extends PersistenceEntity {

	private static final long serialVersionUID = 3731359829220413517L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "region_id", unique = true, nullable = false)
	private Integer regionId;

	@Column(name = "region_code", nullable = true)
	private String regionCode;

	@Column(name = "region_name", nullable = false)
	private String regionName;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "parent_id", nullable = true)
	private RegionEntity parentEntity;

	@Column(name = "sequence", nullable = false)
	private Integer sequence;

	@Column(name = "map_name", nullable = true)
	private String mapName;

	@Column(name = "use_flag", nullable = false)
	private Integer useFlag;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentEntity", orphanRemoval = true)
	@OrderBy("sequence")
	private List<RegionEntity> childrenList = new ArrayList<RegionEntity>();

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "regionEntity")
	private List<GroupEntity> groupList = new ArrayList<GroupEntity>();

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public RegionEntity getParentEntity() {
		return parentEntity;
	}

	public void setParentEntity(RegionEntity parentEntity) {
		this.parentEntity = parentEntity;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public Integer getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}

	public List<RegionEntity> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<RegionEntity> childrenList) {
		this.childrenList = childrenList;
	}

	public List<GroupEntity> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<GroupEntity> groupList) {
		this.groupList = groupList;
	}

}
