package org.limingnihao.application.service;

import org.limingnihao.application.service.model.AuthorityBean;
import org.limingnihao.application.service.model.ListBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AuthorityService {

	/**
	 * 创建或者修改权限
	 * 
	 * @param authorityId
	 * @param authorityName
	 * @param authorityFlag
	 * @param useFlagType
	 * @param resourceIds
	 */
	public void createOrUpdate(Integer authorityId, String authorityName, String authorityFlag, Integer useFlagType, String resourceIds);

	/**
	 * 删除权限
	 * 
	 * @param authorityId
	 */
	public void deleteEntity(Integer authorityId);

	/**
	 * 获取权限列表
	 */
	public ListBean<AuthorityBean> getListBean(int pageNow, int pageSize, String pageAction);

}
