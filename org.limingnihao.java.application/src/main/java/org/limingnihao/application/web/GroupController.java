package org.limingnihao.application.web;

import java.util.List;

import org.limingnihao.application.service.GroupService;
import org.limingnihao.application.service.exception.GroupNameExistsException;
import org.limingnihao.application.service.exception.GroupUsingException;
import org.limingnihao.application.service.exception.MessageServiceErrorException;
import org.limingnihao.application.service.model.GroupBean;
import org.limingnihao.application.service.model.GroupTreeBean;
import org.limingnihao.application.service.model.ResultBean;
import org.limingnihao.application.type.UseFlagType;
import org.limingnihao.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("group/")
public class GroupController {

	public static final Logger logger = LoggerFactory.getLogger(GroupController.class);

	@Autowired
	private GroupService groupService;

	/**
	 * 添加或修改组织结构
	 */
	@RequestMapping("createOrUpdate")
	@ResponseBody
	public ResultBean createOrUpdate(Integer groupId, String groupName, String description, Integer parentId, Integer regionId, Integer useFlag) {
		logger.info("createOrUpdate - groupId=" + groupId + ", groupName=" + groupName + ", description=" + description + ", parentId=" + parentId + ", regionId=" + regionId + ", useFlag=" + useFlag);
		ResultBean resultBean = new ResultBean();
		resultBean.setSuccess(true);
		if (groupId != null && groupId != 0) {
			resultBean.setMessage("修改操作成功！");
		} else {
			resultBean.setMessage("添加操作成功！");
		}
		try {
			this.groupService.createOrUpdate(groupId, groupName, description, parentId, regionId, useFlag);
		} catch (GroupNameExistsException e) {
			resultBean.setSuccess(false);
			resultBean.setMessage(e.getMessage());
		} catch (MessageServiceErrorException e) {
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
	 * 删除组织结构
	 */
	@RequestMapping("deleteGroup")
	@ResponseBody
	public ResultBean deleteGroup(Integer groupId) {
		logger.info("deleteGroup - groupId=" + groupId);
		ResultBean resultBean = new ResultBean();
		try {
			this.groupService.deleteGroup(groupId);
			resultBean.setSuccess(true);
			resultBean.setMessage("删除成功！");
		} catch (GroupUsingException e) {
			resultBean.setSuccess(false);
			resultBean.setMessage(e.getMessage());
		} catch (MessageServiceErrorException e) {
			resultBean.setSuccess(false);
			resultBean.setMessage(e.getMessage());
		} catch (Exception e) {
			resultBean.setSuccess(false);
			resultBean.setMessage("由于未知原因，操作失败！");
			e.printStackTrace();
		}
		return resultBean;
	}

	/**
	 * 根据组织结构parentId获取组织结构的树形结构
	 */
	@RequestMapping("getGroupTreeByParentId")
	@ResponseBody
	public List<GroupTreeBean> getGroupTreeByParentId(Integer groupId) {
		logger.info("getGroupTreeByParentId - groupId=" + groupId);
		return this.groupService.getTreeBeanListByParentId(groupId, UseFlagType.ENABLED.value());
	}

	/**
	 * 根据组织结构parentId获取组织结构的树形结构
	 */
	@RequestMapping("getGroupTreeAllByParentId")
	@ResponseBody
	public List<GroupTreeBean> getGroupTreeAllByParentId(Integer groupId) {
		logger.info("getGroupTreeAllByParentId - groupId=" + groupId);
		return this.groupService.getTreeBeanListByParentId(groupId, null);
	}

	/**
	 * 获取所有根节点的组织结构
	 */
	@RequestMapping("getGroupBeanListByRoot")
	@ResponseBody
	public List<GroupBean> getGroupBeanListByRoot() {
		return this.groupService.getListBeanByRoot(1, Integer.MAX_VALUE, PageUtil.FIRST).getBeanList();
	}
	
	/**
	 * 根据parentId和type获得GroupBean
	 */
	@RequestMapping("getGroupBeanListByParentIdAndGroupType")
	@ResponseBody
	public List<GroupBean> getGroupBeanListByParentIdAndGroupType(Integer parentId,Integer groupType){
		return this.groupService.getListBeanByParentIdAndGroupType(parentId,groupType).getBeanList();
	}

	/**
	 * 排序
	 */
	@RequestMapping("updateSequence")
	@ResponseBody
	public ResultBean updateSequence(Integer groupId, Integer parentId, Integer moveType) {
		logger.info("updateSequence - groupId=" + groupId + ", parentId=" + parentId + ", moveType=" + moveType);
		ResultBean resultBean = new ResultBean();
		try {
			this.groupService.updateSequence(groupId, parentId, moveType);
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
