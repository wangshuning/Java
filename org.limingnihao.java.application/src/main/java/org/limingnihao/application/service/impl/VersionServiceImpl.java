package org.limingnihao.application.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.limingnihao.application.data.VersionDao;
import org.limingnihao.application.data.model.VersionEntity;
import org.limingnihao.application.help.ApplicationHelp;
import org.limingnihao.application.service.VersionService;
import org.limingnihao.application.service.exception.FileMkdirErrorException;
import org.limingnihao.application.service.exception.VersionExistsException;
import org.limingnihao.application.service.exception.VersionNullPointerException;
import org.limingnihao.application.service.model.ListBean;
import org.limingnihao.application.service.model.ResultBean;
import org.limingnihao.application.service.model.VersionBean;
import org.limingnihao.application.type.VersionType;
import org.limingnihao.util.DateUtil;
import org.limingnihao.util.Md5Util;
import org.limingnihao.util.NumberUtil;
import org.limingnihao.util.PageUtil;
import org.limingnihao.util.PathUtil;
import org.limingnihao.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VersionServiceImpl implements VersionService {

	public static Logger logger = LoggerFactory.getLogger(VersionServiceImpl.class);

	@Autowired
	private VersionDao versionDao;

	@Override
	public void create(Integer versionId, Integer versionType, String versionName, Integer versionCode, Integer deviceType, String description, MultipartFile fileData) throws IOException,
			VersionExistsException, FileMkdirErrorException {
		String savePath = ApplicationHelp.VERSION_SAVE_PATH;
		String httpPath = ApplicationHelp.VERSION_HTTP_PATH;
		logger.info("createOrUpdate - versionId=" + versionId + ", versionType=" + versionType + ", versionName=" + versionName + ", versionCode=" + versionCode + ", deviceType=" + deviceType
				+ ", description=" + description + ", fileData=" + fileData + ", savePath=" + savePath + ", httpPath=" + httpPath);
		if (StringUtil.isBlank(httpPath) || StringUtil.isBlank(httpPath)) {
			throw new FileMkdirErrorException("版本文件保存路径或访问路径不存在，请重新设置！");
		}
		File dir = new File(savePath);
		boolean mkResult = true;
		if (!dir.exists()) {
			mkResult = dir.mkdirs();
		} else if (!dir.isDirectory()) {
			dir.delete();
			mkResult = dir.mkdirs();
		}
		if (!mkResult) {
			logger.info("createOrUpdate - savePath=" + savePath + ", httpPath=" + httpPath + ", mkResult=" + mkResult);
			throw new FileMkdirErrorException();
		}
		if (versionId == null) {
			VersionEntity nameEntity = this.versionDao.getEntityByDeviceTypeAndVersionName(deviceType, versionName, ApplicationHelp.SYSTEM_CURRENT_TYPE);
			VersionEntity codeEntity = this.versionDao.getEntityByDeviceTypeAndVersionCode(deviceType, versionCode, ApplicationHelp.SYSTEM_CURRENT_TYPE);
			if (nameEntity != null || codeEntity != null) {
				throw new VersionExistsException();
			}
		}
		VersionEntity versionEntity = this.versionDao.getEntity(versionId);
		if (versionEntity == null) {
			versionEntity = new VersionEntity();
			versionEntity.setSystemType(ApplicationHelp.SYSTEM_CURRENT_TYPE);
			versionEntity.setHttpPath(httpPath);
			versionEntity.setSavePath(savePath);
			versionEntity.setUploadTime(new Timestamp(System.currentTimeMillis()));

			String fileName = fileData.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			String saveFileName = deviceType + "_" + versionName + "_" + DateUtil.format(new Date(), "yyyy-MM-dd") + "." + fileType;
			versionEntity.setSaveName(saveFileName);
			versionEntity.setFileName(fileName);
			versionEntity.setFileType(fileType);
			versionEntity.setFileSize(fileData.getSize());
			String filePath = dir.getAbsolutePath() + File.separator + saveFileName;
			String md5 = Md5Util.getMD5AndSave(fileData.getInputStream(), filePath);
			versionEntity.setFileMd5(md5);
		}
		versionEntity.setVersionType(versionType);
		versionEntity.setVersionName(versionName);
		versionEntity.setVersionCode(versionCode);
		versionEntity.setDeviceType(deviceType);
		versionEntity.setDescription(description);
		this.versionDao.saveEntity(versionEntity);
	}

	@Override
	public void update(Integer versionId, Integer versionType, String versionName, Integer versionCode, Integer deviceType, String description) {
		VersionEntity versionEntity = this.versionDao.getEntity(versionId);
		if (versionEntity == null) {
			throw new VersionNullPointerException();
		}
		versionEntity.setVersionType(versionType);
		versionEntity.setVersionName(versionName);
		versionEntity.setVersionCode(versionCode);
		versionEntity.setDeviceType(deviceType);
		versionEntity.setDescription(description);
		this.versionDao.saveEntity(versionEntity);
	}

	@Override
	public void deleteEntity(Integer versionId) throws VersionNullPointerException {
		logger.info("deleteDeviceVersion - versionId=" + versionId);
		VersionEntity entity = this.versionDao.getEntity(versionId);
		if (entity == null) {
			throw new VersionNullPointerException();
		}
		String fileName = PathUtil.mergePath(entity.getSavePath(), entity.getSaveName());
		this.versionDao.deleteEntity(entity);
		File file = new File(fileName);
		if (file != null) {
			logger.info("deleteDeviceVersion - 文件=" + file.getAbsolutePath());
			file.delete();
		}
	}

	@Override
	public VersionBean getBean(Integer versionId, String serverAddress) {
		VersionEntity entity = this.versionDao.getEntity(versionId);
		if (entity != null) {
			VersionBean bean = new VersionBean();
			bean.setVersionId(entity.getVersionId());
			bean.setVersionType(entity.getVersionType());
			bean.setVersionTypeName(VersionType.valueOf(entity.getVersionType()).getName());
			bean.setVersionName(entity.getVersionName());
			bean.setVersionCode(entity.getVersionCode());
			bean.setDeviceType(entity.getDeviceType());
			bean.setDeviceTypeName(ApplicationHelp.getAttributeName("DEVICE_TYPE", entity.getDeviceType()));
			bean.setFileName(entity.getFileName());
			bean.setSaveName(entity.getSaveName());
			bean.setFileType(entity.getFileType());
			bean.setFileSize(NumberUtil.conversionUnitMemory(entity.getFileSize().doubleValue(), 2));
			bean.setFileMd5(entity.getFileMd5());
			bean.setDescription(entity.getDescription());
			bean.setUploadTime(DateUtil.format(entity.getUploadTime(), "yyyy-MM-dd HH:mm"));

			// 文件路径信息
			File folder = new File(entity.getSavePath());
			if (folder.exists()) {
				bean.setDownUrl(PathUtil.mergeHttp(serverAddress, PathUtil.mergeUrl(entity.getHttpPath(), entity.getSaveName())));
			} else {
				bean.setDownUrl("文件地址错误");
			}
			return bean;
		}
		return null;
	}

	@Override
	public VersionBean getBeanByDeviceType(Integer deviceType, String serverAddress) {
		VersionEntity entity = this.versionDao.getEntityByMaxCodeAndDeviceType(deviceType, ApplicationHelp.SYSTEM_CURRENT_TYPE);
		if (entity != null) {
			return this.getBean(entity.getVersionId(), serverAddress);
		}
		return null;
	}

	@Override
	public ListBean<VersionBean> getListBeanByDeviceType(Integer deviceType, int pageNow, int pageSize, String pageAction, String serverAddress) {
		ListBean<VersionBean> listBean = new ListBean<VersionBean>();
		listBean.setNumberTotal(this.versionDao.getListByDeviceType_count(deviceType, ApplicationHelp.SYSTEM_CURRENT_TYPE)); // 总个数
		listBean.setPageTotal(PageUtil.getPageTotal(listBean.getNumberTotal(), pageSize)); // 总页数
		listBean.setPageNow(PageUtil.getPageNow(pageAction, pageNow, listBean.getPageTotal())); // 操作后的当前页
		int firstResult = PageUtil.getFirstResult(listBean.getPageNow(), pageSize);
		int maxResults = PageUtil.getMaxResults(pageSize);

		List<VersionBean> beanList = new ArrayList<VersionBean>();
		List<VersionEntity> entityList = this.versionDao.getListByDeviceType(deviceType, firstResult, maxResults, ApplicationHelp.SYSTEM_CURRENT_TYPE);
		for (VersionEntity entity : entityList) {
			VersionBean bean = this.getBean(entity.getVersionId(), serverAddress);
			if (bean != null) {
				beanList.add(bean);
			}
		}
		listBean.setBeanList(beanList);
		return listBean;
	}

	@Override
	public ResultBean validateRepeat(Integer versionId, String versionName, Integer versionCode, Integer deviceType) {
		ResultBean result = new ResultBean();
		if (versionName == null || versionCode == null || deviceType == null) {
			result.setSuccess(false);
			result.setMessage("参数不正确！");
			return result;
		}
		VersionEntity nameEntity = this.versionDao.getEntityByDeviceTypeAndVersionName(deviceType, versionName, ApplicationHelp.SYSTEM_CURRENT_TYPE);
		VersionEntity codeEntity = this.versionDao.getEntityByDeviceTypeAndVersionCode(deviceType, versionCode, ApplicationHelp.SYSTEM_CURRENT_TYPE);
		VersionEntity maxEntity = this.versionDao.getEntityByMaxCodeAndDeviceType(deviceType, ApplicationHelp.SYSTEM_CURRENT_TYPE);
		if ((nameEntity != null && versionId == null) || (nameEntity != null && versionId != null && nameEntity.getVersionId().intValue() != versionId.intValue())) {
			result.setSuccess(false);
			result.setMessage("版本名称已存在，请更换其他的名称！");
		} else if ((codeEntity != null && versionId == null) || (codeEntity != null && versionId != null && codeEntity.getVersionId().intValue() != versionId.intValue())) {
			result.setSuccess(false);
			result.setMessage("版本编号已存在，请更换其他的编号！");
		} else if (maxEntity != null && versionCode.intValue() < maxEntity.getVersionCode().intValue()) {
			result.setSuccess(true);
			result.setMessage("请注意！版本" + maxEntity.getVersionName() + "比当前编号级别高，所以终端不会进行更新。是否继续上传？");
		} else {
			result.setSuccess(true);
			result.setMessage("");
		}
		return result;
	}

}
