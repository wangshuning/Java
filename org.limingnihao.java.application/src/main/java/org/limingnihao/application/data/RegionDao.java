package org.limingnihao.application.data;

import java.util.List;

import org.limingnihao.application.data.model.RegionEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RegionDao extends GenericDao<RegionEntity, Integer> {

	/**
	 * 根据regionCode
	 */
	public abstract RegionEntity getEntityByRegionCode(String regionCode);

	/**
	 * 根据regionName获取区域
	 */
	public abstract RegionEntity getEntityByRegionName(String regionName, Integer parentRegionId);

	/**
	 * 对象列表 - 有地图信息的区域
	 */
	public abstract List<RegionEntity> getListByHasMapFile();

	/**
	 * 对象列表 - 根据parentRegionId
	 */
	public abstract List<RegionEntity> getListByParentId(Integer parentId, Integer useFlag, int firstResult, int maxResults);

	public abstract Integer getListByParentId_count(Integer parentId, Integer useFlag);

	/**
	 * 对象列表 - 最顶层的
	 */
	public abstract List<RegionEntity> getListByRoot();

	/**
	 * 对象列表 - 用户所在区域
	 */
	public abstract List<RegionEntity> getListByUserId(Integer userId);

	/**
	 * 天气接口使用 - 根据用户所在地获得地区
	 * 
	 * @param localName
	 * @return
	 */
	public abstract RegionEntity getEntityByLocalName(String localName);
}