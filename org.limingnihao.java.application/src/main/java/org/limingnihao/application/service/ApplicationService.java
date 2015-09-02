package org.limingnihao.application.service;

import org.limingnihao.application.service.model.ApplicationBean;
import org.limingnihao.application.service.model.ListBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ApplicationService {

	/**
	 * 获取配置信息列表
	 */
	public abstract ListBean<ApplicationBean> getListBean(int pageNow, int pageSize, String pageAction);

	/**
	 * 初始化-配置信息
	 */
	public abstract void initApplicationConfig();

	/**
	 * 更新配置信息
	 */
	public abstract int updateApplicationConfig(String ids, String values);

}
