package org.limingnihao.application.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AppUpdateService {

	/**
	 * 更新application配置项到项目
	 */
	public abstract void updateToProject();

}
