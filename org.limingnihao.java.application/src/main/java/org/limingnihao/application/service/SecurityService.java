package org.limingnihao.application.service;

import java.util.List;
import java.util.Map;

import org.limingnihao.application.service.exception.UserNullPointerException;
import org.limingnihao.application.service.model.ResourceBean;
import org.limingnihao.application.service.model.UserAuthorityBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SecurityService {

	/**
	 * 获取资源列表 - 根据父ID，和用户 - (用于组织导航菜单)
	 * 
	 * @param parentId
	 * @param userId
	 * @return
	 */
	public abstract List<ResourceBean> getResourceBeanListForMenuAndChildByUserId(Integer parentId, Integer userId);

	/**
	 * 获取资源列表 - 根据父ID
	 * 
	 * @param parentId
	 * @return Map<String, ResourceBean>
	 */
	public abstract Map<String, ResourceBean> getResourceBeanMap(Integer parentId);

	/**
	 * 获取用户带权限的bean
	 * 
	 * @param userId
	 * @return
	 * @throws UserNullPointerException
	 */
	public abstract UserAuthorityBean getUserAuthorityBeanByUserId(Integer userId) throws UserNullPointerException;
}