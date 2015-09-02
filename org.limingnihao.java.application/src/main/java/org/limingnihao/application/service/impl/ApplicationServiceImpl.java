package org.limingnihao.application.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.limingnihao.application.data.ApplicationDao;
import org.limingnihao.application.data.AttributeDao;
import org.limingnihao.application.data.model.ApplicationEntity;
import org.limingnihao.application.data.model.AttributeEntity;
import org.limingnihao.application.help.ApplicationHelp;
import org.limingnihao.application.service.AppUpdateService;
import org.limingnihao.application.service.ApplicationService;
import org.limingnihao.application.service.SecurityService;
import org.limingnihao.application.service.model.ApplicationBean;
import org.limingnihao.application.service.model.ListBean;
import org.limingnihao.application.type.PropertyFlagType;
import org.limingnihao.util.NumberUtil;
import org.limingnihao.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);

	@Autowired
	private AttributeDao attributeDao;

	@Autowired
	private ApplicationDao applicationDao;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private AppUpdateService applicationUpdateService;

	@Override
	public ListBean<ApplicationBean> getListBean(int pageNow, int pageSize, String pageAction) {
		ListBean<ApplicationBean> listBean = new ListBean<ApplicationBean>();
		listBean.setNumberTotal(this.applicationDao.getListBySystemType_count(ApplicationHelp.SYSTEM_CURRENT_TYPE));// 总个数
		listBean.setPageTotal(PageUtil.getPageTotal(listBean.getNumberTotal(), pageSize));// 总页数
		listBean.setPageNow(PageUtil.getPageNow(pageAction, pageNow, listBean.getPageTotal()));// 操作后的当前页
		int firstResult = PageUtil.getFirstResult(listBean.getPageNow(), pageSize);
		int maxResults = PageUtil.getMaxResults(pageSize);

		List<ApplicationBean> beanList = new ArrayList<ApplicationBean>();
		List<ApplicationEntity> entityList = this.applicationDao.getListBySystemType(ApplicationHelp.SYSTEM_CURRENT_TYPE, firstResult, maxResults);
		for (ApplicationEntity entity : entityList) {
			ApplicationBean bean = new ApplicationBean();
			bean.setUid(entity.getPropertyId());
			bean.setPropertyName(entity.getPropertyName());
			bean.setPropertyFlag(entity.getPropertyFlag());
			bean.setPropertyValue(entity.getPropertyValue());
			bean.setDescription(entity.getDescription());
			beanList.add(bean);
		}
		listBean.setBeanList(beanList);
		return listBean;
	}

	@Override
	public void initApplicationConfig() {
		// 权限列表
		ApplicationHelp.RESOURCE_AUTHORITY_MAP = this.securityService.getResourceBeanMap(ApplicationHelp.SYSTEM_CURRENT_TYPE);

		// 属性配置
		List<ApplicationEntity> entityList = this.applicationDao.getListBySystemType(ApplicationHelp.SYSTEM_CURRENT_TYPE, 0, Integer.MAX_VALUE);
		for (ApplicationEntity entity : entityList) {
			ApplicationHelp.APPLICATION_CONFIG.put(entity.getPropertyFlag(), entity.getPropertyValue());
		}

		// 字段配置
		List<AttributeEntity> list = this.attributeDao.getListBySystemType(ApplicationHelp.SYSTEM_CURRENT_TYPE);
		for (AttributeEntity e : list) {
			if (ApplicationHelp.APPLICATION_ATTRIBUTE.containsKey(e.getAttributeFlag())) {
				ApplicationHelp.APPLICATION_ATTRIBUTE.get(e.getAttributeFlag()).put(e.getAttributeValue(), e.getAttributeName());
				continue;
			}
			HashMap<Integer, String> value = new HashMap<Integer, String>();
			value.put(e.getAttributeValue(), e.getAttributeName());
			ApplicationHelp.APPLICATION_ATTRIBUTE.put(e.getAttributeFlag(), value);
		}

		// --------------------------系统配置信息------------------------------ */
		String value = "";
		value = ApplicationHelp.APPLICATION_CONFIG.get(PropertyFlagType.PROJECT_NAME.value());
		if (StringUtils.isNotBlank(value)) {
			ApplicationHelp.PROJECT_NAME = value;
			logger.info("initApplicationConfig - PROJECT_NAME - get=" + value);
		} else {
			logger.info("initApplicationConfig - PROJECT_NAME - def=" + ApplicationHelp.PROJECT_NAME);
		}

		value = ApplicationHelp.APPLICATION_CONFIG.get(PropertyFlagType.IS_SYN_USERNAME.value());
		if (StringUtils.isNotBlank(value)) {
			ApplicationHelp.IS_SYN_INFORMATION = value.trim().equals("1");
			logger.info("initApplicationConfig - IS_SYN_INFORMATION - get=" + value);
		} else {
			logger.info("initApplicationConfig - IS_SYN_INFORMATION - def=" + ApplicationHelp.IS_SYN_INFORMATION);
		}

		value = ApplicationHelp.APPLICATION_CONFIG.get(PropertyFlagType.VERSION_SAVE_PATH.value());
		if (StringUtils.isNotBlank(value)) {
			ApplicationHelp.VERSION_SAVE_PATH = value;
			logger.info("initApplicationConfig - VERSION_SAVE_PATH - get=" + value);
		} else {
			logger.info("initApplicationConfig - VERSION_SAVE_PATH - def=" + ApplicationHelp.VERSION_SAVE_PATH);
		}

		value = ApplicationHelp.APPLICATION_CONFIG.get(PropertyFlagType.VERSION_HTTP_PATH.value());
		if (StringUtils.isNotBlank(value)) {
			ApplicationHelp.VERSION_HTTP_PATH = value;
			logger.info("initApplicationConfig - VERSION_HTTP_PATH - get=" + value);
		} else {
			logger.info("initApplicationConfig - VERSION_HTTP_PATH - def=" + ApplicationHelp.VERSION_HTTP_PATH);
		}

		value = ApplicationHelp.APPLICATION_CONFIG.get(PropertyFlagType.LOGFILE_SAVE_PATH.value());
		if (StringUtils.isNotBlank(value)) {
			ApplicationHelp.LOGFILE_SAVE_PATH = value;
			logger.info("initApplicationConfig - LOGFILE_SAVE_PATH - get=" + value);
		} else {
			logger.info("initApplicationConfig - LOGFILE_SAVE_PATH - def=" + ApplicationHelp.LOGFILE_SAVE_PATH);
		}

		value = ApplicationHelp.APPLICATION_CONFIG.get(PropertyFlagType.LOGFILE_HTTP_PATH.value());
		if (StringUtils.isNotBlank(value)) {
			ApplicationHelp.LOGFILE_HTTP_PATH = value;
			logger.info("initApplicationConfig - LOGFILE_HTTP_PATH - get=" + value);
		} else {
			logger.info("initApplicationConfig - LOGFILE_HTTP_PATH - def=" + ApplicationHelp.LOGFILE_HTTP_PATH);
		}

	}

	@Override
	public int updateApplicationConfig(String ids, String values) {
		if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(values)) {
			String[] idArray = ids.split(",");
			String[] valueArray = values.split(",");
			int update_count = 0;// 是否进行了更新操作
			for (int i = 0; i < idArray.length; i++) {
				ApplicationEntity entity = this.applicationDao.getEntity(NumberUtil.parseInt(idArray[i]));
				if (entity != null) {
					logger.info("updateApplicationProperty - propertyId=" + entity.getPropertyId() + " - flag=" + entity.getPropertyFlag() + ", 旧值=" + entity.getPropertyValue() + ", 新值="
							+ valueArray[i]);
					entity.setPropertyValue(valueArray[i]);
					this.applicationDao.saveEntity(entity);
					update_count++;
				}
			}
			this.applicationUpdateService.updateToProject();
			return update_count;
		}
		return 0;
	}

}
