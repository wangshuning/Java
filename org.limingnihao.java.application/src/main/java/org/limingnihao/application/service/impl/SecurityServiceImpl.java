package org.limingnihao.application.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.limingnihao.application.data.ResourceDao;
import org.limingnihao.application.data.UserDao;
import org.limingnihao.application.data.model.AuthorityEntity;
import org.limingnihao.application.data.model.GroupEntity;
import org.limingnihao.application.data.model.ResourceEntity;
import org.limingnihao.application.data.model.RoleEntity;
import org.limingnihao.application.data.model.UserEntity;
import org.limingnihao.application.service.SecurityService;
import org.limingnihao.application.service.exception.UserNullPointerException;
import org.limingnihao.application.service.model.GroupBean;
import org.limingnihao.application.service.model.ResourceBean;
import org.limingnihao.application.service.model.RoleBean;
import org.limingnihao.application.service.model.UserAuthorityBean;
import org.limingnihao.application.type.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	private UserDao userDao;

	@Override
	public List<ResourceBean> getResourceBeanListForMenuAndChildByUserId(Integer parentId, Integer userId) {
		List<ResourceBean> beanList = new ArrayList<ResourceBean>();
		List<ResourceEntity> entityList = this.resourceDao.getListByParentIdAndUserId(parentId, userId);
		for (ResourceEntity entity : entityList) {
			if (entity.getResourceType().intValue() == ResourceType.MENU.value()) {
				// 第一级MENU
				ResourceBean bean = new ResourceBean();
				bean.setResourceId(entity.getResourceId());
				bean.setResourceName(entity.getResourceName());
				bean.setResourceUrl(entity.getResourceUrl());
				bean.setSequence(entity.getSequence());
				bean.setResourceType(entity.getResourceType());
				bean.setUseFlag(entity.getUseFlag());
				// 第二级URL
				List<ResourceBean> childrenList = new ArrayList<ResourceBean>();
				for (ResourceEntity childEntity : this.resourceDao.getListByParentIdAndUserId(entity.getResourceId(), userId)) {
					ResourceBean childBean = new ResourceBean();
					childBean.setResourceId(childEntity.getResourceId());
					childBean.setResourceName(childEntity.getResourceName());
					childBean.setResourceUrl(childEntity.getResourceUrl());
					childBean.setSequence(childEntity.getSequence());
					childBean.setResourceType(childEntity.getResourceType());
					childBean.setUseFlag(childEntity.getUseFlag());
					childrenList.add(childBean);
				}
				bean.setChildrenList(childrenList);
				beanList.add(bean);
			}
		}
		return beanList;
	}

	@Override
	public Map<String, ResourceBean> getResourceBeanMap(Integer parentId) {
		Map<String, ResourceBean> map = new HashMap<String, ResourceBean>();
		List<ResourceEntity> entityList = this.resourceDao.getListByParentId(parentId);
		for (ResourceEntity entity : entityList) {
			// 1.未加入菜单中的URL
			if (entity.getResourceType().intValue() == ResourceType.URL.value()) {
				ResourceBean bean = new ResourceBean();
				bean.setResourceId(entity.getResourceId());
				bean.setResourceName(entity.getResourceName());
				bean.setResourceUrl(entity.getResourceUrl());
				bean.setSequence(entity.getSequence());
				bean.setResourceType(entity.getResourceType());
				bean.setUseFlag(entity.getUseFlag());
				List<AuthorityEntity> authorityEntityList = entity.getAuthorityList();
				List<String> authorityFlagList = new ArrayList<String>();
				for (AuthorityEntity authorityEntity : authorityEntityList) {
					authorityFlagList.add(authorityEntity.getAuthorityFlag());
				}
				bean.setAuthorityFlagList(authorityFlagList);
				map.put(bean.getResourceUrl(), bean);
			}
			// 2.导航条的菜单
			else {
				List<ResourceEntity> childList = this.resourceDao.getListByParentId(entity.getResourceId());
				for (ResourceEntity child : childList) {
					ResourceBean bean = new ResourceBean();
					bean.setResourceId(child.getResourceId());
					bean.setResourceName(child.getResourceName());
					bean.setResourceUrl(child.getResourceUrl());
					bean.setSequence(child.getSequence());
					bean.setResourceType(child.getResourceType());
					bean.setUseFlag(child.getUseFlag());
					List<AuthorityEntity> authorityEntityList = child.getAuthorityList();
					List<String> authorityFlagList = new ArrayList<String>();
					for (AuthorityEntity authorityEntity : authorityEntityList) {
						authorityFlagList.add(authorityEntity.getAuthorityFlag());
					}
					bean.setAuthorityFlagList(authorityFlagList);
					map.put(bean.getResourceUrl(), bean);
				}
			}
		}
		return map;
	}

	@Override
	public UserAuthorityBean getUserAuthorityBeanByUserId(Integer userId) throws UserNullPointerException {
		UserEntity userEntity = this.userDao.getEntity(userId);
		if (userEntity == null) {
			throw new UserNullPointerException();
		}
		UserAuthorityBean userBean = new UserAuthorityBean();
		userBean.setUserId(userEntity.getUserId());
		userBean.setUsername(userEntity.getUsername());
		userBean.setNickname(userEntity.getNickname());

		List<String> authorityFlagList = new ArrayList<String>();
		for (RoleEntity roleEntity : userEntity.getRoleList()) {
			for (AuthorityEntity authorityEntity : roleEntity.getAuthorityList()) {
				authorityFlagList.add(authorityEntity.getAuthorityFlag());
			}
		}
		userBean.setAuthorityFlagList(authorityFlagList);

		List<RoleBean> roleBeanList = new ArrayList<RoleBean>();
		for (RoleEntity roleEntity : userEntity.getRoleList()) {
			RoleBean roleBean = new RoleBean();
			roleBean.setRoleId(roleEntity.getRoleId());
			roleBean.setRoleName(roleEntity.getRoleName());
			roleBeanList.add(roleBean);
		}
		userBean.setRoleBeanList(roleBeanList);

		List<GroupBean> groupBeanList = new ArrayList<GroupBean>();
		for (GroupEntity groupEntity : userEntity.getGroupList()) {
			GroupBean groupBean = new GroupBean();
			groupBean.setGroupId(groupEntity.getGroupId());
			groupBean.setGroupName(groupEntity.getGroupName());
			groupBeanList.add(groupBean);
		}
		userBean.setGroupBeanList(groupBeanList);
		return userBean;
	}

}
