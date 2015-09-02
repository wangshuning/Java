package org.limingnihao.application.web;

import java.util.List;

import org.limingnihao.application.help.ApplicationHelp;
import org.limingnihao.application.service.ResourceService;
import org.limingnihao.application.service.RoleService;
import org.limingnihao.application.service.exception.RoleNullPointerException;
import org.limingnihao.application.service.exception.RoleUsingException;
import org.limingnihao.application.service.model.ExtReaderBean;
import org.limingnihao.application.service.model.ListBean;
import org.limingnihao.application.service.model.ResourceBean;
import org.limingnihao.application.service.model.ResultBean;
import org.limingnihao.application.service.model.RoleBean;
import org.limingnihao.application.type.UseFlagType;
import org.limingnihao.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 权限管理相关
 */
@Controller
@RequestMapping("role/")
public class RoleController {

	public static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

	@Autowired
	private RoleService roleService;

	@Autowired
	private ResourceService resourceService;

	@RequestMapping("list")
	public void list() {
	}

	/**
	 * 创建或修改角色
	 */
	@RequestMapping("createOrUpdate")
	@ResponseBody
	public ResultBean createOrUpdate(Integer roleId, String roleName, String resourceIds, Integer useFlag) {
		logger.info("createOrUpdate - roleId=" + roleId + ", roleName=" + roleName + ", resourceIds=" + resourceIds + ", useFlag=" + useFlag);
		ResultBean result = new ResultBean();
		result.setSuccess(true);
		if (roleId == null) {
			result.setMessage("新增角色成功！");
		} else {
			result.setMessage("修改角色成功！");
		}
		try {
			this.roleService.createOrUpdate(roleId, roleName, ApplicationHelp.SYSTEM_CURRENT_TYPE, resourceIds, useFlag);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("由于未知原因，操作失败！");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除角色
	 */
	@RequestMapping("deleteEntity")
	@ResponseBody
	public ResultBean deleteRoleById(Integer roleId) {
		logger.info("deleteEntity - roleId=" + roleId);
		ResultBean result = new ResultBean();
		try {
			this.roleService.deleteEntity(roleId);
			result.setSuccess(true);
			result.setMessage("删除角色成功！");
		} catch (RoleNullPointerException e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		} catch (RoleUsingException e) {
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
	 * 角色列表
	 */
	@RequestMapping("getRoleBeanList")
	@ResponseBody
	public List<RoleBean> getRoleBeanList() {
		logger.info("getRoleBeanList");
		ListBean<RoleBean> roleListBean = this.roleService.getListBean(ApplicationHelp.SYSTEM_CURRENT_TYPE, UseFlagType.ENABLED.value(), 0, Integer.MAX_VALUE, PageUtil.NEXT);
		return roleListBean.getBeanList();
	}

	/**
	 * 角色列表 - 分页
	 */
	@RequestMapping("getRoleListBean_grid")
	@ResponseBody
	public ExtReaderBean getRoleListBean_grid(Integer page, Integer limit) {
		logger.info("getRoleListBean_grid - page=" + page + ", limit=" + limit);
		ListBean<RoleBean> listBean = this.roleService.getListBean(ApplicationHelp.SYSTEM_CURRENT_TYPE, UseFlagType.ENABLED.value(), page - 1, limit, PageUtil.NEXT);
		ExtReaderBean render = new ExtReaderBean();
		render.setTotalSize(listBean.getNumberTotal());
		render.setDataArray(listBean.getBeanList().toArray());
		return render;
	}

	/**
	 * 根据角色获取资源列表
	 */
	@RequestMapping("getResourceBeanListByRoleId")
	@ResponseBody
	public List<ResourceBean> getResourceBeanListByRoleId(Integer roleId) {
		logger.info("getResourceBeanListByRoleId - roleId=" + roleId);
		return this.resourceService.getResourceBeanListByRoleId(roleId, ApplicationHelp.SYSTEM_CURRENT_TYPE);
	}

	/**
	 * 所有资源列表
	 */
	@RequestMapping("getResourceBeanTreeList")
	@ResponseBody
	public List<ResourceBean> getResourceBeanTreeList() {
		logger.info("getResourceBeanTreeList");
		return this.resourceService.getResourceBeanTreeList(ApplicationHelp.SYSTEM_CURRENT_TYPE);
	}
}
