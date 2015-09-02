package org.limingnihao.application.service;

import org.limingnihao.application.service.exception.RoleNullPointerException;
import org.limingnihao.application.service.exception.RoleUsingException;
import org.limingnihao.application.service.model.ListBean;
import org.limingnihao.application.service.model.RoleBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoleService {

	/**
	 * 保存或更新角色
	 */
	public void createOrUpdate(Integer roleId, String roleName, Integer systemType, String resourceIds, Integer useFlag);

	/**
	 * 删除角色
	 */
	public void deleteEntity(Integer roleId) throws RoleNullPointerException, RoleUsingException;

	/**
	 * 获取角色列表
	 */
	public ListBean<RoleBean> getListBean(Integer systemType, Integer useFlag, int pageNow, int pageSize, String pageAction);

}
