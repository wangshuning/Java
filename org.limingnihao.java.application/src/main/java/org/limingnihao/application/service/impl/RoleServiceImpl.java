package org.limingnihao.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.limingnihao.application.data.ResourceDao;
import org.limingnihao.application.data.RoleDao;
import org.limingnihao.application.data.model.AuthorityEntity;
import org.limingnihao.application.data.model.ResourceEntity;
import org.limingnihao.application.data.model.RoleEntity;
import org.limingnihao.application.data.model.UserEntity;
import org.limingnihao.application.service.RoleService;
import org.limingnihao.application.service.exception.RoleNullPointerException;
import org.limingnihao.application.service.exception.RoleUsingException;
import org.limingnihao.application.service.model.AuthorityBean;
import org.limingnihao.application.service.model.ListBean;
import org.limingnihao.application.service.model.RoleBean;
import org.limingnihao.application.type.UseFlagType;
import org.limingnihao.util.NumberUtil;
import org.limingnihao.util.PageUtil;
import org.limingnihao.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

	public static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void createOrUpdate(Integer roleId, String roleName, Integer systemType, String resourceIds, Integer useFlag) {
		RoleEntity entity = this.roleDao.getEntity(roleId);
		if (null == entity) {
			entity = new RoleEntity();
		}
		entity.setRoleName(roleName);
		entity.setSystemType(systemType);
		entity.setUseFlag(useFlag);

		entity.getAuthorityList().clear();
		this.roleDao.saveEntity(entity);
		if (!StringUtil.isBlank(resourceIds)) {
			List<AuthorityEntity> authorityList = new ArrayList<AuthorityEntity>();
			int[] ids = NumberUtil.parseInts(resourceIds, ",");
			for (int resourceId : ids) {
				ResourceEntity resourceEntity = this.resourceDao.getEntity(resourceId);
				if (null != resourceEntity && resourceEntity.getAuthorityList() != null && resourceEntity.getAuthorityList().size() > 0) {
					AuthorityEntity authBean = resourceEntity.getAuthorityList().get(0);
					logger.info("createOrUpdate - resourceId=" + resourceId + ", authorityId=" + authBean.getAuthorityId());
					authorityList.add(authBean);
				} else {
					logger.info("createOrUpdate - resourceId=" + resourceId + ", authorityId=null - 添加失败");
				}
			}
			entity.setAuthorityList(authorityList);
			this.roleDao.saveEntity(entity);
		}
	}

	@Override
	public void deleteEntity(Integer roleId) throws RoleNullPointerException, RoleUsingException {
		RoleEntity entity = this.roleDao.getEntity(roleId);
		if (entity == null) {
			throw new RoleNullPointerException();
		}
		List<UserEntity> userEntityList = entity.getUserList();
		if (userEntityList != null && !userEntityList.isEmpty()) {
			throw new RoleUsingException();
		}
		this.roleDao.deleteEntity(entity);
	}

	@Override
	public ListBean<RoleBean> getListBean(Integer systemType, Integer useFlag, int pageNow, int pageSize, String pageAction) {
		ListBean<RoleBean> listBean = new ListBean<RoleBean>();
		listBean.setNumberTotal(this.roleDao.getListBySystemType_count(systemType, useFlag));
		listBean.setPageTotal(PageUtil.getPageTotal(listBean.getNumberTotal(), pageSize));// 总页数
		listBean.setPageNow(PageUtil.getPageNow(pageAction, pageNow, listBean.getPageTotal()));// 操作后的当前页
		int firstResult = PageUtil.getFirstResult(listBean.getPageNow(), pageSize);
		int maxResults = PageUtil.getMaxResults(pageSize);

		List<RoleBean> beanList = new ArrayList<RoleBean>();
		List<RoleEntity> roleEntityList = this.roleDao.getListBySystemType(systemType, useFlag, firstResult, maxResults);
		for (RoleEntity roleEntity : roleEntityList) {
			RoleBean roleBean = new RoleBean();
			roleBean.setRoleId(roleEntity.getRoleId());
			roleBean.setRoleName(roleEntity.getRoleName());
			roleBean.setUseFlag(roleEntity.getUseFlag());
			roleBean.setUseFlagName(roleEntity.getUseFlag().intValue() == UseFlagType.ENABLED.value() ? UseFlagType.ENABLED.getName() : UseFlagType.DISABLED.getName());

			List<AuthorityEntity> authorityEntityList = roleEntity.getAuthorityList();
			String[] authorityNames = new String[authorityEntityList.size()];
			AuthorityBean[] authoritys = new AuthorityBean[authorityEntityList.size()];
			for (int i = 0; i < authorityEntityList.size(); i++) {
				AuthorityEntity authorityEntity = authorityEntityList.get(i);
				authorityNames[i] = authorityEntity.getAuthorityName();

				AuthorityBean authorityBean = new AuthorityBean();
				authorityBean.setAuthorityId(authorityEntity.getAuthorityId());
				authorityBean.setAuthorityName(authorityEntity.getAuthorityName());
				authoritys[i] = authorityBean;
			}
			roleBean.setAuthorityNames(authorityNames);
			roleBean.setAuthoritys(authoritys);
			beanList.add(roleBean);
		}
		listBean.setBeanList(beanList);
		return listBean;
	}

}
