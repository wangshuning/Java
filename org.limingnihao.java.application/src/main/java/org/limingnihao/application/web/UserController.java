package org.limingnihao.application.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.limingnihao.application.help.ApplicationHelp;
import org.limingnihao.application.service.SecurityService;
import org.limingnihao.application.service.UserService;
import org.limingnihao.application.service.exception.*;
import org.limingnihao.application.service.model.ExtReaderBean;
import org.limingnihao.application.service.model.ListBean;
import org.limingnihao.application.service.model.ResourceBean;
import org.limingnihao.application.service.model.ResultBean;
import org.limingnihao.application.service.model.UserAuthorityBean;
import org.limingnihao.application.service.model.UserBean;
import org.limingnihao.util.PageUtil;
import org.limingnihao.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user/")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@RequestMapping("list")
	public void list() {
	}

	/**
	 * 新增或修改用户
	 */
	@RequestMapping("createOrUpdate")
	@ResponseBody
	public ResultBean createOrUpdate(Integer userId, String username, String nickname, String password, String groupIds, String roleIds, Integer useFlag) {
		logger.info("createOrUpdate - userId=" + userId + ", username=" + username + ", nickname=" + nickname + ", password=" + password + ", groupIds=" + groupIds + ", roleIds=" + roleIds
				+ ", useFlag=" + useFlag);
		ResultBean resultBean = new ResultBean();
		resultBean.setSuccess(true);
		if (userId == null || userId == 0) {
			resultBean.setMessage("新增用户成功。");
		} else {
			resultBean.setMessage("修改用户成功。");
		}
		try {
			this.userService.createOrUpdate(userId, username, nickname, password, 1, groupIds, roleIds, useFlag);
		} catch (UsernameExistsException e) {
			resultBean.setSuccess(false);
			resultBean.setMessage(e.getMessage());
		} catch (GroupNullPointerException e) {
			resultBean.setSuccess(false);
			resultBean.setMessage(e.getMessage());
		} catch (RoleNullPointerException e) {
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
	 * 删除用户
	 */
	@RequestMapping("deleteUser")
	@ResponseBody
	public ResultBean deleteUser(Integer userId) {
		logger.info("deleteUser - userId=" + userId);
		ResultBean resultBean = new ResultBean();
		try {
			this.userService.deleteEntity(userId);
			resultBean.setSuccess(true);
			resultBean.setMessage("删除用户操作成功！");
		} catch (UserNullPointerException e) {
			resultBean.setSuccess(false);
			resultBean.setMessage(e.getMessage());
		} catch (UserUsingException e) {
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
	 * 用户登录
	 */
	@RequestMapping("login")
	@ResponseBody
	public ResultBean login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		logger.info("login - username=" + username + ", password=" + password);
		ResultBean resultBean = new ResultBean();
		try {
			UserBean userBean = this.userService.userLogin(username, password);
//			if (userBean.getUserType() != 1) {
//				resultBean.setSuccess(false);
//				resultBean.setMessage("该用户不允许登陆当前系统！");
//			} else {
				UserAuthorityBean userAuthority = this.securityService.getUserAuthorityBeanByUserId(userBean.getUserId());
				List<ResourceBean> resourceBeanList = this.securityService.getResourceBeanListForMenuAndChildByUserId(ApplicationHelp.SYSTEM_CURRENT_TYPE, userBean.getUserId());
				HttpSession session = request.getSession();
				session.setAttribute(ApplicationHelp.ATTRIBUTE_CURRENT_USER, userAuthority);
				session.setAttribute(ApplicationHelp.ATTRIBUTE_RESOURCE_LIST, resourceBeanList);
				String sessionUrl = (String) session.getAttribute(ApplicationHelp.ATTRIBUTE_CURRENT_URL);
				if (StringUtils.isBlank(sessionUrl)) {
					sessionUrl = ApplicationHelp.URL_INDEX;
				}
				userAuthority.setSessionUrl(sessionUrl);
				session.setAttribute(ApplicationHelp.ATTRIBUTE_CURRENT_URL, sessionUrl);
				resultBean.setSuccess(true);
				resultBean.setMessage(userAuthority.toJson());
//			}
		} catch (PasswordErrorException e) {
			resultBean.setSuccess(false);
			resultBean.setMessage(e.getMessage());
		} catch (UserNullPointerException e) {
			resultBean.setSuccess(false);
			resultBean.setMessage(e.getMessage());
		} catch (RuntimeException e) {
			resultBean.setSuccess(false);
			resultBean.setMessage(e.getMessage());
		} catch (Exception e) {
			resultBean.setSuccess(false);
			resultBean.setMessage("由于未知的原因，用户登录失败！");
			e.printStackTrace();
		}
		return resultBean;
	}

	/**
	 * 用户注销
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute(ApplicationHelp.ATTRIBUTE_CURRENT_USER);
		if (userBean != null) {
			logger.info("logout - userBean=" + userBean.getUsername());
			session.removeAttribute(ApplicationHelp.ATTRIBUTE_CURRENT_USER);
			session.removeAttribute(ApplicationHelp.ATTRIBUTE_RESOURCE_LIST);
			session.removeAttribute(ApplicationHelp.ATTRIBUTE_CURRENT_URL);
		} else {
			logger.info("logout - userBean=null");
		}
		return "redirect:/" + ApplicationHelp.URL_LOGIN;
	}

	/**
	 * 更改密码
	 */
	@RequestMapping("userPasswordChange")
	@ResponseBody
	public ResultBean userPasswordChange(Integer userId, String oldPassword, String newPassword) {
		logger.info("userPasswordChange - userId=" + userId + ", oldPassword=" + oldPassword + ", newPassword=" + newPassword);
		ResultBean resultBean = new ResultBean();
		try {
			this.userService.userPasswordChange(userId, oldPassword, newPassword);
			resultBean.setSuccess(true);
			resultBean.setMessage("用户更改密码成功！");
		} catch (UserNullPointerException e) {
			resultBean.setSuccess(false);
			resultBean.setMessage(e.getMessage());
		} catch (PasswordErrorException e) {
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
	 * 重置密码
	 */
	@RequestMapping("userPasswordReset")
	@ResponseBody
	public ResultBean userPasswordReset(Integer userId, String password) {
		logger.info("userPasswordReset - userId=" + userId + ", password=" + password);
		ResultBean resultBean = new ResultBean();
		try {
			this.userService.userPasswordReset(userId, password);
			resultBean.setSuccess(true);
			resultBean.setMessage("重置密码成功！");
		} catch (UserNullPointerException e) {
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
	 * 获取用户列表 - 全部的
	 */
	@RequestMapping("getUserListBean_grid")
	@ResponseBody
	public ExtReaderBean getUserListBean_grid(Integer groupId, Integer roleId, String nickname, Integer page, Integer limit) {
		logger.info("getUserListBean_grid - groupId=" + groupId + ", roleId=" + roleId + ", nickname=" + nickname + ", page=" + page + ", limit=" + limit);
		ListBean<UserBean> listBean = this.userService.getListBeanByGroupIdRoleIdNicknameUserType(groupId, roleId, StringUtil.decode(nickname), 1, page - 1, limit, PageUtil.NEXT);
		ExtReaderBean render = new ExtReaderBean();
		render.setTotalSize(listBean.getNumberTotal());
		render.setDataArray(listBean.getBeanList().toArray());
		return render;
	}

	/**
	 * 根据groupId获取普通用户列表(用于建会) - 启用的
	 */
	@RequestMapping("getUserListByGroupId")
	@ResponseBody
	public List<UserBean> getUserListByGroupId(Integer groupId, String nickname, Integer userType) {
		logger.info("getUserListByGroupId - groupId=" + groupId + ", nickname=" + nickname + ", userType=" + userType);
		return this.userService.getListByGroupId(groupId, StringUtil.decode(nickname), userType);
	}

	@RequestMapping("getUserListByRoleId")
	@ResponseBody
	public List<UserBean> getUserListByRoleId(Integer roleId) {
		ListBean<UserBean> listBean = this.userService.getListBeanByGroupIdRoleIdNicknameUserType(null, roleId, "", 1, 0, Integer.MAX_VALUE, PageUtil.NEXT);
		return listBean.getBeanList();
	}
}
