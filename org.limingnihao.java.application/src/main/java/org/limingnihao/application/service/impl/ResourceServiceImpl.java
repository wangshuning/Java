package org.limingnihao.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.limingnihao.application.data.ResourceDao;
import org.limingnihao.application.data.model.ResourceEntity;
import org.limingnihao.application.service.ResourceService;
import org.limingnihao.application.service.model.ResourceBean;
import org.limingnihao.application.type.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceDao resourceDao;

	@Override
	public List<ResourceBean> getResourceBeanList(Integer systemType) {
		List<ResourceEntity> resourceEntityList = this.resourceDao.getListByResourceTypeExcludeRoot(null, systemType);
		List<ResourceBean> beanList = new ArrayList<ResourceBean>();
		for (ResourceEntity entity : resourceEntityList) {
			ResourceBean bean = new ResourceBean();
			bean.setResourceId(entity.getResourceId());
			String resourceName;
			if (entity.getResourceType().intValue() == ResourceType.MENU.value()) {
				resourceName = entity.getResourceName() + "[" + ResourceType.MENU.getName() + "]";
			} else {
				resourceName = entity.getResourceName() + "[" + ResourceType.URL.getName()+ "]";
			}
			bean.setResourceName(resourceName);
			beanList.add(bean);
		}
		return beanList;
	}

	@Override
	public List<ResourceBean> getResourceBeanListByRoleId(Integer roleId, Integer systemType) {
		List<ResourceBean> beanList = new ArrayList<ResourceBean>();
		List<ResourceEntity> resourceEntityList = this.resourceDao.getListByRoleIdAndResourceType(roleId, ResourceType.URL.value(), systemType);
		for (ResourceEntity entity : resourceEntityList) {
			ResourceBean bean = new ResourceBean();
			bean.setResourceId(entity.getResourceId());
			bean.setResourceName(entity.getResourceName());
			beanList.add(bean);
		}
		return beanList;
	}

	@Override
	public List<ResourceBean> getResourceBeanTreeList(Integer systemType) {
		List<ResourceBean> beanList = new ArrayList<ResourceBean>();
		List<ResourceEntity> resourceEntityList = this.resourceDao.getListByResourceTypeExcludeRoot(ResourceType.MENU.value(), systemType);
		for (ResourceEntity entity : resourceEntityList) {
			ResourceBean bean = new ResourceBean();
			bean.setResourceId(entity.getResourceId());
			bean.setResourceName(entity.getResourceName());

			List<ResourceBean> childrenList = new ArrayList<ResourceBean>();
			List<ResourceEntity> childrenEntityList = this.resourceDao.getListByParentId(entity.getResourceId());
			for (ResourceEntity childEntity : childrenEntityList) {
				ResourceBean child = new ResourceBean();
				child.setResourceId(childEntity.getResourceId());
				child.setResourceName(childEntity.getResourceName());
				childrenList.add(child);
			}
			bean.setChildrenList(childrenList);

			beanList.add(bean);
		}
		return beanList;
	}

}
