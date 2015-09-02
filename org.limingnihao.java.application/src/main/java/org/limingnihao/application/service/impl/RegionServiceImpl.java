package org.limingnihao.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.limingnihao.application.data.RegionDao;
import org.limingnihao.application.data.model.RegionEntity;
import org.limingnihao.application.service.RegionService;
import org.limingnihao.application.service.exception.RegionNameExistsException;
import org.limingnihao.application.service.exception.RegionUsingException;
import org.limingnihao.application.service.model.RegionBean;
import org.limingnihao.application.service.model.RegionTreeBean;
import org.limingnihao.application.type.MoveControlType;
import org.limingnihao.application.type.UseFlagType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionServiceImpl implements RegionService {

	public static Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);

	@Autowired
	private RegionDao regionDao;

	@Override
	public void createOrUpdate(Integer regionId, String regionName, Integer regionCode, Integer parentId, Integer useFlag) throws RegionNameExistsException {
		RegionEntity regionEntity = this.regionDao.getEntity(regionId);
		if (regionEntity == null) {
			regionEntity = new RegionEntity();
			regionEntity.setSequence(-1);
		}

		// 重名判断
		RegionEntity oldEntity = this.regionDao.getEntityByRegionName(regionName, parentId);
		if (regionId == null && oldEntity != null || (regionId != null && oldEntity != null && regionId.intValue() != oldEntity.getRegionId().intValue())) {
			throw new RegionNameExistsException();
		}

		regionEntity.setRegionName(regionName);
		regionEntity.setParentEntity(this.regionDao.getEntity(parentId));
		regionEntity.setUseFlag(useFlag);
		this.regionDao.saveEntity(regionEntity);
	}

	@Override
	public void deleteRegion(Integer regionId) throws RegionUsingException {
		RegionEntity regionEntity = this.regionDao.getEntity(regionId);
		if (regionEntity.getChildrenList() != null && !regionEntity.getChildrenList().isEmpty()) {
			throw new RegionUsingException("当前区域下存在子属区域，不能进行删除操作！");
		}
		if (regionEntity.getGroupList() != null && !regionEntity.getGroupList().isEmpty()) {
			throw new RegionUsingException("当前区域下存在组织结构，不能进行删除操作！");
		}
		this.regionDao.deleteEntity(regionEntity);
	}

	@Override
	public List<RegionBean> getListAll() {
		List<RegionBean> beanList = new ArrayList<RegionBean>();
		List<RegionEntity> entityList = this.regionDao.getList();
		for (RegionEntity entity : entityList) {
			RegionBean bean = new RegionBean();
			bean.setRegionId(entity.getRegionId());
			bean.setRegionName(entity.getRegionName());
			bean.setSequence(entity.getSequence());
			bean.setUseFlag(entity.getUseFlag());
			bean.setUseFlagName(entity.getUseFlag().intValue() == UseFlagType.ENABLED.value() ? UseFlagType.ENABLED.getName() : UseFlagType.DISABLED.getName());
			bean.setParentId(entity.getParentEntity() != null ? entity.getParentEntity().getRegionId() : 1);
			bean.setParentName(entity.getParentEntity() != null ? entity.getParentEntity().getRegionName() : "");
			bean.setMapName(entity.getMapName());
			beanList.add(bean);
		}
		return beanList;
	}

	@Override
	public List<RegionBean> getListByMap() {
		List<RegionBean> beanList = new ArrayList<RegionBean>();
		List<RegionEntity> entityList = this.regionDao.getListByHasMapFile();
		for (RegionEntity entity : entityList) {
			RegionBean bean = new RegionBean();
			bean.setRegionId(entity.getRegionId());
			bean.setRegionName(entity.getRegionName());
			bean.setSequence(entity.getSequence());
			bean.setUseFlag(entity.getUseFlag());
			bean.setUseFlagName(entity.getUseFlag().intValue() == UseFlagType.ENABLED.value() ? UseFlagType.ENABLED.getName() : UseFlagType.DISABLED.getName());
			bean.setParentId(entity.getParentEntity() != null ? entity.getParentEntity().getRegionId() : 1);
			bean.setParentName(entity.getParentEntity() != null ? entity.getParentEntity().getRegionName() : "");
			bean.setMapName(entity.getMapName());
			beanList.add(bean);
		}
		return beanList;
	}

	@Override
	public List<RegionBean> getListByParentId(Integer parentId) {
		List<RegionBean> beanList = new ArrayList<RegionBean>();
		List<RegionEntity> entityList = this.regionDao.getListByParentId(parentId, UseFlagType.ENABLED.value(), 0, Integer.MAX_VALUE);
		for (RegionEntity entity : entityList) {
			RegionBean bean = new RegionBean();
			bean.setRegionId(entity.getRegionId());
			bean.setRegionName(entity.getRegionName());
			bean.setSequence(entity.getSequence());
			bean.setUseFlag(entity.getUseFlag());
			bean.setUseFlagName(entity.getUseFlag().intValue() == UseFlagType.ENABLED.value() ? UseFlagType.ENABLED.getName() : UseFlagType.DISABLED.getName());
			bean.setParentId(entity.getParentEntity() != null ? entity.getParentEntity().getRegionId() : 1);
			bean.setParentName(entity.getParentEntity() != null ? entity.getParentEntity().getRegionName() : "");
			beanList.add(bean);
			List<RegionBean> childList = this.getListByParentId(entity.getRegionId());
			if (!childList.isEmpty()) {
				beanList.addAll(childList);
			}
		}
		return beanList;
	}

	@Override
	public List<RegionBean> getListByRoot() {
		List<RegionBean> beanList = new ArrayList<RegionBean>();
		List<RegionEntity> entityList = this.regionDao.getListByRoot();
		for (RegionEntity e : entityList) {
			RegionBean b = new RegionBean();
			b.setRegionId(e.getRegionId());
			b.setRegionCode(e.getRegionCode());
			b.setRegionName(e.getRegionName());
			b.setSequence(e.getSequence());
			RegionEntity p = e.getParentEntity();
			if (p != null) {
				b.setParentId(p.getRegionId());
				b.setParentName(p.getRegionName());
			}
			beanList.add(b);
		}
		return beanList;
	}

	@Override
	public List<RegionTreeBean> getTreeBeanListByParentId(Integer parentId, Integer useFlag) {
		List<RegionTreeBean> treeList = new ArrayList<RegionTreeBean>();
		List<RegionEntity> regionEntityList = this.regionDao.getListByParentId(parentId, useFlag, 0, Integer.MAX_VALUE);
		for (RegionEntity entity : regionEntityList) {
			RegionTreeBean bean = new RegionTreeBean();
			bean.setRegionId(entity.getRegionId());
			bean.setRegionName(entity.getRegionName());
			bean.setSequence(entity.getSequence());
			bean.setUseFlag(entity.getUseFlag());
			bean.setUseFlagName(entity.getUseFlag().intValue() == UseFlagType.ENABLED.value() ? UseFlagType.ENABLED.getName() : UseFlagType.DISABLED.getName());
			bean.setParentId(entity.getParentEntity() != null ? entity.getParentEntity().getRegionId() : 1);
			bean.setParentName(entity.getParentEntity() != null ? entity.getParentEntity().getRegionName() : "");
			bean.setLeaf(entity.getChildrenList().isEmpty());
			treeList.add(bean);
		}
		return treeList;
	}

	@Override
	public void updateSequence(Integer regionId, Integer parentId, Integer moveType) {
		List<RegionEntity> list = this.regionDao.getListByParentId(parentId, null, 0, Integer.MAX_VALUE);
		for (int i = 0; i < list.size(); i++) {
			RegionEntity entity = list.get(i);
			entity.setSequence(i);
			// 向上
			if (entity.getRegionId().intValue() == regionId.intValue() && MoveControlType.UP.value() == moveType.intValue() && i > 0) {
				RegionEntity temp = list.get(i - 1);
				entity.setSequence(i - 1);
				temp.setSequence(i);
				this.regionDao.saveEntity(temp);
			}
			// 向下
			else if (entity.getRegionId().intValue() == regionId.intValue() && MoveControlType.DOWN.value() == moveType.intValue() && i < list.size() - 1) {
				RegionEntity temp = list.get(i + 1);
				entity.setSequence(i + 1);
				temp.setSequence(i);
				this.regionDao.saveEntity(temp);
			}
			this.regionDao.saveEntity(entity);
		}
	}

}
