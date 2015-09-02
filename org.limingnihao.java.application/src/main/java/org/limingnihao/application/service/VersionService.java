package org.limingnihao.application.service;

import java.io.IOException;

import org.limingnihao.application.service.exception.FileMkdirErrorException;
import org.limingnihao.application.service.exception.VersionExistsException;
import org.limingnihao.application.service.exception.VersionNullPointerException;
import org.limingnihao.application.service.model.ListBean;
import org.limingnihao.application.service.model.ResultBean;
import org.limingnihao.application.service.model.VersionBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
public interface VersionService {

	/**
	 * 新增或修改版本信息
	 */
	public abstract void create(Integer versionId, Integer versionType, String versionName, Integer versionCode, Integer deviceType, String description, MultipartFile fileData) throws IOException,
			VersionExistsException, FileMkdirErrorException;

	public abstract void update(Integer versionId, Integer versionType, String versionName, Integer versionCode, Integer deviceType, String description);

	/**
	 * 刪除实体
	 */
	public abstract void deleteEntity(Integer versionId) throws VersionNullPointerException;

	/**
	 * 获取最新版本实体 - 根据设备类型
	 */
	public abstract VersionBean getBeanByDeviceType(Integer deviceType, String serverAddress);

	/**
	 * 获取实体对象
	 */
	public abstract VersionBean getBean(Integer versionId, String serverAddress);

	/**
	 * 获取列表 - 根据设备类型
	 */
	public abstract ListBean<VersionBean> getListBeanByDeviceType(Integer deviceType, int pageNow, int pageSize, String pageAction, String serverAddress);

	/**
	 * 验证 - 新版本是否可用
	 */
	public abstract ResultBean validateRepeat(Integer versionId, String versionName, Integer versionCode, Integer deviceType);

}
