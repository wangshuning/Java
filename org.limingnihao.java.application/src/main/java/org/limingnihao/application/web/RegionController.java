package org.limingnihao.application.web;

import java.util.List;

import org.limingnihao.application.service.RegionService;
import org.limingnihao.application.service.exception.RegionNameExistsException;
import org.limingnihao.application.service.exception.RegionUsingException;
import org.limingnihao.application.service.model.RegionBean;
import org.limingnihao.application.service.model.RegionTreeBean;
import org.limingnihao.application.service.model.ResultBean;
import org.limingnihao.application.type.UseFlagType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("region/")
public class RegionController {

	public static final Logger logger = LoggerFactory.getLogger(RegionController.class);

	@Autowired
	private RegionService regionService;

	@RequestMapping("list")
	public void list() {
	}

	/**
	 * 添加或修改区域
	 */
	@RequestMapping("createOrUpdate")
	@ResponseBody
	public ResultBean createOrUpdate(Integer regionId, String regionName, Integer regionCode, Integer parentId, Integer useFlag) {
		logger.info("createOrUpdate - regionId=" + regionId + ", regionName=" + regionName + ", regionCode=" + regionCode + ", parentId=" + parentId + ", useFlag=" + useFlag);
		ResultBean resultBean = new ResultBean();
		resultBean.setSuccess(true);
		if (regionId != null && regionId != 0) {
			resultBean.setMessage("修改操作成功！");
		} else {
			resultBean.setMessage("添加操作成功！");
		}
		try {
			this.regionService.createOrUpdate(regionId, regionName, regionCode, parentId, useFlag);
		} catch (RegionNameExistsException e) {
			resultBean.setSuccess(false);
			resultBean.setMessage(e.getMessage());
		} catch (Exception e) {
			resultBean.setSuccess(false);
			resultBean.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return resultBean;
	}

	/**
	 * 删除区域
	 */
	@RequestMapping("deleteRegion")
	@ResponseBody
	public ResultBean deleteRegion(Integer regionId) {
		logger.info("deleteRegion - regionId=" + regionId);
		ResultBean resultBean = new ResultBean();
		try {
			this.regionService.deleteRegion(regionId);
			resultBean.setSuccess(true);
			resultBean.setMessage("删除成功！");
		} catch (RegionUsingException e) {
			resultBean.setSuccess(false);
			resultBean.setMessage(e.getMessage());
		} catch (Exception e) {
			resultBean.setSuccess(false);
			resultBean.setMessage("由于未知的原因，操作失败！");
			e.printStackTrace();
		}
		return resultBean;
	}

	/**
	 * 根据区域parentId获取所有子区域列表
	 */
	@RequestMapping("getRegionBeanAllList")
	@ResponseBody
	public List<RegionBean> getRegionBeanAllList() {
		logger.info("getRegionBeanAllList");
		return this.regionService.getListAll();
	}

	/**
	 * 根据区域parentId获取区域的树形结构 - 启用的
	 */
	@RequestMapping("getRegionTreeByParentId")
	@ResponseBody
	public List<RegionTreeBean> getRegionTreeByParentId(Integer regionId) {
		logger.info("getRegionTreeByParentId - regionId=" + regionId);
		return this.regionService.getTreeBeanListByParentId(regionId, UseFlagType.ENABLED.value());
	}

	/**
	 * 根据区域parentId获取区域的树形结构 - 全部
	 */
	@RequestMapping("getRegionTreeAllByParentId")
	@ResponseBody
	public List<RegionTreeBean> getRegionTreeAllByParentId(Integer regionId) {
		logger.info("getRegionTreeAllByParentId - regionId=" + regionId);
		return this.regionService.getTreeBeanListByParentId(regionId, null);
	}

	/**
	 * 排序
	 */
	@RequestMapping("updateSequence")
	@ResponseBody
	public ResultBean updateSequence(Integer regionId, Integer parentId, Integer moveType) {
		logger.info("updateSequence - regionId=" + regionId + ", parentId=" + parentId + ", moveType=" + moveType);
		ResultBean resultBean = new ResultBean();
		try {
			this.regionService.updateSequence(regionId, parentId, moveType);
			resultBean.setSuccess(true);
			resultBean.setMessage("");
		} catch (Exception e) {
			resultBean.setSuccess(false);
			resultBean.setMessage("由于未知原因，操作失败！");
			e.printStackTrace();
		}
		return resultBean;
	}

}
