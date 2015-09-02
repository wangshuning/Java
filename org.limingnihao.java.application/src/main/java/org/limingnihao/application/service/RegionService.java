package org.limingnihao.application.service;

import java.util.List;

import org.limingnihao.application.service.exception.RegionNameExistsException;
import org.limingnihao.application.service.exception.RegionUsingException;
import org.limingnihao.application.service.model.RegionBean;
import org.limingnihao.application.service.model.RegionTreeBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RegionService {

	/**
	 * 新增或更新
	 */
	public abstract void createOrUpdate(Integer regionId, String regionName, Integer regionCode, Integer parentId, Integer useFlag) throws RegionNameExistsException;

	/**
	 * 删除区域
	 */
	public abstract void deleteRegion(Integer regionId) throws RegionUsingException;

	/**
	 * 获取区域列表 - 所有的
	 */
	public abstract List<RegionBean> getListAll();

	/**
	 * 获取区域列表 - 所有有地图信息的
	 */
	@Deprecated
	public abstract List<RegionBean> getListByMap();

	/**
	 * 获取所有子区域列表 - 根据父ID
	 */
	public abstract List<RegionBean> getListByParentId(Integer parentId);

	/**
	 * 获取顶层区域
	 */
	public abstract List<RegionBean> getListByRoot();

	/**
	 * 获取区域列表 - 树结构数据 - 根据父ID - 所有
	 */
	public abstract List<RegionTreeBean> getTreeBeanListByParentId(Integer parentId, Integer useFlag);

	/**
	 * 修改区域排序
	 */
	public abstract void updateSequence(Integer regionId, Integer parentId, Integer moveType);

}
