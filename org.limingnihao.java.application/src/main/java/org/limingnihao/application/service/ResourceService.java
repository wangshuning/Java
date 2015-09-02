package org.limingnihao.application.service;

import java.util.List;

import org.limingnihao.application.service.model.ResourceBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ResourceService {

	/**
	 * 获取资源列表
	 * @return
	 */
	public List<ResourceBean> getResourceBeanList(Integer systemType);

	/**
	 * 根据角色Id获取资源列表
	 * @param roleId
	 * @return
	 */
	public List<ResourceBean> getResourceBeanListByRoleId(Integer roleId, Integer systemType);

	/**
	 * 获取资源列表树形结构
	 * @return
	 */
	public List<ResourceBean> getResourceBeanTreeList(Integer systemType);

}
