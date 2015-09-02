package org.limingnihao.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.limingnihao.application.data.AuthorityDao;
import org.limingnihao.application.data.ResourceDao;
import org.limingnihao.application.data.model.AuthorityEntity;
import org.limingnihao.application.data.model.ResourceEntity;
import org.limingnihao.application.help.ApplicationHelp;
import org.limingnihao.application.service.AuthorityService;
import org.limingnihao.application.service.model.AuthorityBean;
import org.limingnihao.application.service.model.ListBean;
import org.limingnihao.application.service.model.ResourceBean;
import org.limingnihao.application.type.ResourceType;
import org.limingnihao.application.type.UseFlagType;
import org.limingnihao.util.NumberUtil;
import org.limingnihao.util.PageUtil;
import org.limingnihao.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityDao authorityDao;

	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void createOrUpdate(Integer authorityId, String authorityName, String authorityFlag, Integer useFlagType, String resourceIds) {
		AuthorityEntity entity = authorityDao.getEntity(authorityId);
		if (null == entity) {
			entity = new AuthorityEntity();
		}
		entity.setSystemType(ApplicationHelp.SYSTEM_CURRENT_TYPE);
		entity.setAuthorityName(authorityName);
		entity.setAuthorityFlag(authorityFlag);
		entity.setUseFlag(useFlagType);

		if (!StringUtil.isBlank(resourceIds)) {
			int[] idArray = NumberUtil.parseInts(resourceIds, ",");
			List<ResourceEntity> resourceList = new ArrayList<ResourceEntity>();
			for (int resourceId : idArray) {
				ResourceEntity resourceEntity = this.resourceDao.getEntity(resourceId);
				if (resourceEntity != null) {
					resourceList.add(resourceEntity);
				}
			}
			entity.setResourceList(resourceList);
		}
		this.authorityDao.saveEntity(entity);
	}

	@Override
	public void deleteEntity(Integer authorityId) {
		AuthorityEntity entity = authorityDao.getEntity(authorityId);
		if (null != entity) {
			this.authorityDao.deleteEntity(entity);
		}
	}

	@Override
	public ListBean<AuthorityBean> getListBean(int pageNow, int pageSize, String pageAction) {
		ListBean<AuthorityBean> listBean = new ListBean<AuthorityBean>();
		listBean.setNumberTotal(this.authorityDao.getList_count()); // 总记录数
		listBean.setPageTotal(PageUtil.getPageTotal(listBean.getNumberTotal(), pageSize)); // 总页数
		listBean.setPageNow(PageUtil.getPageNow(pageAction, pageNow, listBean.getPageTotal())); // 当前页
		int firstResult = PageUtil.getFirstResult(listBean.getPageNow(), pageSize);
		int maxResults = PageUtil.getMaxResults(pageSize);

		List<AuthorityBean> beanList = new ArrayList<AuthorityBean>();
		List<AuthorityEntity> authorityEntityList = this.authorityDao.getList(firstResult, maxResults);
		for (AuthorityEntity entity : authorityEntityList) {
			AuthorityBean authorityBean = new AuthorityBean();
			authorityBean.setAuthorityId(entity.getAuthorityId());
			authorityBean.setAuthorityName(entity.getAuthorityName());
			authorityBean.setAuthorityFlag(entity.getAuthorityFlag());
			authorityBean.setUseFlag(entity.getUseFlag());
			authorityBean.setUseFlagName(entity.getUseFlag().intValue() == UseFlagType.ENABLED.value() ? UseFlagType.ENABLED.getName() : UseFlagType.DISABLED.getName());

			List<ResourceEntity> resourceEntityList = entity.getResourceList();
			String[] resourceNames = new String[resourceEntityList.size()];
			int[] resourceIds = new int[resourceEntityList.size()];
			ResourceBean[] resources = new ResourceBean[resourceEntityList.size()];
			for (int i = 0; i < resourceEntityList.size(); i++) {
				ResourceEntity en = resourceEntityList.get(i);
				resourceIds[i] = en.getResourceId();
				if (en.getResourceType().intValue() == ResourceType.MENU.value()) {
					resourceNames[i] = en.getResourceName() + "[" + ResourceType.MENU.getName() + "]";
				} else {
					resourceNames[i] = en.getResourceName() + "[" + ResourceType.URL.getName() + "]";
				}
				ResourceBean resourceBean = new ResourceBean();
				resourceBean.setResourceId(resourceIds[i]);
				resourceBean.setResourceName(resourceNames[i]);
				resources[i] = resourceBean;
			}
			authorityBean.setResourceIds(resourceIds);
			authorityBean.setResourceNames(resourceNames);
			authorityBean.setResources(resources);
			beanList.add(authorityBean);
		}
		listBean.setBeanList(beanList);
		return listBean;
	}

}
