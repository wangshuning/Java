package org.limingnihao.application.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.limingnihao.application.help.ApplicationHelp;
import org.limingnihao.application.service.AttributeService;
import org.limingnihao.application.service.VersionService;
import org.limingnihao.application.service.exception.FileMkdirErrorException;
import org.limingnihao.application.service.exception.VersionExistsException;
import org.limingnihao.application.service.exception.VersionNullPointerException;
import org.limingnihao.application.service.model.AttributeBean;
import org.limingnihao.application.service.model.ExtReaderBean;
import org.limingnihao.application.service.model.ListBean;
import org.limingnihao.application.service.model.ResultBean;
import org.limingnihao.application.service.model.VersionBean;
import org.limingnihao.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("version/")
public class VersionConrtroller {

	public static final Logger logger = LoggerFactory.getLogger(VersionConrtroller.class);

	@Autowired
	private AttributeService attributeService;

	@Autowired
	private VersionService versionService;

	@RequestMapping("list")
	public void list() {
	}

	/**
	 * 创建版本
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public void create(Integer versionId, Integer versionType, String versionName, Integer versionCode, Integer deviceType, String description, @RequestParam("fileData") MultipartFile fileData,
			HttpServletResponse response) {
		logger.info("create - versionId=" + versionId + ", versionType=" + versionType + ", versionName=" + versionName + ", versionCode=" + versionCode + ", deviceType=" + deviceType
				+ ", description=" + description);
		ResultBean result = new ResultBean();
		try {
			this.versionService.create(versionId, versionType, versionName, versionCode, deviceType, description, fileData);
			result.setSuccess(true);
			if (versionId == null) {
				result.setMessage("上传版本成功！");
			} else {
				result.setMessage("修改版本成功！");
			}
		} catch (VersionExistsException e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		} catch (FileMkdirErrorException e) {
			result.setSuccess(false);
			result.setMessage("创建保存版本文件夹失败，操作失败！");
		} catch (IOException e1) {
			result.setSuccess(false);
			result.setMessage("保存文件出现错误，操作失败！");
			e1.printStackTrace();
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("由于未知原因，操作失败！");
			e.printStackTrace();
		}
		try {
			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result.toJson());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改版本
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public ResultBean update(Integer versionId, Integer versionType, String versionName, Integer versionCode, Integer deviceType, String description) {
		logger.info("update - versionId=" + versionId + ", versionType=" + versionType + ", versionName=" + versionName + ", versionCode=" + versionCode + ", deviceType=" + deviceType
				+ ", description=" + description);
		ResultBean result = new ResultBean();
		try {
			this.versionService.update(versionId, versionType, versionName, versionCode, deviceType, description);
			result.setSuccess(true);
			result.setMessage("修改版本成功！");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("由于未知原因，操作失败！");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除版本
	 */
	@RequestMapping("deleteEntity")
	@ResponseBody
	public ResultBean deleteEntity(Integer versionId, HttpServletRequest request) {
		logger.info("deleteEntity - versionId=" + versionId);
		ResultBean result = new ResultBean();
		try {
			this.versionService.deleteEntity(versionId);
			result.setSuccess(true);
			result.setMessage("删除版本信息成功！");
		} catch (VersionNullPointerException e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("由于未知原因，操作失败！");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 校验版本重复
	 */
	@RequestMapping("validateVersionRepeat")
	@ResponseBody
	public ResultBean validateVersionRepeat(Integer versionId, String versionName, Integer versionCode, Integer deviceType) {
		logger.info("validateVersionRepeat - 入参 - versionId=" + versionId + ", versionName=" + versionName + ", versionCode=" + versionCode + ", deviceType=" + deviceType);
		ResultBean bean = this.versionService.validateRepeat(versionId, versionName, versionCode, deviceType);
		logger.info("validateVersionRepeat - 出参 - ResultBean=" + bean);
		return bean;
	}

	/**
	 * 获取下拉列表
	 */
	@RequestMapping("getDeviceTypeList")
	@ResponseBody
	public List<AttributeBean> getDeviceTypeList() {
		return this.attributeService.getListBySystemTypeFlag(ApplicationHelp.SYSTEM_CURRENT_TYPE, "DEVICE_TYPE", true);
	}

	/**
	 * 版本列表
	 */
	@RequestMapping("getListBeanByDeviceType_grid")
	@ResponseBody
	public ExtReaderBean getVersionBeanListByDeviceType_grid(Integer deviceType, Integer page, Integer limit, HttpServletRequest request) {
		String serverAddress = request.getScheme() + "://" + request.getServerName();
		logger.info("getListBeanByDeviceType_grid - deviceType=" + deviceType + ", page=" + page + ", limit=" + limit + ", serverAddress=" + serverAddress);
		ListBean<VersionBean> listBean = this.versionService.getListBeanByDeviceType(deviceType, page - 1, limit, PageUtil.NEXT, serverAddress);
		ExtReaderBean render = new ExtReaderBean();
		render.setTotalSize(listBean.getNumberTotal());
		render.setDataArray(listBean.getBeanList().toArray());
		return render;
	}

}
