package org.limingnihao.application.service;

import java.util.List;

import org.limingnihao.application.service.exception.GroupNullPointerException;
import org.limingnihao.application.service.exception.MessageServiceErrorException;
import org.limingnihao.application.service.exception.PasswordErrorException;
import org.limingnihao.application.service.exception.RoleNullPointerException;
import org.limingnihao.application.service.exception.UserNullPointerException;
import org.limingnihao.application.service.exception.UserUsingException;
import org.limingnihao.application.service.exception.UsernameExistsException;
import org.limingnihao.application.service.model.ListBean;
import org.limingnihao.application.service.model.UserBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

	/**
	 * 新增或修改用户
	 */
	public abstract int createOrUpdate(Integer userId, String username, String nickname, String password, Integer userType, String groupIds, String roleIds, Integer useFlag)
			throws UsernameExistsException, GroupNullPointerException, RoleNullPointerException, MessageServiceErrorException;

	/**
	 * 删除用户
	 */
	public abstract void deleteEntity(Integer userId) throws UserNullPointerException, UserUsingException, MessageServiceErrorException;

	/**
	 * 获取用户Bean - 根据userId
	 */
	public abstract UserBean getBeanByUserId(Integer userId);

	/**
	 * 获取用户bean - 根据username
	 */
	public abstract UserBean getBeanByUsername(String username);

	/**
	 * 获取列表 - groupId、roleId、nickname、userType - 全部的 - 分页
	 */
	public abstract ListBean<UserBean> getListBeanByGroupIdRoleIdNicknameUserType(Integer groupId, Integer roleId, String nickname, Integer userType, int pageNow, int pageSize, String pageAction);

	/**
	 * 获取列表 - group、nickname、userType - 可用的
	 */
	public abstract List<UserBean> getListByGroupId(Integer groupId, String nickname, Integer userType);

	/**
	 * 用户登录
	 */
	public abstract UserBean userLogin(String username, String password) throws UserNullPointerException, PasswordErrorException;
	
	/**
	 * 用户登出
	 */
	public abstract UserBean userLoginOut(String username);
	/**
	 * 更改用户密码
	 */
	public abstract void userPasswordChange(Integer userId, String oldPassword, String newPassword) throws UserNullPointerException, PasswordErrorException, MessageServiceErrorException;

	/**
	 * 重置密码
	 */
	public abstract void userPasswordReset(Integer userId, String password) throws UserNullPointerException, MessageServiceErrorException;

}
