package org.limingnihao.application.service.impl;

import org.limingnihao.application.service.LocationService;
import org.limingnihao.application.service.model.LocationBean;
import org.limingnihao.util.GsonUtil;
import org.limingnihao.util.HTTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

	public static Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);

	@Override
	public LocationBean getLocationBeanByIpAddress(String ipAddress) {

		String ak = "B1371e0691276763a07067e0405cf510";
		String url = "http://api.map.baidu.com/location/ip?ak=" + ak + "&ip=" + ipAddress + "&coor=bd09ll";

		logger.info("geLocationBeanByIpAddress = url=" + url);

		String json = HTTPUtil.sendGetHttpRequest(url);

		if (json == null || "".equals(json)) {
			logger.info("geLocationBeanByIpAddress - ipAddress=" + ipAddress + " - http请求失败!");
		}
		logger.info("geLocationBeanByIpAddress - json=" + json);

		LocationBean bean = null;

		try {
			bean = GsonUtil.fromJson(json, LocationBean.class);
		} catch (Exception e) {
			logger.info("geLocationBeanByIpAddress - ipAddress=" + ipAddress + " - 解析json失败!");
			e.printStackTrace();
		}
		return bean;
	}
}
