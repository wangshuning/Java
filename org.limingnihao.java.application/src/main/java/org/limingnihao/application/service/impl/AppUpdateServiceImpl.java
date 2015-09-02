package org.limingnihao.application.service.impl;

import org.limingnihao.application.service.AppUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppUpdateServiceImpl implements AppUpdateService {

	private static final Logger logger = LoggerFactory.getLogger(AppUpdateServiceImpl.class);

	@Override
	public void updateToProject() {
		logger.info("updateToProject - abstract");
	}

}
